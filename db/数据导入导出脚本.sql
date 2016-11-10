--在服务器端执行导出脚本
expdp ams/tsystems directory=dpdata dumpfile=20161109.dmp schemas=ams logfile=ams_expdp.log


--在服务器端执行导入命令，其中dpdata是通过select * from dba_directories;语句查询得到的默认文件目录中的一个，dmp文件需要拷贝到dpdata目录下
impdp ams/tsystems directory=dpdata dumpfile=20161109.dmp

--注意：若尚未建立数据库和表空间，则需要先建立

--创建临时表空间，其中的tempfile根据实际数据库在服务器上的安装路径修改
create temporary tablespace TS_AMS_DATA_TMP  
tempfile 'D:\app\Administrator\oradata\orcl\ams_tmp.dbf' 
size 50m  
autoextend on  
next 50m maxsize 20480m/unlimited  
extent management local;  
 
--创建数据表空间，其中的datafile根据实际数据库在服务器上的安装路径修改
create tablespace TS_AMS_DATA  
logging  
datafile 'D:\app\Administrator\oradata\orcl\ams_data.dbf' 
size 50m  
autoextend on  
next 50m maxsize 20480m/unlimited  
extent management local;  

--创建ams用户
create user AMS
 	identified by tsystems
  default tablespace TS_AMS_DATA
  temporary tablespace TS_AMS_DATA_TMP
  profile DEFAULT
  password expire
  quota unlimited on ts_ams_data;
-- Grant/Revoke role privileges 
grant aq_administrator_role to AMS;
grant aq_user_role to AMS;
grant connect to AMS with admin option;
grant dba to AMS;
grant resource to AMS with admin option;
-- Grant/Revoke system privileges 
grant alter system to AMS;
grant create database link to AMS;
grant create procedure to AMS;
grant create role to AMS;
grant create session to AMS with admin option;
grant create synonym to AMS;
grant create table to AMS;
grant create trigger to AMS;
grant create view to AMS;
grant unlimited tablespace to AMS with admin option;



