create table car (
    id bigint not null auto_increment,
    name varchar(255),
    description varchar(255),
    url_photo varchar(255),
    url_video varchar(255),
    latitude varchar(255),
    longitude varchar(255),
    type varchar(255),
    primary key (id)
);

create table user (
   id bigint not null auto_increment,
    email varchar(255),
    username varchar(255),
    name varchar(255),
    password varchar(255),
    primary key (id)
);

create table role (
   id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
);

create table user_roles (
   user_id bigint not null,
   role_id bigint not null
);

alter table user_roles
   add constraint FK_user_roles_role
   foreign key (role_id)
   references role (id);

alter table user_roles
   add constraint FK_user_roles_user
   foreign key (user_id)
   references user (id);