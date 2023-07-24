DELETE FROM user_role;
DELETE FROM user_belong;
DELETE FROM activity;
DELETE FROM users;

DELETE FROM REFERENCE;
DELETE FROM CONTACT;
DELETE FROM profile;


DELETE FROM task;
DELETE FROM sprint;
DELETE FROM project;

DELETE FROM task_tag;
-- ALTER SEQUENCE users_id_seq RESTART WITH 1;
DROP SEQUENCE  IF EXISTS  users_id_seq;

CREATE SEQUENCE users_id_seq
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1
    START WITH 1
    NOCACHE
    NOCYCLE;


insert into users (ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, DISPLAY_NAME)
values (1, 'user@gmail.com', '{noop}password', 'userFirstName', 'userLastName', 'userDisplayName'),
       (2, 'admin@gmail.com', '{noop}admin', 'adminFirstName', 'adminLastName', 'adminDisplayName'),
       (3, 'guest@gmail.com', '{noop}guest', 'guestFirstName', 'guestLastName', 'guestDisplayName');

-- 0 DEV
-- 1 ADMIN
insert into USER_ROLE (ROLE, USER_ID)
values (0, 1),
       (1, 2),
       (0, 2);

DELETE FROM reference;
-- ALTER SEQUENCE reference_id_seq RESTART WITH 1;
DROP SEQUENCE  IF EXISTS  reference_id_seq;
CREATE SEQUENCE reference_id_seq
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1
    START WITH 1
    NOCACHE
    NOCYCLE;
-- ============ References =================
insert into reference (CODE, TITLE, REF_TYPE)
-- TASK
values ('task', 'Task', 2),
       ('story', 'Story', 2),
       ('bug', 'Bug', 2),
       ('epic', 'Epic', 2),
-- TASK_STATUS
       ('icebox', 'Icebox', 3),
       ('backlog', 'Backlog', 3),
       ('ready', 'Ready', 3),
       ('in progress', 'In progress', 3),
       ('done', 'Done', 3),
-- SPRINT_STATUS
       ('planning', 'Planning', 4),
       ('implementation', 'Implementation', 4),
       ('review', 'Review', 4),
       ('retrospective', 'Retrospective', 4),
-- USER_TYPE
       ('admin', 'Admin', 5),
       ('user', 'User', 5),
-- PROJECT
       ('scrum', 'Scrum', 1),
       ('task tracker', 'Task tracker', 1),
-- CONTACT
       ('skype', 'Skype', 0),
       ('tg', 'Telegram', 0),
       ('mobile', 'Mobile', 0),
       ('phone', 'Phone', 0),
       ('website', 'Website', 0),
       ('vk', 'VK', 0),
       ('linkedin', 'LinkedIn', 0),
       ('github', 'GitHub', 0),
-- PRIORITY
       ('critical', 'Critical', 7),
       ('high', 'High', 7),
       ('normal', 'Normal', 7),
       ('low', 'Low', 7),
       ('neutral', 'Neutral', 7),
-- TAG
        ('test', 'Test', 8);

insert into reference (CODE, TITLE, REF_TYPE, AUX)
-- MAIL_NOTIFICATION
values ('assigned', 'Assigned', 6, '1'),
       ('three_days_before_deadline', 'Three days before deadline', 6, '2'),
       ('two_days_before_deadline', 'Two days before deadline', 6, '4'),
       ('one_day_before_deadline', 'One day before deadline', 6, '8'),
       ('deadline', 'Deadline', 6, '16'),
       ('overdue', 'Overdue', 6, '32');

insert into profile (ID, LAST_FAILED_LOGIN, LAST_LOGIN, MAIL_NOTIFICATIONS)
values (1, null, null, 49),
       (2, null, null, 14);

DELETE FROM contact;
insert into contact (ID, CODE, VALUE)
values (1, 'skype', 'userSkype'),
       (1, 'mobile', '+01234567890'),
       (1, 'website', 'user.com'),
       (2, 'github', 'adminGitHub'),
       (2, 'tg', 'adminTg'),
       (2, 'vk', 'adminVk');

-- bugtracking
INSERT INTO project (id, code, title, description, type_code, parent_id)
VALUES (2, 'task tracker', 'PROJECT-1', 'test project', 'task tracker', null);

INSERT INTO sprint (id, status_code, title, project_id)
VALUES (1, 'planning', 'Sprint-1', 2);

INSERT INTO task (id, title, description, type_code, status_code, priority_code, estimate, updated, project_id,
                  sprint_id, parent_id)
VALUES (2, 'Task-1', 'short test task', 'task', 'in progress', 'high', null, null, 2, 1, null);
INSERT INTO task (id, title, description, type_code, status_code, priority_code, estimate, updated, project_id,
                  sprint_id, parent_id)
VALUES (3, 'Task-2', 'test 2 task', 'bug', 'ready', 'normal', null, null, 2, 1, null);
INSERT INTO task (id, title, description, type_code, status_code, priority_code, estimate, updated, project_id,
                  sprint_id, parent_id)
VALUES (5, 'Task-4', 'test 4', 'bug', 'in progress', 'normal', null, null, 2, 1, null);
INSERT INTO task (id, title, description, type_code, status_code, priority_code, estimate, updated, project_id,
                  sprint_id, parent_id)
VALUES (4, 'Task-3', 'test 3 descr', 'task', 'done', 'low', null, null, 2, 1, null);



INSERT INTO user_belong (id, object_id, object_type, user_id, user_type_code)
VALUES (3, 2, 2, 2, 'admin');
INSERT INTO user_belong (id, object_id, object_type, user_id, user_type_code)
VALUES (4, 3, 2, 2, 'admin');
INSERT INTO user_belong (id, object_id, object_type, user_id, user_type_code)
VALUES (5, 4, 2, 2, 'admin');
INSERT INTO user_belong (id, object_id, object_type, user_id, user_type_code)
VALUES (6, 5, 2, 2, 'admin');

DELETE FROM ACTIVITY;
insert into ACTIVITY ( ID, AUTHOR_ID, TASK_ID, UPDATED, STATUS_CODE )
values (1, 1, 5, '2023-05-24 20:53:56.287754', 'in progress'),
       (2, 1, 5, '2023-05-25 20:53:56.287754', 'in progress'),
       (3, 2, 5, '2023-05-26 20:53:56.287754', 'ready'),
       (4, 1, 5, '2023-05-30 20:53:56.287754', 'done');

