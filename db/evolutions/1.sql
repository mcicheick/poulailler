
    drop table if exists T_CLIENTS;

    create table T_CLIENTS (
        ID bigint not null auto_increment unique,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        FIRST_NAME varchar(255),
        LAST_NAME varchar(255),
        PHONE varchar(255),
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
