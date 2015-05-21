

BAnk url

http://137.226.112.107:8080/bbf4

Step1 : Install spring toolsuite from below link for IDE to work with
https://spring.io/tools/sts/all

Step 2 : Install xampp for your respective OS from below link. for MySql
https://www.apachefriends.org/download.html
Create a DB user bbf4dba with password P@ssbbf4_dba
create database bank4_db
use bank4_db;
Execute sql statements from DMLScript.sql to setup Database

We have used hibernate so you dont need to create tables but if you want to do manually you can do so we have kept ddlscript in DDLScript.sql
Info about Tables description is given in DDL and DML scripts

Belo are the Account username and password for admin and employee role
Admin Accounts
BNK490000001 - B@nk4P@ssAdm9#1
BNK490000002 - B@nk4P@ssAdm9#2
BNK490000003 - B@nk4P@ssAdm9#3

Employee Accounts
BNK494000001 - B@nk4P@ssEmp9#1
BNK494000002 - B@nk4P@ssEmp9#2
BNK494000003 - B@nk4P@ssEmp9#3

Customer Account you can create by registering in the application . You can initially deposit amount to your account while registering, this will be your initial balance. Please deposit more amount so that you can test 
all debit/credit properly.
We add this amount to our total balance initially.
SQL root pwd
P@ssadm4_dba
mysql --user=bbf4dba --password=P@ssbbf4_dba bank4_db



