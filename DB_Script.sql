create table ACNT_ROLE (
        AR_ID integer not null auto_increment,
        AR_ACNT_ID varchar(12) not null,
        AR_RL_ID integer not null,
        primary key (AR_ID)
    )
Hibernate: 
    create table PERMISSION (
        PR_ID integer not null auto_increment,
        PR_NAME varchar(12) not null,
        primary key (PR_ID)
    )
Hibernate: 
    create table ROLE (
        RL_ID integer not null auto_increment,
        RL_NAME varchar(12) not null,
        primary key (RL_ID)
    )
Hibernate: 
    create table ROLE_PERMISSION (
        RP_ID integer not null auto_increment,
        RP_PR_ID integer not null,
        RP_RL_ID integer not null,
        primary key (RP_ID)
    )
Hibernate: 
    create table USER_ACNT (
        Id bigint not null auto_increment,
        UA_ADDRESS varchar(13),
        UA_ACNT_ID varchar(12) not null,
        UA_ATM_PIN varchar(255),
        UA_BALANCE double precision,
        UA_EMAIL varchar(30) not null,
        UA_ENABLED varchar(255),
        UA_FNAME varchar(20) not null,
        UA_LNAME varchar(20) not null,
        UA_ONLN_PIN varchar(255),
        UA_PASSWD varchar(255),
        UA_PHN_NO varchar(13),
        primary key (Id)
    )