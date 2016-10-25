DROP SEQUENCE SEQ_AF_RULE_TYPE;

CREATE SEQUENCE SEQ_AF_RULE_TYPE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;
  
DROP SEQUENCE SEQ_AF_RULE;

CREATE SEQUENCE SEQ_AF_RULE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;  

DROP SEQUENCE SEQ_AF_RELATION;

CREATE SEQUENCE SEQ_AF_RELATION
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_OBJ_INFO;

CREATE SEQUENCE SEQ_AF_OBJ_INFO
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_ATTACH;

CREATE SEQUENCE SEQ_AF_ATTACH
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;
  
drop sequence SEQ_AF_CCATE;

create sequence SEQ_AF_CCATE
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 minvalue 1
nocycle
 cache 20
order;

drop sequence SEQ_AF_CODES;

create sequence SEQ_AF_CODES
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 minvalue 1
nocycle
 cache 20
order;

drop sequence SEQ_AF_IMPORT_CONFIG;
CREATE SEQUENCE SEQ_AF_IMPORT_CONFIG
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

drop sequence SEQ_AF_IMPORT_CONFIG_LOG;  
CREATE SEQUENCE SEQ_AF_IMPORT_CONFIG_LOG
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;  

drop table AF_RULE_TYPE cascade constraints;

drop table AF_RULE cascade constraints;

drop table AF_RELATION cascade constraints;

drop table AF_OBJ_INFO cascade constraints;

drop table AF_ATTACH cascade constraints;

alter table AF_CODES
   drop constraint FK_CODES_REFERENCE_CCATE;

drop table AF_CCATE cascade constraints;

drop table AF_CODES cascade constraints;

drop table AF_IMPORT_CONFIG cascade constraints;

drop table AF_IMPORT_CONFIG_LOG cascade constraints;

/*==============================================================*/
/* Table: AF_RULE_TYPE                                       */
/*==============================================================*/
create table AF_RULE_TYPE
(
  ID           INTEGER not null,
  NAME         VARCHAR2(50) not null,
  OBJ_IDS 		   VARCHAR2(1000),
  OBJ_TYPES 		   VARCHAR2(1000),
  REMOVED      INTEGER default 0 not null,
  constraint PK_AF_RULE_TYPE primary key (ID)
);
comment on table AF_RULE_TYPE is
'关联规则类型表';
comment on column AF_RULE_TYPE.NAME is
'规则名';
comment on column AF_RULE_TYPE.OBJ_IDS is
'包含对象ID';
comment on column AF_RULE_TYPE.OBJ_TYPES is
'包含对象类型';

/*==============================================================*/
/* Table: AF_RULE                                       */
/*==============================================================*/
create table AF_RULE
(
  ID      INTEGER not null,
  RULE_TYPE_ID    INTEGER not null,
  P_OBJ_TYPE  VARCHAR2(50) not null,
  N_OBJ_TYPE  VARCHAR2(50) not null,  
  P_OBJ_ID  INTEGER not null,
  N_OBJ_ID  INTEGER not null,  
  removed INTEGER default 0 not null,
  constraint PK_AF_RULE primary key (ID)
);
comment on table AF_RULE is
'关联规则表';

comment on column AF_RULE.RULE_TYPE_ID is
'规则类型ID';

comment on column AF_RULE.P_OBJ_TYPE is
'前置对象（主对象）类型';

comment on column AF_RULE.N_OBJ_TYPE is
'后置对象（从属对象）类型';

comment on column AF_RULE.P_OBJ_ID is
'前置对象（主对象）ID';

comment on column AF_RULE.N_OBJ_ID is
'后置对象（从属对象）ID';

/*==============================================================*/
/* Table: AF_OBJ_RELATION                                       */
/*==============================================================*/
create table AF_RELATION 
(
   ID                   INTEGER              not null,
   P_TYPE               VARCHAR2(50)  not null,
   P_ID                 INTEGER  not null,
   N_TYPE               VARCHAR2(50)  not null,
   N_ID                 INTEGER  not null,
   RULE_TYPE_ID         INTEGER  not null,   
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_RELATION primary key (ID)
);

comment on table AF_RELATION is
'业务对象关系表';
comment on column AF_RELATION.P_TYPE is
'前置对象类型';
comment on column AF_RELATION.P_ID is
'前置对象记录ID';
comment on column AF_RELATION.N_TYPE is
'后置对象类型';
comment on column AF_RELATION.N_ID is
'后置对象ID';
comment on column AF_RELATION.RULE_TYPE_ID is
'规则类型ID';

/*==============================================================*/
/* Table: AF_OBJ_INFO                                */
/*==============================================================*/
create table AF_OBJ_INFO 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)  not null,
   TYPE                 VARCHAR2(50)  not null,
   PARAMS               VARCHAR2(500)  not null,
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_OBJ_INFO primary key (ID)
);

comment on table AF_OBJ_INFO is
'对象信息表';
comment on column AF_OBJ_INFO.NAME is
'对象名称';
comment on column AF_OBJ_INFO.TYPE is
'对象类型';
comment on column AF_OBJ_INFO.PARAMS is
'对象参数,json格式，记录对象实体类全路径';
/*==============================================================*/
/* Table: AF_ATTACH                                */
/*==============================================================*/

create table AF_ATTACH
(
  ID                  INTEGER not null,
  FILE_NAME           VARCHAR2(200),
  EXT_NAME             VARCHAR2(10),
  FILE_PATH           VARCHAR2(200),
  FILE_SIZE           VARCHAR2(10),
  UPLOADER            VARCHAR2(20),
  UPLOAD_DATE         DATE,
  FILE_TYPE           VARCHAR2(20),
  MEMO                VARCHAR2(200),
  VERSION             VARCHAR2(10),
  KEY_WORD            VARCHAR2(200),
  MODEL_NAME          VARCHAR2(50),
  MODEL_ID            VARCHAR2(50),
  GROUP_NAME  VARCHAR2(50),
  REMOVED             INTEGER default(0) not null,  
  constraint PK_AF_ATTACH primary key (ID)
);
comment on table AF_ATTACH is
'附件表';
comment on column AF_ATTACH.FILE_NAME is
'文件名';
comment on column AF_ATTACH.EXT_NAME is
'扩展名';
comment on column AF_ATTACH.FILE_PATH is
'文件存储路径';
comment on column AF_ATTACH.FILE_SIZE is
'文件大小';
comment on column AF_ATTACH.UPLOADER is
'上传人';
comment on column AF_ATTACH.UPLOAD_DATE is
'上传时间';
comment on column AF_ATTACH.FILE_TYPE is
'文件类型';
comment on column AF_ATTACH.MEMO is
'备注';
comment on column AF_ATTACH.VERSION is
'版本';
comment on column AF_ATTACH.KEY_WORD is
'关键字';
comment on column AF_ATTACH.MODEL_NAME is
'模块（对象）名';
comment on column AF_ATTACH.MODEL_ID is
'模块（对象记录）ID';
comment on column AF_ATTACH.GROUP_NAME is
'组名';
/*==============================================================*/
/* Table: AF_CCATE                                                 */
/*==============================================================*/
create table AF_CCATE 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR2(50) not null,
   NAME                 VARCHAR2(50) not null,
   DESCRIPTION          VARCHAR2(100),
   REMOVED              INTEGER default 0,
   constraint PK_AF_CCATE primary key (ID)
);

comment on table AF_CCATE is
'数据编码类';
comment on column AF_CCATE.NAME is
'编码类名称';
comment on column AF_CCATE.TYPE is
'编码类类型';
comment on column AF_CCATE.DESCRIPTION is
'编码类描述';
/*==============================================================*/
/* Table: AF_CODES                                                 */
/*==============================================================*/
create table AF_CODES 
(
   ID                   INTEGER              not null,
   CCATE_ID             INTEGER not null,
   PCODE                VARCHAR2(50),
   CODE                 VARCHAR2(50) not null,
   DISPLAY              VARCHAR2(200),
   DESCRIPTION          VARCHAR2(200) not null,
   ORDERS               INTEGER,   
   REMOVED              INTEGER default 0,
   constraint PK_AF_CODES primary key (ID)
);

comment on table AF_CODES is
'数据编码项';
comment on column AF_CODES.CCATE_ID is
'编码类ID';
comment on column AF_CODES.PCODE is
'父编码值';
comment on column AF_CODES.CODE is
'编码值';
comment on column AF_CODES.DISPLAY is
'展示值';
comment on column AF_CODES.DESCRIPTION is
'编码项描述';
comment on column AF_CODES.ORDERS is
'排序';
alter table AF_CODES
   add constraint FK_CODES_REFERENCE_CCATE foreign key (CCATE_ID)
      references AF_CCATE (ID);
      
/*==============================================================*/
/* Table: AF_IMPORT_CONFIG                                                 */
/*==============================================================*/      
create table AF_IMPORT_CONFIG
(
   ID INTEGER NOT NULL,
   ENTITY VARCHAR2(20) NOT NULL,
   ENTITY_FIELD VARCHAR2(50) NOT NULL,
   EXCEL_FIELD VARCHAR2(50) NOT NULL,
   TYPE VARCHAR2(20) NOT NULL,
   HAS_TITLE INTEGER NOT NULL,   
   REMOVED INTEGER DEFAULT(0) NOT NULL,
   constraint PK_AF_IMPORT_CONFIG primary key (ID)
);

comment on table AF_IMPORT_CONFIG is
'导入配置表';
comment on column AF_IMPORT_CONFIG.ENTITY is
'对象名';
comment on column AF_IMPORT_CONFIG.ENTITY_FIELD is
'对象属性字段';
comment on column AF_IMPORT_CONFIG.EXCEL_FIELD is
'Excel字段';
comment on column AF_IMPORT_CONFIG.TYPE is
'区分类型';
comment on column AF_IMPORT_CONFIG.HAS_TITLE is
'Excel是否有抬头';

/*==============================================================*/
/* Table: AF_IMPORT_CONFIG_LOG                                                 */
/*==============================================================*/      
create table AF_IMPORT_CONFIG_LOG
(
     ID INTEGER not null,
     ORIGINAL_FILENAME VARCHAR2(200),
     SAVE_FILENAME VARCHAR2(200),
     SAVE_PATH VARCHAR2(500),
     FILETYPE VARCHAR2(10),
     RECORD_NUM INTEGER ,
     FLAG VARCHAR2(10),
     IMPORT_TIME DATE,
     REMOVED INTEGER DEFAULT(0) NOT NULL,
     constraint PK_AF_IMPORT_CONFIG_LOG primary key (ID)
);

comment on table AF_IMPORT_CONFIG_LOG is
'导入日志表';
comment on column AF_IMPORT_CONFIG_LOG.ORIGINAL_FILENAME is
'源文件名';
comment on column AF_IMPORT_CONFIG_LOG.SAVE_FILENAME is
'保存文件名';
comment on column AF_IMPORT_CONFIG_LOG.SAVE_PATH is
'保存路径';
comment on column AF_IMPORT_CONFIG_LOG.FILETYPE is
'文件扩展名';
comment on column AF_IMPORT_CONFIG_LOG.RECORD_NUM is
'导入记录数';
comment on column AF_IMPORT_CONFIG_LOG.FLAG is
'标志位';
comment on column AF_IMPORT_CONFIG_LOG.IMPORT_TIME is
'导入时间';    
      
      
DROP SEQUENCE SEQ_AF_USER;

CREATE SEQUENCE SEQ_AF_USER
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_ROLE;

CREATE SEQUENCE SEQ_AF_ROLE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_ORGAN;

CREATE SEQUENCE SEQ_AF_ORGAN
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_GROUP;

CREATE SEQUENCE SEQ_AF_GROUP
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_PRIVILEGE;

CREATE SEQUENCE SEQ_AF_PRIVILEGE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_RESOURCE;

CREATE SEQUENCE SEQ_AF_RESOURCE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;


drop table AF_GROUP cascade constraints;

drop table AF_ORGAN cascade constraints;

drop table AF_PRIVILEGE cascade constraints;

drop table AF_RESOURCE cascade constraints;

drop table AF_ROLE cascade constraints;

drop table AF_USER cascade constraints;

/*==============================================================*/
/* Table: AF_GROUP                                              */
/*==============================================================*/
create table AF_GROUP 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50),
   TYPE                 VARCHAR2(50),--groupType:用户组、角色组
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_GROUP primary key (ID)
);
alter table AF_GROUP add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_ORGAN                                              */
/*==============================================================*/
create table AF_ORGAN 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   TYPE                 VARCHAR2(50),--organType:单位、部门
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_ORGAN primary key (ID)
);
alter table AF_ORGAN add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_PRIVILEGE                                          */
/*==============================================================*/
create table AF_PRIVILEGE 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   TYPE                 VARCHAR2(50),--privilegeType:查看、新增、修改、删除、所有权限、无权限
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_PRIVILEGE primary key (ID)
);
alter table AF_PRIVILEGE add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_RESOURCE                                           */
/*==============================================================*/
create table AF_RESOURCE 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   TYPE                 VARCHAR2(50),--页面（菜单）、模块、功能、数据（字典项）
   PATH                 VARCHAR2(200),
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_RESOURCE primary key (ID)
);
alter table AF_RESOURCE add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_ROLE                                               */
/*==============================================================*/
create table AF_ROLE 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   TYPE                 VARCHAR2(50),--一般用户、流程用户
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_ROLE primary key (ID)
);
alter table AF_ROLE add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_USER                                               */
/*==============================================================*/
create table AF_USER 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   LOGIN_NAME           VARCHAR2(50),
   PASSWORD             VARCHAR2(50),
   MOBILE1              NUMBER(11,0),
   MOBILE2              NUMBER(11,0),
   TELEPHONE            VARCHAR2(20),
   EMAIL                VARCHAR2(50),
   GENDER               VARCHAR2(10),
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_USER primary key (ID)
);





delete from af_codes where ccate_id in(select id from af_ccate where type='gender');
delete from af_ccate where type='gender'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'gender','性别','性别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'male','男','男' ,1 from af_ccate where type='gender';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'female','女','女' ,2 from af_ccate where type='gender';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'unknow','未知','未知' ,3 from af_ccate where type='gender';   


delete from af_codes where ccate_id in(select id from af_ccate where type='groupType');
delete from af_ccate where type='groupType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'groupType','组类别','组类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'user','用户组','用户组' ,1 from af_ccate where type='groupType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'role','角色组','角色组' ,2 from af_ccate where type='groupType';   



delete from af_codes where ccate_id in(select id from af_ccate where type='organType');
delete from af_ccate where type='organType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'organType','机构类别','机构类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'unit','单位','单位' ,1 from af_ccate where type='organType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'dept','部门','部门' ,2 from af_ccate where type='organType';   


delete from af_codes where ccate_id in(select id from af_ccate where type='privilegeType');
delete from af_ccate where type='privilegeType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'privilegeType','权限类别','权限类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'view','查看','查看' ,1 from af_ccate where type='privilegeType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'add','新增','新增' ,2 from af_ccate where type='privilegeType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'update','修改','修改' ,3 from af_ccate where type='privilegeType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'del','删除','删除' ,4 from af_ccate where type='privilegeType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'all','所有权限','所有权限' ,5 from af_ccate where type='privilegeType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'none','无权限','无权限' ,6 from af_ccate where type='privilegeType';

delete from af_codes where ccate_id in(select id from af_ccate where type='roleType');
delete from af_ccate where type='roleType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'roleType','角色类别','角色类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'general','一般角色','一般角色' ,1 from af_ccate where type='roleType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'workflow','工作流角色','工作流角色' ,2 from af_ccate where type='roleType';   


delete from af_codes where ccate_id in(select id from af_ccate where type='resourceType');
delete from af_ccate where type='resourceType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'resourceType','资源类别','资源类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'menu','菜单','菜单' ,1 from af_ccate where type='resourceType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'module','模块','模块' ,2 from af_ccate where type='resourceType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'function','功能','功能' ,3 from af_ccate where type='resourceType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'data','数据','数据' ,4 from af_ccate where type='resourceType';


insert into AF_OBJ_INFO (id, name, type, params, removed)
values (1, '组信息', 'group', '{"entity":"com.wonders.frame.core.model.bo.Group"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (2, '组织机构信息', 'organ', '{"entity":"com.wonders.frame.core.model.bo.Organ"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (3, '权限信息', 'privilege', '{"entity":"com.wonders.frame.core.model.bo.Privilege"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (4, '资源信息', 'resource', '{"entity":"com.wonders.frame.core.model.bo.Resource"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (5, '用户信息', 'user', '{"entity":"com.wonders.frame.core.model.bo.User"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (6, '角色信息', 'role', '{"entity":"com.wonders.frame.core.model.bo.Role"}', 0);
commit;


insert into af_rule_type(id,name,obj_ids,obj_types,removed) values(1,'ams','1,2,3,4,5,6','group,organ,privilege,resource,user,role',0);
     
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(1,1,'role','user',6,5,0);

insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(1,1,'role','resource',6,4,0);


insert into af_user(id,name,login_name,password,removed) values(1,'财务用户','cw','test',0);
insert into af_user(id,name,login_name,password,removed) values(2,'汽服用户','qf','test',0);
insert into af_user(id,name,login_name,password,removed) values(3,'廊桥用户','lq','test',0);
insert into af_user(id,name,login_name,password,removed) values(4,'舱单用户','cd','test',0);
insert into af_user(id,name,login_name,password,removed) values(5,'VIP用户','vip','test',0);


insert into af_user(id,name,login_name,password,removed) values(6,'汽服用户2','qf2','test',0);
insert into af_user(id,name,login_name,password,removed) values(7,'廊桥用户2','lq2','test',0);
insert into af_user(id,name,login_name,password,removed) values(8,'舱单用户2','cd2','test',0);
insert into af_user(id,name,login_name,password,removed) values(9,'VIP用户2','vip2','test',0);

insert into af_user(id,name,login_name,password,removed) values(10,'汽服用户3','qf3','test',0);
insert into af_user(id,name,login_name,password,removed) values(11,'廊桥用户3','lq3','test',0);
insert into af_user(id,name,login_name,password,removed) values(12,'舱单用户3','cd3','test',0);
insert into af_user(id,name,login_name,password,removed) values(13,'VIP用户3','vip3','test',0);


insert into af_role(id,name,description,removed) values(1,'财务','财务角色',0);
insert into af_role(id,name,description,removed) values(2,'汽服','汽服角色',0);
insert into af_role(id,name,description,removed) values(3,'廊桥','廊桥角色',0);
insert into af_role(id,name,description,removed) values(4,'舱单','舱单角色',0);
insert into af_role(id,name,description,removed) values(5,'VIP','VIP角色',0);

insert into af_resource(id,name,type,path,removed) values(1,'财务资源','menu','cw',0);
insert into af_resource(id,name,type,path,removed) values(2,'汽服资源','menu','qf',0);
insert into af_resource(id,name,type,path,removed) values(3,'廊桥资源','menu','lq',0);
insert into af_resource(id,name,type,path,removed) values(4,'舱单资源','menu','cd',0);
insert into af_resource(id,name,type,path,removed) values(5,'VIP资源','menu','vip',0);

insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(1,'role',1,'user',1,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(2,'role',2,'user',2,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(3,'role',3,'user',3,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(4,'role',4,'user',4,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(5,'role',5,'user',5,1,0);


insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(6,'role',2,'user',6,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(7,'role',3,'user',7,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(8,'role',4,'user',8,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(9,'role',5,'user',9,1,0);



insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(10,'role',2,'user',10,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(11,'role',3,'user',11,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(12,'role',4,'user',12,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(13,'role',5,'user',13,1,0);


insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(14,'role',1,'resource',1,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(15,'role',2,'resource',2,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(16,'role',3,'resource',3,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(17,'role',4,'resource',4,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(18,'role',5,'resource',5,1,0);