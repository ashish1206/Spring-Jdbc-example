drop table if exists users;
drop table if exists address;

create table users(
    user_id serial not null,
    username varchar(50) not null,
    date_of_birth DATE,
    constraint user_id_pk primary key ( user_id )
);

create table address(
    add_id serial not null,
    door varchar(50) not null,
    street varchar(50) not null,
    landmark varchar(50) not null,
    city varchar(50) not null,
    user_id int not null,
    constraint add_id_pk primary key ( add_id ),
    constraint user_id_fk foreign key (user_id ) references users (user_id)
);