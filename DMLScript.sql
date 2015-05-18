use bank4_db;
--stores admin and employee details
INSERT INTO `bank4_db`.`user_acnt`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(90000001,'BNK490000001','aachen','',0,'admin1@bank4.com',1,'Admin1','Admin1','$2a$10$P4vuRW91QpF2UyU61zRTG.dfojlSfQ0TzrTHo8m2zLRFvuPdyzbwe','','+491111111111');
INSERT INTO `bank4_db`.`user_acnt`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(90000002,'BNK490000002','aachen','',0,'admin2@bank4.com',1,'Admin2','Admin2','$2a$10$vQqUycQyFy7erQr1wpHD9eaIHlOpVluPadHDlVUEcmbTA5HOh/gZ2','','+491111111111');
INSERT INTO `bank4_db`.`user_acnt`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(90000003,'BNK490000003','aachen','',0,'admin3@bank4.com',1,'Admin3','Admin3','$2a$10$6eB66kZs0dvqiIqyBYrT2..wVTmn2Ko6Gs8j9zHHdhnumu3Ftz.HW','','+491111111111');

INSERT INTO `bank4_db`.`user_acnt`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(94000001,'BNK494000001','aachen','',0,'emp1@bank4.com',1,'Employee1','Employee1','$2a$10$Wf0KT4suPbiwjo0KzS9cXOiRQR32K815aEuUo79BKgN3g3hDXrA4i','','+491111111111');
INSERT INTO `bank4_db`.`user_acnt`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(94000002,'BNK494000002','aachen','',0,'emp2@bank4.com',1,'Employee2','Employee2','$2a$10$i5arbFfal9K2jBOpQDP7hesOtQuhklQiVFgURC3CJ3MkKdLY4itlm','','+491111111111');
INSERT INTO `bank4_db`.`user_acnt`(`Id`,`UA_ACNT_ID`,`UA_ADDRESS`,`UA_ATM_PIN`,`UA_BALANCE`,`UA_EMAIL`,`UA_ENABLED`,`UA_FNAME`,`UA_LNAME`,`UA_ONLN_PIN`,`UA_PASSWD`,`UA_PHN_NO`)
VALUES(94000003,'BNK494000003','aachen','',0,'emp3@bank4.com',1,'Employee3','Employee3','$2a$10$yEsnf8fNfasBPesjh6KAGOSrloM6xmtAvtrAfYafSt2Zsl88BJjS2','','+491111111111');

--stores cash details for atms of our bank and corresponding account numbers.
insert into cash_dtls values (1,'BNK494000001',10000000,'ATM1','CR');
insert into cash_dtls values (2,'BNK494000002',10000000,'ATM2','CR');
insert into cash_dtls values (3,'BNK494000003',10000000,'ATM3','CR');

--stores cash details for each bank each identified by a unique account no
insert into cash_dtls values (101,'BNK491000000',0,'Bank1','CR');
insert into cash_dtls values (102,'BNK492000000',0,'Bank2','CR');
insert into cash_dtls values (103,'BNK493000000',0,'Bank4','CR');
insert into cash_dtls values (104,'BNK494000000',100000000,'Bank4','CR');
insert into cash_dtls values (105,'BNK495000000',0,'Bank5','CR');
insert into cash_dtls values (106,'BNK496000000',0,'Bank6','CR');
insert into cash_dtls values (107,'BNK497000000',0,'Bank7','CR');
insert into cash_dtls values (108,'BNK498000000',0,'Bank8','CR');
insert into cash_dtls values (109,'BNK499000000',0,'Bank9','CR');

--different roles allowed in our system
insert into role values (1,"ADMN");
insert into role values (2,"EMPL");
insert into role values (3,"CUST");
insert into role values (4,"BANK");

--contains mapping for default admin and employee accounts
insert into acnt_role values (1,'BNK490000001','ADMN',1);
insert into acnt_role values (2,'BNK490000002','ADMN',1);
insert into acnt_role values (3,'BNK490000003','ADMN',1);
insert into acnt_role values (4,'BNK494000001','EMPL',2);
insert into acnt_role values (5,'BNK494000002','EMPL',2);
insert into acnt_role values (6,'BNK494000003','EMPL',2);

--contains mapping of account and role
insert into acnt_role values (7,'BNK491000000','BANK',4);
insert into acnt_role values (8,'BNK492000000','BANK',4);
insert into acnt_role values (9,'BNK493000000','BANK',4);
insert into acnt_role values (10,'BNK495000000','BANK',4);
insert into acnt_role values (11,'BNK496000000','BANK',4);
insert into acnt_role values (12,'BNK497000000','BANK',4);
insert into acnt_role values (13,'BNK498000000','BANK',4);
insert into acnt_role values (14,'BNK499000000','BANK',4);

commit;