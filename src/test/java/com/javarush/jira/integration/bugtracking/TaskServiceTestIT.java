package com.javarush.jira.integration.bugtracking;

import com.javarush.jira.bugtracking.TaskService;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.integration.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;


import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@IT
@Sql(scripts = "classpath:db/test.sql", config = @SqlConfig(encoding = "UTF-8"))
@RequiredArgsConstructor
class TaskServiceTestIT {

    private final TaskService taskService;
    private final TaskRepository taskRepository;


    private static final Long taskIdExisted = 5L;
    private static final Long taskIdNotExisted = 6L;
    private static final String tagCodeExisted = "test";
    private static final String tagCodeNotExisted = "invalid";

    private static final String tagTitleExisted = "Test";

    @Test
    void addTagPositiveTest() {
        Optional<TaskTo> task =  taskService.addTag(taskIdExisted, tagCodeExisted);
        assertTrue((task).isPresent());
        assertThat(task.get().getTags()).hasSize(1);
        assertTrue(task.get().getTags().contains(tagTitleExisted));
    }

    @Test
    void addBadCodeTagTest() {
        Optional<TaskTo> task =  taskService.addTag(taskIdExisted, tagCodeNotExisted);
        assertTrue((task).isEmpty());

        Optional<Task> taskShouldNotEdit = taskRepository.findById(taskIdExisted);
        assertThat(taskShouldNotEdit.get().getTags()).hasSize(0);
        assertFalse(taskShouldNotEdit.get().getTags().contains(tagCodeNotExisted));
    }

    @Test
    void subscribeToTask() {
    }

    @Test
    void fetchWorkTime(){
        Duration duration = taskService.fetchWorkTime(taskRepository.findById(5L).get());
        assertEquals(duration.toDays(),2L);
    }


}