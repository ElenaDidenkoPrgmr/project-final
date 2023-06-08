--liquibase formatted sql

--changeset eldidenko:1
insert into ACTIVITY ( ID, AUTHOR_ID, TASK_ID, UPDATED, STATUS_CODE )
values (1, 1, 5, '2023-05-24 20:53:56.287754', 'in progress'),
       (2, 2, 5, '2023-05-26 20:53:56.287754', 'ready'),
       (3, 1, 5, '2023-05-30 20:53:56.287754', 'done');