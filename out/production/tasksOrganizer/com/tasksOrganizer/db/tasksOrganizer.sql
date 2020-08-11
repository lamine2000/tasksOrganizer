drop database if exists tasksOrganizer;
drop user if exists 'tsk';
create database tasksOrganizer;
use tasksOrganizer;
create user 'tsk' identified by 'tsk';
grant all privileges on tasksOrganizer.* to 'tsk'@'%';
flush privileges;

create table Task
(
    id          int auto_increment
        primary key,
    nom         varchar(255) not null,
    description varchar(255)         ,
    importance  int          not null,
    difficulte  int          not null,
    echeance     date         not null,
    tsupp       date         not null,
    ok          boolean      default false
);

select DATE_FORMAT (echeance, '%Y-%m-%d') from tasksOrganizer.Task;
select DATE_FORMAT (tsupp, '%Y-%m-%d') from tasksOrganizer.Task;



