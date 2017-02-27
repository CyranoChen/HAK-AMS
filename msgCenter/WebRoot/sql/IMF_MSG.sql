CREATE TABLE MC_MSG_STORE
(
  ID     VARCHAR2(40 BYTE)            NOT NULL, 
  MSG_TYPE   VARCHAR2(10 BYTE),   
  MSG_CONTENT   CLOB,  
  RECEIVER VARCHAR2(20),
  STATUS INTEGER default 0,
  CREATE_TIME     DATE, 
  OPERATE_TIME     DATE,
  REMOVED INTEGER default 0,  
  CONSTRAINT PK_MC_MSG_STORE PRIMARY KEY (ID)
);

alter table mc_msg_store add MessageSequenceID varchar2(20);
alter table mc_msg_store add MessageType varchar2(30);
alter table mc_msg_store add ServiceType varchar2(10);
alter table mc_msg_store add OperationMode varchar2(10);
alter table mc_msg_store add FlightScheduledDate varchar2(10);
alter table mc_msg_store add FlightIdentity varchar2(10);
alter table mc_msg_store add FlightDirection varchar2(5);
alter table mc_msg_store add KEY_STATUS INTEGER default 0;
alter table mc_msg_store add KEY_CONTENT varchar2(300);

update mc_msg_store set key_status=2 where key_status is null and status  in(2,3); 
update mc_msg_store set key_status=0 where key_status is null and status  not in(2,3) and messageSequenceId is null;
update mc_msg_store set key_status=1 where key_status is null and status  not in(2,3) and messageSequenceId is not null; 
update mc_msg_store set key_status=0 where key_status is null and status  not in(2,3) and messageSequenceId is not null and msg_type ='FSS' and receiver='SSMS' and operationMode='MOD'; 

COMMENT ON TABLE MC_MSG_STORE IS 'IMF信息存储中心';

COMMENT ON COLUMN MC_MSG_STORE.ID IS '编号';

COMMENT ON COLUMN MC_MSG_STORE.MSG_TYPE IS '信息类型';

COMMENT ON COLUMN MC_MSG_STORE.MSG_CONTENT IS '信息内容';

COMMENT ON COLUMN MC_MSG_STORE.RECEIVER IS '信息接收者(SSMS/FQDB)';

COMMENT ON COLUMN MC_MSG_STORE.STATUS IS '信息状态(0:未发送；1：已发送)';

COMMENT ON COLUMN MC_MSG_STORE.KEY_STATUS IS '关键字更新状态(0:未更新；1：已更新)';

COMMENT ON COLUMN MC_MSG_STORE.KEY_CONTENT IS '关键字更新内容（json格式）';

COMMENT ON COLUMN MC_MSG_STORE.CREATE_TIME IS '信息创建时间';

COMMENT ON COLUMN MC_MSG_STORE.OPERATE_TIME IS '信息处理时间';

COMMENT ON COLUMN MC_MSG_STORE.REMOVED IS '逻辑删除位';

CREATE TABLE MC_MSG_STORE_HISTORY
(
  ID     VARCHAR2(40 BYTE)            NOT NULL, 
  MSG_TYPE   VARCHAR2(10 BYTE),    
  MSG_CONTENT   CLOB,  
  RECEIVER VARCHAR2(20),
  STATUS INTEGER default 0,  
  CREATE_TIME     DATE, 
  OPERATE_TIME     DATE,
  REMOVED INTEGER default 0,  
  CONSTRAINT PK_MC_MSG_STORE_HISTORY PRIMARY KEY (ID)
);

alter table mc_msg_store_history add MessageSequenceID varchar2(20);
alter table mc_msg_store_history add MessageType varchar2(30);
alter table mc_msg_store_history add ServiceType varchar2(10);
alter table mc_msg_store_history add OperationMode varchar2(10);
alter table mc_msg_store_history add FlightScheduledDate varchar2(10);
alter table mc_msg_store_history add FlightIdentity varchar2(10);
alter table mc_msg_store_history add FlightDirection varchar2(5);
alter table mc_msg_store_history add KEY_STATUS INTEGER default 0;
alter table mc_msg_store_history add KEY_CONTENT varchar2(300);

COMMENT ON TABLE MC_MSG_STORE_HISTORY IS '历史IMF信息存储中心';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.ID IS '编号';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.MSG_TYPE IS '信息类型';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.MSG_CONTENT IS '信息内容';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.RECEIVER IS '信息接收者(SSMS/FQDB)';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.STATUS IS '信息状态(0:未发送；1：已发送)';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.KEY_STATUS IS '关键字更新状态(0:未更新；1：已更新)';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.KEY_CONTENT IS '关键字更新内容（json格式）';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.CREATE_TIME IS '信息创建时间';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.OPERATE_TIME IS '信息处理时间';

COMMENT ON COLUMN MC_MSG_STORE_HISTORY.REMOVED IS '逻辑删除位';

CREATE TABLE MC_MSG_LOG
(
  ID     VARCHAR2(40 BYTE)            NOT NULL,
  STORE_ID   VARCHAR2(40 BYTE),
  OPERATE_TYPE   VARCHAR2(50 BYTE),   
  OPERATE_TIME     DATE, 
  OPERATE_INFO     VARCHAR2(10 BYTE),
  RETURN_MSG  CLOB,  
  CONSTRAINT PK_MC_MSG_LOG PRIMARY KEY (ID)
);

COMMENT ON TABLE MC_MSG_LOG IS 'IMF信息处理日志';

COMMENT ON COLUMN MC_MSG_LOG.ID IS 'MSG编号';

COMMENT ON COLUMN MC_MSG_LOG.STORE_ID IS '信息存储ID';

COMMENT ON COLUMN MC_MSG_LOG.OPERATE_TYPE IS '操作类型（get/send）';

COMMENT ON COLUMN MC_MSG_LOG.OPERATE_TIME IS '操作时间';

COMMENT ON COLUMN MC_MSG_LOG.OPERATE_INFO IS '操作结果（success/failure）';

COMMENT ON COLUMN MC_MSG_LOG.RETURN_MSG IS '返回结果';

CREATE TABLE MC_MSG_LOG_HISTORY
(
  ID     VARCHAR2(40 BYTE)            NOT NULL,
  STORE_ID   VARCHAR2(40 BYTE),
  OPERATE_TYPE   VARCHAR2(50 BYTE),   
  OPERATE_TIME     DATE, 
  OPERATE_INFO     VARCHAR2(10 BYTE),
  RETURN_MSG  CLOB,  
  CONSTRAINT PK_MC_MSG_LOG_HISTORY PRIMARY KEY (ID)
);

COMMENT ON TABLE MC_MSG_LOG_HISTORY IS '历史IMF信息处理日志';

COMMENT ON COLUMN MC_MSG_LOG_HISTORY.ID IS 'MSG编号';

COMMENT ON COLUMN MC_MSG_LOG_HISTORY.STORE_ID IS '信息存储ID';

COMMENT ON COLUMN MC_MSG_LOG_HISTORY.OPERATE_TYPE IS '操作类型（get/send）';

COMMENT ON COLUMN MC_MSG_LOG_HISTORY.OPERATE_TIME IS '操作时间';

COMMENT ON COLUMN MC_MSG_LOG_HISTORY.OPERATE_INFO IS '操作结果（success/failure）';

COMMENT ON COLUMN MC_MSG_LOG_HISTORY.RETURN_MSG IS '返回结果';



CREATE TABLE MC_SCHEDULE_CONFIG
(
  ID           VARCHAR2(50 BYTE)                NOT NULL,
  NAME         VARCHAR2(200 BYTE),
  DESCRIPTION  VARCHAR2(1000 BYTE),
  TIME         VARCHAR2(5 BYTE),
  INTERVAL     VARCHAR2(20 BYTE),
  TYPE         VARCHAR2(20 BYTE),
  METHOD       VARCHAR2(200 BYTE),
  PARAM        VARCHAR2(200 BYTE),
  LAST_TIME    VARCHAR2(19 BYTE),
  REMOVED      VARCHAR2(1 BYTE),
  EXT1         VARCHAR2(200 BYTE),
  EXT2         VARCHAR2(200 BYTE),
  EXT3         VARCHAR2(200 BYTE),
  DATA_SOURCE  VARCHAR2(50 BYTE),
  CONSTRAINT PK_MC_SCHEDULE_CONFIG PRIMARY KEY (ID)
);

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.ID IS '数据唯一标识';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.NAME IS '功能模块名称,bean_id';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.DESCRIPTION IS '模块描述';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.TIME IS '每天固定时间调用 格式为：00:00 - 23:59';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.INTERVAL IS '固定间隔调用 单位为 H  M S ';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.TYPE IS '1:JAVA本地调用；2：web方式调用；3：存储过程调用';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.METHOD IS '1：JAVA类及方法名：2：rest接口地址：3存储过程名字';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.PARAM IS 'method 中的 方法参数 格式为json对象';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.LAST_TIME IS '上一次调用时间';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.REMOVED IS '1—删除，0-使用';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.EXT1 IS '备注';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.EXT2 IS '备注';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.EXT3 IS '备注';

COMMENT ON COLUMN MC_SCHEDULE_CONFIG.DATA_SOURCE IS '数据源 stptinc：老OA  stptdemo：新';                                                    


CREATE TABLE MC_SCHEDULE_LOG
(
  ID         VARCHAR2(50 BYTE)                  NOT NULL,
  NAME       VARCHAR2(200 BYTE),
  EXEC_TIME  VARCHAR2(19 BYTE),
  PROCESS    VARCHAR2(100 BYTE),
  TYPE       VARCHAR2(20 BYTE),
  METHOD     VARCHAR2(200 BYTE),
  PARAM      VARCHAR2(200 BYTE),
  RESULT     VARCHAR2(200 BYTE),
  EXT1       VARCHAR2(200 BYTE),
  EXT2       VARCHAR2(200 BYTE),
  EXT3       VARCHAR2(200 BYTE),
  EXCEPTION  VARCHAR2(2000 BYTE),
  CONSTRAINT PK_MC_SCHEDULE_LOG PRIMARY KEY (ID)
);

COMMENT ON COLUMN MC_SCHEDULE_LOG.ID IS '数据唯一标识';

COMMENT ON COLUMN MC_SCHEDULE_LOG.NAME IS '功能模块名称,bean_id';

COMMENT ON COLUMN MC_SCHEDULE_LOG.EXEC_TIME IS '开始时间';

COMMENT ON COLUMN MC_SCHEDULE_LOG.PROCESS IS '开始 or 结束';

COMMENT ON COLUMN MC_SCHEDULE_LOG.TYPE IS '1:JAVA本地调用；2：web方式调用；3：存储过程调用';

COMMENT ON COLUMN MC_SCHEDULE_LOG.METHOD IS '1：JAVA类及方法名 . 分割 ：2：rest接口地址：3存储过程名字';

COMMENT ON COLUMN MC_SCHEDULE_LOG.PARAM IS 'method 中的 方法参数 格式为json对象';

COMMENT ON COLUMN MC_SCHEDULE_LOG.RESULT IS '成功标志位';

COMMENT ON COLUMN MC_SCHEDULE_LOG.EXT1 IS '备注';

COMMENT ON COLUMN MC_SCHEDULE_LOG.EXT2 IS '备注';

COMMENT ON COLUMN MC_SCHEDULE_LOG.EXT3 IS '备注';

COMMENT ON COLUMN MC_SCHEDULE_LOG.EXCEPTION IS '异常信息';

DROP TABLE MC_API_INFO;
CREATE TABLE MC_API_INFO
(
  ID               VARCHAR2(40 BYTE)            NOT NULL,
  USER_NAME   VARCHAR2(20 BYTE),
  PASSWORD   	VARCHAR2(50 BYTE),
  METHOD_NAME   VARCHAR2(50 BYTE), 
  DATA_TYPE   VARCHAR2(100 BYTE),     
  COMPANY     VARCHAR2(100 BYTE),    
  DATA_NAME    VARCHAR2(100 BYTE),       
  CLIENT_IP    VARCHAR2(20 BYTE),   
  PARAMS    VARCHAR2(1000 BYTE),       
  OPERATE_TIME     TIMESTAMP(6), 
	REMOVED    INTEGER,
  CONSTRAINT PK_MC_API_INFO PRIMARY KEY (ID)
);

COMMENT ON TABLE MC_API_INFO IS '接口信息';

COMMENT ON COLUMN MC_API_INFO.ID IS '编号';

COMMENT ON COLUMN MC_API_INFO.USER_NAME IS '用户名';

COMMENT ON COLUMN MC_API_INFO.PASSWORD IS '密码';

COMMENT ON COLUMN MC_API_INFO.METHOD_NAME IS '可调用接口方法';

COMMENT ON COLUMN MC_API_INFO.DATA_TYPE IS '数据类型';

COMMENT ON COLUMN MC_API_INFO.COMPANY IS '公司名称';

COMMENT ON COLUMN MC_API_INFO.DATA_NAME IS '数据名称';

COMMENT ON COLUMN MC_API_INFO.CLIENT_IP IS '客户端IP';

COMMENT ON COLUMN MC_API_INFO.PARAMS IS '参数';

COMMENT ON COLUMN MC_API_INFO.OPERATE_TIME IS '操作时间';

COMMENT ON COLUMN MC_API_INFO.REMOVED IS '删除标志位';


DROP TABLE MC_API_LOG;
CREATE TABLE MC_API_LOG
(
  ID            VARCHAR2(40 BYTE)            NOT NULL,
  API_ID        VARCHAR2(40 BYTE),   
  CALLER   			VARCHAR2(50 BYTE),
  CALL_WAY   		VARCHAR2(50 BYTE),
  CALL_METHOD   VARCHAR2(50 BYTE), 
  CALL_TIME   	DATE,     
  CALL_RESULT   VARCHAR2(10 BYTE),     
  BACK_INFO   	VARCHAR2(500 BYTE),      
  EXCEPTION  		CLOB,
	REMOVED    		INTEGER default 0,
  CONSTRAINT PK_MC_API_LOG PRIMARY KEY (ID)
);

alter table MC_API_LOG add FINISH_TIME DATE;

COMMENT ON TABLE MC_API_LOG IS '接口日志信息';

COMMENT ON COLUMN MC_API_LOG.ID IS '编号';

COMMENT ON COLUMN MC_API_LOG.API_ID IS 'ApiInfo表的ID';

COMMENT ON COLUMN MC_API_LOG.CALLER IS '调用方，IP/admin/serviceName';

COMMENT ON COLUMN MC_API_LOG.CALL_WAY IS '调用方式，manual(手动)/loop（轮询）/tigger（触发）';

COMMENT ON COLUMN MC_API_LOG.CALL_METHOD IS '调用方法，get(查询)/sync(同步)/autoEngine(自动化引擎)';

COMMENT ON COLUMN MC_API_LOG.CALL_TIME IS '调用时间';

COMMENT ON COLUMN MC_API_LOG.FINISH_TIME IS '调用结束时间';

COMMENT ON COLUMN MC_API_LOG.CALL_RESULT IS '调用结果,success/failure';

COMMENT ON COLUMN MC_API_LOG.BACK_INFO IS '接口返回信息';

COMMENT ON COLUMN MC_API_LOG.EXCEPTION IS '异常信息';

COMMENT ON COLUMN MC_API_LOG.REMOVED IS '删除标志位,1-删除；0-未删除';

----------------------------------------------------初始化语句--------------------------------------------------------------------
truncate table MC_MSG_STORE;
truncate table MC_MSG_STORE_HISTORY;
truncate table MC_MSG_LOG;
truncate table MC_MSG_LOG_HISTORY;
truncate table MC_SCHEDULE_CONFIG;
truncate table MC_SCHEDULE_LOG;
truncate table MC_API_INFO;
truncate table MC_API_LOG;
INSERT INTO MC_SCHEDULE_CONFIG
            (id,name, description,time, interval,type,
             method, last_time,removed
            )
     VALUES (9,'FqdbCacheRefresh15', 'FQDB航班刷新', '00:15', '15M', '2',
             'http://10.23.3.50/msgCenter/autoEngine/runService.action',to_char(SYSDATE,'yyyy-MM-dd hh24:mi:ss'),'1'
            );
            
INSERT INTO MC_SCHEDULE_CONFIG
            (id,name, description,time, interval,type,
             method, last_time,removed
            )
     VALUES (15,'GenerateFlightLoadData', '生成配载数据', '05:00', '24H', '2',
             'http://10.23.3.50/msgCenter/autoEngine/runService.action',to_char(SYSDATE,'yyyy-MM-dd hh24:mi:ss'),'1'
            );
            
INSERT INTO MC_SCHEDULE_CONFIG
            (id,name, description,time, interval,type,
             method, last_time,removed
            )
     VALUES (18,'GenerateFlightMateInfo', '生成航班配对', '00:30', '5M', '1H',
             'http://10.23.3.50/msgCenter/autoEngine/runService.action',to_char(SYSDATE,'yyyy-MM-dd hh24:mi:ss'),'1'
            );

INSERT INTO MC_SCHEDULE_CONFIG
            (id,name, description,time, interval,type,
             method, last_time,removed
            )
     VALUES (19,'CleanAmsData', '数据清理', '20:00', '24H', '2',
             'http://10.23.3.50/msgCenter/autoEngine/runService.action',to_char(SYSDATE,'yyyy-MM-dd hh24:mi:ss'),'1'
            );
                        

                                    
INSERT INTO MC_API_INFO
            (id,user_name, PASSWORD, method_name, data_type, company,
             data_name, client_ip,removed, operate_time,params
            )
     VALUES (sys_GUID(),'omms', 'omms', 'autoEngine', 'GenerateFlightLoadData', 'tsystems',
             '生成配载数据','xxx.xxx.xxx.xxx', 0, SYSDATE,'{"initParams":{"startDate":"today-1 00:00:00","endDate":"today+1 00:00:00"},"restApi":"http://10.23.3.50/fqdb/flightLoadData/generate","autoSwitch":"off","autoWay":"loop","scheduleId":"15"}'
            );

INSERT INTO MC_API_INFO
            (id,user_name, PASSWORD, method_name, data_type, company,
             data_name, client_ip,removed, operate_time,params
            )
     VALUES (sys_GUID(),'omms', 'omms', 'autoEngine', 'GenerateFlightMateInfo', 'tsystems',
             '生成航班配对','xxx.xxx.xxx.xxx', 0, SYSDATE,'{"initParams":{"startDate":"today-1 00:00:00","endDate":"today+1 00:00:00"},"restApi":"http://10.23.3.50/fqdb/flightMateInfo/generate","autoSwitch":"off","autoWay":"loop","scheduleId":"18"}'
            ); 
             
INSERT INTO MC_API_INFO
            (id,user_name, PASSWORD, method_name, data_type, company,
             data_name, client_ip,removed, operate_time,params
            )
     VALUES (sys_GUID(),'omms', 'omms', 'autoEngine', 'FqdbCacheRefresh15', 'tsystems',
             'FQDB航班刷新','xxx.xxx.xxx.xxx', 0, SYSDATE,'{"initParams":{"startDate":"today-10 00:00:00","endDate":"today+3 00:00:00"},"restApi":"http://10.23.3.50/fqdb/flight/refreshCache","autoSwitch":"off","autoWay":"loop","scheduleId":"9"}'
            );   
                     
INSERT INTO MC_API_INFO
            (id,user_name, PASSWORD, method_name, data_type, company,
             data_name, client_ip,removed, operate_time,params
            )
     VALUES (sys_GUID(),'omms', 'omms', 'autoEngine', 'CleanAmsData', 'tsystems',
             '数据清理','xxx.xxx.xxx.xxx', 0, SYSDATE,'{"initParams":{"day":"today-10","method":"dw_clean_removed_data"},"restApi":"http://10.23.3.50/fqdb/procedure/execute","autoSwitch":"off","autoWay":"loop","scheduleId":"19"}'
            );  
   