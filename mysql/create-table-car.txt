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