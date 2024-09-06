create schema if not exists tasks_schema;

create table tasks_schema.tasks
(
    id          serial primary key not null,
    title       varchar(500)       not null,
    description varchar(10000),
    start_time  timestamp check (start_time > '0001-01-01' and start_time < '9999-12-31'),
    end_time    timestamp check (end_time > '0001-01-01' and end_time < '9999-12-31'),
    status      varchar(50),
    created_at  timestamp          not null,
    updated_at  timestamp
);
