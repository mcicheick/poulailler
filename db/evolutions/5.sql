
    drop table if exists T_CLIENTS;

    drop table if exists T_USERS;

    create table T_CLIENTS (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        ADDRESS varchar(255),
        FIRST_NAME varchar(255),
        LAST_NAME varchar(255),
        PHONE varchar(255),
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table T_USERS (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        EMAIL varchar(255) not null,
        PASSWORD varchar(255),
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    alter table T_USERS 
        add constraint UK_kbdgs6v1gu1pcoq5u9ohje6ep unique (EMAIL);
