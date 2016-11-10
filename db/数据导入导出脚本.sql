--�ڷ�������ִ�е����ű�
expdp ams/tsystems directory=dpdata dumpfile=20161109.dmp schemas=ams logfile=ams_expdp.log


--�ڷ�������ִ�е����������dpdata��ͨ��select * from dba_directories;����ѯ�õ���Ĭ���ļ�Ŀ¼�е�һ����dmp�ļ���Ҫ������dpdataĿ¼��
impdp ams/tsystems directory=dpdata dumpfile=20161109.dmp

--ע�⣺����δ�������ݿ�ͱ�ռ䣬����Ҫ�Ƚ���

--������ʱ��ռ䣬���е�tempfile����ʵ�����ݿ��ڷ������ϵİ�װ·���޸�
create temporary tablespace TS_AMS_DATA_TMP  
tempfile 'D:\app\Administrator\oradata\orcl\ams_tmp.dbf' 
size 50m  
autoextend on  
next 50m maxsize 20480m/unlimited  
extent management local;  
 
--�������ݱ�ռ䣬���е�datafile����ʵ�����ݿ��ڷ������ϵİ�װ·���޸�
create tablespace TS_AMS_DATA  
logging  
datafile 'D:\app\Administrator\oradata\orcl\ams_data.dbf' 
size 50m  
autoextend on  
next 50m maxsize 20480m/unlimited  
extent management local;  

--����ams�û�
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



