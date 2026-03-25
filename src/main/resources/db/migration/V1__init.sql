create sequence bet_SEQ start with 1 increment by 50;

create table bet (
    created_at timestamp(6),
    id bigint not null,
    resolved_at timestamp(6),
    server_id bigint,
    user_id bigint,
    primary key (id)
);

create table user (
      created_at timestamp(6),
      deleted_at timestamp(6),
      server_id bigint not null,
      user_id bigint not null,
      name varchar(255),
      primary key (server_id, user_id)
);

alter table bet
    add constraint FKqnww6dst9wfwg3lgdacy105v9
        foreign key (server_id, user_id)
            references user (server_id, user_id);
