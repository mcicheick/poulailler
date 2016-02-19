
    alter table T_BANDES 
        drop 
        foreign key FK_5ypfrlu3qo319vc4g3tsq7uk2;

    alter table T_DESPENSES 
        drop 
        foreign key FK_hbfd73gxy6xdxjn9bd0dahvsn;

    alter table T_OBSERVATIONS 
        drop 
        foreign key FK_hhnx7r1h056cm33s7lftnh9e6;

    alter table T_PAYMENTS 
        drop 
        foreign key FK_rlpfj2yl7ye623q4feuao0a66;

    alter table T_TRANSACTIONS 
        drop 
        foreign key FK_pylf9piihf811chqcfdoihgx3;

    alter table T_TRANSACTIONS 
        drop 
        foreign key FK_mvb43twt3f27w3e92079iry7y;

    drop table if exists T_BANDES;

    drop table if exists T_CLIENTS;

    drop table if exists T_DESPENSES;

    drop table if exists T_OBSERVATIONS;

    drop table if exists T_PAYMENTS;

    drop table if exists T_TRANSACTIONS;

    drop table if exists T_USERS;

    create table T_BANDES (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        ARRIVED_DATE datetime not null,
        DISEASE integer,
        INITIAL_COUNT integer,
        PRICE double precision,
        REMAIN_COUNT integer,
        USER_ID bigint,
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table T_CLIENTS (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        ADDRESS varchar(255),
        FIRST_NAME varchar(255),
        LAST_NAME varchar(255),
        PHONE varchar(255) not null,
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table T_DESPENSES (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        AMOUNT double precision,
        DEPENSE_DATE datetime,
        DESCRIPTION longtext,
        bande_ID bigint,
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table T_OBSERVATIONS (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        DESCRIPTION longtext,
        bande_ID bigint,
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table T_PAYMENTS (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        AMOUNT double precision,
        PAYMENT_DATE datetime,
        TRANSACTION_ID bigint,
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table T_TRANSACTIONS (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        PRICE_BY_KILO double precision,
        QUANTITY double precision,
        TRANSACTION_DATE datetime not null,
        UNIT_PRICE double precision,
        WEIGHT double precision,
        BANDE_ID bigint,
        CLIENT_ID bigint,
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table T_USERS (
        ID bigint not null auto_increment,
        CREATE_DATE datetime not null,
        MODIFY_DATE datetime not null,
        EMAIL varchar(255) not null,
        FIRST_NAME varchar(255),
        LAST_NAME varchar(255),
        PASSWORD varchar(255),
        primary key (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    alter table T_USERS 
        add constraint UK_kbdgs6v1gu1pcoq5u9ohje6ep unique (EMAIL);

    create index EMAILS_INDEXES on T_USERS (EMAIL);

    alter table T_BANDES 
        add index FK_5ypfrlu3qo319vc4g3tsq7uk2 (USER_ID), 
        add constraint FK_5ypfrlu3qo319vc4g3tsq7uk2 
        foreign key (USER_ID) 
        references T_USERS (ID);

    alter table T_DESPENSES 
        add index FK_hbfd73gxy6xdxjn9bd0dahvsn (bande_ID), 
        add constraint FK_hbfd73gxy6xdxjn9bd0dahvsn 
        foreign key (bande_ID) 
        references T_BANDES (ID);

    alter table T_OBSERVATIONS 
        add index FK_hhnx7r1h056cm33s7lftnh9e6 (bande_ID), 
        add constraint FK_hhnx7r1h056cm33s7lftnh9e6 
        foreign key (bande_ID) 
        references T_BANDES (ID);

    alter table T_PAYMENTS 
        add index FK_rlpfj2yl7ye623q4feuao0a66 (TRANSACTION_ID), 
        add constraint FK_rlpfj2yl7ye623q4feuao0a66 
        foreign key (TRANSACTION_ID) 
        references T_TRANSACTIONS (ID);

    alter table T_TRANSACTIONS 
        add index FK_pylf9piihf811chqcfdoihgx3 (BANDE_ID), 
        add constraint FK_pylf9piihf811chqcfdoihgx3 
        foreign key (BANDE_ID) 
        references T_BANDES (ID);

    alter table T_TRANSACTIONS 
        add index FK_mvb43twt3f27w3e92079iry7y (CLIENT_ID), 
        add constraint FK_mvb43twt3f27w3e92079iry7y 
        foreign key (CLIENT_ID) 
        references T_CLIENTS (ID);
