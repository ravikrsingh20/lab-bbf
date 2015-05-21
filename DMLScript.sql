use bank4_db;
--stores admin and employee details
INSERT INTO `bank4_db`.`USER_ACNT`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(1,'BNK490000001','aachen','',0,'admin1@bank4.com',1,'Admin1','Admin1','$2a$10$NoAhK5alkA82BGStGNc.u.mQRxPvF0SjOt9bMmIVvJZSXRNGm6j52','','+491111111111');
INSERT INTO `bank4_db`.`USER_ACNT`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(2,'BNK490000002','aachen','',0,'admin2@bank4.com',1,'Admin2','Admin2','$2a$10$MKH8OvDK.y6qjV2aHEfvgeaRMDZAQZoRTuygMopqPuW5b85iGXr/e','','+491111111111');
INSERT INTO `bank4_db`.`USER_ACNT`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(3,'BNK490000003','aachen','',0,'admin3@bank4.com',1,'Admin3','Admin3','$2a$10$NRccmjXNfkFMc4wkUDDC5eXxeN6XUTPaSHnZpyXhnc91hMyfDI06.','','+491111111111');

INSERT INTO `bank4_db`.`USER_ACNT`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(4,'BNK494000001','aachen','',0,'emp1@bank4.com',1,'Employee1','Employee1','$2a$10$9mN2KvVkiHqLffv9BP9Y7eodclGx9GNV4DjDdnqqRTBRjIH5udCx.','','+491111111111');
INSERT INTO `bank4_db`.`USER_ACNT`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(5,'BNK494000002','aachen','',0,'emp2@bank4.com',1,'Employee2','Employee2','$2a$10$oVEzAqSADF7dNuQLNvTpPu/JQQxM8BV96ehuiZL5sOyS614P9IbiC','','+491111111111');
INSERT INTO `bank4_db`.`USER_ACNT`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(6,'BNK494000003','aachen','',0,'emp3@bank4.com',1,'Employee3','Employee3','$2a$10$Qk.UeMG/4YW3IEc4pXsDMOyMuf4s7uMOBbe/4G1kmJ5Iye74vfQwK','','+491111111111');

--stores cash details for atms of our bank and corresponding account numbers.
insert into CASH_DTLS values (1,'BNK494000001',10000000,'ATM1','CR');
insert into CASH_DTLS values (2,'BNK494000002',10000000,'ATM2','CR');
insert into CASH_DTLS values (3,'BNK494000003',10000000,'ATM3','CR');

--stores cash details for each bank each identified by a unique account no
insert into CASH_DTLS values (101,'BNK491000000',0,'Bank1','CR');
insert into CASH_DTLS values (102,'BNK492000000',0,'Bank2','CR');
insert into CASH_DTLS values (103,'BNK493000000',0,'Bank3','CR');
insert into CASH_DTLS values (104,'BNK494000000',100000000,'Bank4','CR');
insert into CASH_DTLS values (105,'BNK495000000',0,'Bank5','CR');
insert into CASH_DTLS values (106,'BNK496000000',0,'Bank6','CR');
insert into CASH_DTLS values (107,'BNK497000000',0,'Bank7','CR');
insert into CASH_DTLS values (108,'BNK498000000',0,'Bank8','CR');
insert into CASH_DTLS values (109,'BNK499000000',0,'Bank9','CR');

--different ROLEs allowed in our system
insert into ROLE values (1,"ADMN");
insert into ROLE values (2,"EMPL");
insert into ROLE values (3,"CUST");
insert into ROLE values (4,"BANK");

--contains mapping for default admin and employee accounts
insert into ACNT_ROLE values (1,'BNK490000001','ADMN',1);
insert into ACNT_ROLE values (2,'BNK490000002','ADMN',1);
insert into ACNT_ROLE values (3,'BNK490000003','ADMN',1);
insert into ACNT_ROLE values (4,'BNK494000001','EMPL',2);
insert into ACNT_ROLE values (5,'BNK494000002','EMPL',2);
insert into ACNT_ROLE values (6,'BNK494000003','EMPL',2);

--contains mapping of account and ROLE
insert into ACNT_ROLE values (7,'BNK491000000','BANK',4);
insert into ACNT_ROLE values (8,'BNK492000000','BANK',4);
insert into ACNT_ROLE values (9,'BNK493000000','BANK',4);
insert into ACNT_ROLE values (10,'BNK495000000','BANK',4);
insert into ACNT_ROLE values (11,'BNK496000000','BANK',4);
insert into ACNT_ROLE values (12,'BNK497000000','BANK',4);
insert into ACNT_ROLE values (13,'BNK498000000','BANK',4);
insert into ACNT_ROLE values (14,'BNK499000000','BANK',4);

commit;