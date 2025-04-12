create table course (
    id int primary key auto_increment,
    external_id char(36) unique not null,
    name varchar(255) not null,
    active bool not null default true,
    created_at datetime not null default now(),
    updated_at datetime
);

create unique index U_IDX_COURSE_NAME on course (name);