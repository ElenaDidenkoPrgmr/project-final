package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Activity;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.internal.repository.ActivityRepository;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.bugtracking.to.ObjectType;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.login.internal.UserRepository;
import com.javarush.jira.ref.RefType;
import com.javarush.jira.ref.internal.Reference;
import com.javarush.jira.ref.internal.ReferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)

public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {

    private static final String READY_TASK = "ready";
    private static final String IN_PROGRESS_TASK = "in progress";
    private static final String DONE_TASK = "done";

    private static final String ADMIN = "admin";
    private static final String USER = "user";

    private final UserRepository userRepository;

    private final UserBelongRepository userBelongRepository;
    private final ReferenceRepository referenceRepository;
    private final ActivityRepository activityRepository;

    public TaskService(TaskRepository repository, TaskMapper mapper,
                       UserRepository userRepository,
                       UserBelongRepository userBelongRepository,
                       ReferenceRepository referenceRepository, ActivityRepository activityRepository) {
        super(repository, mapper);
        this.userRepository = userRepository;
        this.userBelongRepository = userBelongRepository;
        this.referenceRepository = referenceRepository;
        this.activityRepository = activityRepository;
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }

    @Transactional
    public Optional<TaskTo> addTag(Long taskId, String tagCode) {
        return referenceRepository.getByTypeAndCode(RefType.TAG, tagCode)
                .flatMap(tag -> repository.findById(taskId)
                        .map(task -> {
                            task.getTags().add(tag.getTitle());
                            repository.saveAndFlush(task);
                            return mapper.toTo(task);
                        }));
    }

    public List<TaskTo> subscribeToTask(Long taskId, Long userId) {
        User user = userRepository.getExisted(userId);

        UserBelong subscribe = new UserBelong();
        subscribe.setObjectId(taskId);
        subscribe.setObjectType(ObjectType.TASK);
        subscribe.setUserId(userId);

        if (user.hasRole(Role.ADMIN)) {
            subscribe.setUserTypeCode(ADMIN);
        } else {
            subscribe.setUserTypeCode(USER);
        }

        userBelongRepository.saveAndFlush(subscribe);
        return mapper.toToList(repository.getAll());
    }

    public Duration fetchWorkTime(Task task) {
        return fetchTime(task, IN_PROGRESS_TASK, READY_TASK);
    }

    public Duration fetchTestTime(Task task) {
        return fetchTime(task, READY_TASK, DONE_TASK);
    }

    private Duration fetchTime(Task task, String startStatus, String endStatus) {
        List<Activity> activities = activityRepository.findAllByTask(task);
        Optional<Activity> activityStart = activities.stream()
                .filter(a -> a.getStatusCode().equals(startStatus))
                .min(Comparator.comparing(Activity::getUpdated));

        Optional<Activity> activityEnd = activities.stream()
                .filter(a -> a.getStatusCode().equals(endStatus))
                .min(Comparator.comparing(Activity::getUpdated));

        if (activityEnd.isPresent() && activityStart.isPresent()) {
            return Duration.between(activityStart.get().getUpdated(), activityEnd.get().getUpdated());
        } else return Duration.ZERO;
    }
}
