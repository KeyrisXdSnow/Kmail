create table hibernate_sequence (next_val bigint) engine=InnoDB

create table notes (
    id bigint not null,
    file_name varchar(255),
    tag varchar(255),
    text varchar(255),
    user_id bigint,
    primary key (id)) engine=InnoDB
);

create table user_role (
    user_id bigint not null,
    roles varchar(255)) engine=InnoDB
);

create table usr (
    id bigint not null,
     activation_code varchar(255),
      active bit not null,
      email varchar(255),
      password varchar(255) not null,
      username varchar(255) not null,
      primary key (id)) engine=InnoDB
);

alter table usr
    add constraint UK_dfui7gxngrgwn9ewee3ogtgym unique (username)
alter table notes
    add constraint FK70bv6o4exfe3fbrho7nuotopf foreign key (user_id) references usr (id)
alter table user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr (id)