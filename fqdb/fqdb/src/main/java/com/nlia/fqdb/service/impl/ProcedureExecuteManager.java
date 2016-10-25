package com.nlia.fqdb.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.service.IProcedureExecuteManager;

@Service
public class ProcedureExecuteManager implements IProcedureExecuteManager {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public String execute(String procedureName, String params) {

	        System.out.println("start procedure:"+procedureName+" with params:" + params);
	        
	        final String inPutParams = params;
	        final String executeProcedureName = procedureName;
	        
	        @SuppressWarnings("rawtypes")
			String result = (String) jdbcTemplate.execute(
					new CallableStatementCreator() {
	            public CallableStatement createCallableStatement(Connection con) throws SQLException {
	                String storedProc = "{call "+executeProcedureName+"(?,?)}";// 调用的sql
	                CallableStatement cs = con.prepareCall(storedProc);
	                cs.setString(1, inPutParams);// 设置输入参数的值
	                cs.registerOutParameter(2, OracleTypes.VARCHAR);
	                return cs;
	            }
	        }, new CallableStatementCallback() {
	            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
	                cs.execute();
	                return cs.getString(2);// 获取输出参数的值
	            }
	        });

	        return result;


	}

}
