use bank4_db;
drop table if exists ACNT_ROLE
drop table if exists CASH_DTLS
drop table if exists ROLE
drop table if exists TXN_DTLS
drop table if exists USER_ACNT
create table ACNT_ROLE (
        AR_ID integer not null auto_increment,
        AR_ACNT_ID varchar(12) not null,
        AR_RL_NM varchar(4) not null,
        AR_RL_ID integer not null,
        primary key (AR_ID)
    )
create table CASH_DTLS (
        CD_ID integer not null auto_increment,
        CD_ACNT_ID varchar(12),
        CDA_AMT double precision,
        CD_BNK_NAME varchar(20),
        CD_CR_DR varchar(2),
        primary key (CD_ID)
    )
create table ROLE (
        RL_ID integer not null auto_increment,
        RL_NAME varchar(12) not null,
        primary key (RL_ID)
    )
create table TXN_DTLS (
        TXN_ID bigint not null auto_increment,
        TXN_ATM_NM varchar(255),
        TXN_EXEC_DT datetime,
        TXN_ORD_DT datetime,
        TXN_ACNT_ID varchar(255),
        TXN_AMT double precision,
        TXN_CR_DR_ACNT_ID varchar(255),
        TXN_CR_DR_BNK_NM varchar(255),
        TXN_FLG varchar(255),
        TXN_STAT varchar(255),
        TXN_TYP varchar(255),
        primary key (TXN_ID)
    )
create table USER_ACNT (
        Id bigint not null auto_increment,
        UA_ACNT_ID varchar(12),
        UA_ADDRESS varchar(30),
        UA_ATM_PIN varchar(255),
        UA_BALANCE double precision,
        UA_EMAIL varchar(30) not null,
        UA_ENABLED bit,
        UA_FNAME varchar(20) not null,
        UA_LNAME varchar(20) not null,
        UA_ONLN_PIN varchar(255),
        UA_PASSWD varchar(255),
        UA_PHN_NO varchar(13),
        primary key (Id)
    )
alter table USER_ACNT 
        add constraint UK_bboqn4ac98ne2w8krq95ao1ot  unique (UA_ACNT_ID)
 alter table USER_ACNT 
        add constraint UK_s4qpx4pcghxisx7hbylqbbs2e  unique (UA_EMAIL)
commit;