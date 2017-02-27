/**
 * 
 */
package com.tsystems.schedule.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tsystems.schedule.model.bo.TScheduleConfig;
import com.tsystems.task.sample.ITaskService;

/**
 * @ClassName: ExecUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-5 下午02:49:26
 * 
 */

public class ExecUtil {
	/** 
	* @Title: nativeExec 
	* @Description: TODO(任务调用 本地) 
	* @param @param t
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	private static final Logger log = LoggerFactory.getLogger(ExecUtil.class);
	public static String nativeExec(TScheduleConfig t) {
		String method = t.getMethod();
		String param = t.getParam();
		String result = "";
		if (method != null && method.indexOf(".") > -1) {
			String className = (method.split("\\."))[0];
			String methodName = (method.split("\\."))[1];
			try {
				Thread.sleep(2000);
				ITaskService task = (ITaskService) SpringBeanUtil
						.getBean(className);
				Class<?> cls = task.getClass();
				Method[] methods = cls.getDeclaredMethods();
				String methodsName = "";
				for (Method m : methods) {
					methodsName += m.getName() + ",";
				}
				if (methodsName.indexOf(methodName) >= 0) {
					// 返回方法名为“testMethod”的一个 Method 对象，后面跟的是该方法参数
					Method callMethod = cls.getMethod(methodName,
							new Class[] { String.class });
					result = (String) callMethod.invoke(task,
							new Object[] { param });

				}
			} catch (Exception e) {
				log.error("unexcepted Exception when {}","nativeExec",e);
				//e.printStackTrace();
				result = "0";
			}
		}

		return result;
	}

	/** 
	* @Title: webExec 
	* @Description: TODO(任务调用 网络) 
	* @param @param t
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String webExec(TScheduleConfig t) {
		String method = t.getMethod();
		String param = t.getParam();
		String result = "";
		try {
			URL url = null;
			HttpURLConnection http = null;

			try {
				Thread.sleep(2000);
				url = new URL(method);
				http = (HttpURLConnection) url.openConnection();
				http.setDoInput(true);
				http.setDoOutput(true);
				http.setUseCaches(false);
				http.setConnectTimeout(50000);
				http.setReadTimeout(50000);
				http.setRequestMethod("POST");
				// http.setRequestProperty("Content-Type",
				// "text/xml; charset=UTF-8");
				http.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				http.connect();
				param = "&param=" + param;

				OutputStreamWriter osw = new OutputStreamWriter(http
						.getOutputStream(), "utf-8");
				osw.write(param);
				osw.flush();
				osw.close();

				if (http.getResponseCode() == 200) {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(http.getInputStream(),
									"utf-8"));
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						result += inputLine;
					}
					in.close();
				}
			} catch (Exception e) {
				log.error("unexcepted Exception when {}","webExec",e);
				//e.printStackTrace();
				result = "0";
			} finally {
				if (http != null)
					http.disconnect();
			}
		} catch (Exception e) {
			log.error("unexcepted Exception when {}","webExec",e);
			//e.printStackTrace();
		}
		return result;
	}

	// 返回值为String
	/** 
	* @Title: procedureExec 
	* @Description: TODO(任务调用 数据库) 
	* @param @param t
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	public static String procedureExec(TScheduleConfig t) {
		final String datasource = t.getDatasource();
		final String method = t.getMethod();
		final String param = t.getParam();
		JdbcTemplate jdbcTemplate = DbUtil.getJdbcTemplate(datasource);
		String result = "";
		try {
			Thread.sleep(2000);
			result = (String) jdbcTemplate.execute(
					new CallableStatementCreator() {
						public CallableStatement createCallableStatement(
								Connection con) throws SQLException {
							String storedProc = "{call " + method + "(?,?)}";// 调用的sql
							CallableStatement cs = con.prepareCall(storedProc);
							cs.setString(1, param);// 设置输入参数的值
							cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);// 注册输出参数的类型
							return cs;
						}
					}, new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {
							cs.execute();
							return cs.getString(2);// 获取输出参数的值
						}
					});
		} catch (Exception e) {
			log.error("unexcepted Exception when {}","procedureExec",e);
			//e.printStackTrace();
			result = "0";
		}
		return result;
	}

	// 返回值为游标 遍历后的List<Map>
	/** 
	* @Title: procedureExec2 
	* @Description: TODO(任务调用数据库) 
	* @param @param t
	* @param @return    游标类型 转换为 list<Map>结构 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> procedureExec2(TScheduleConfig t) {
		String datasource = "";
		final String method = t.getMethod();
		final String param = t.getParam();
		JdbcTemplate jdbcTemplate = DbUtil.getJdbcTemplate(datasource);
		List resultList = null;
		try {
			resultList = (List) jdbcTemplate.execute(
					new CallableStatementCreator() {
						public CallableStatement createCallableStatement(
								Connection con) throws SQLException {
							String storedProc = "{call " + method + "(?,?)}";// 调用的sql
							CallableStatement cs = con.prepareCall(storedProc);
							cs.setString(1, param);// 设置输入参数的值
							cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);// 注册输出参数的类型
							return cs;
						}
					}, new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {
							List resultsMap = new ArrayList();
							cs.execute();
							ResultSet rs = (ResultSet) cs.getObject(2);// 获取游标一行的值
							while (rs.next()) {// 转换每行的返回值到Map中
								Map rowMap = new HashMap();
								rowMap.put("param1", rs.getString("param1"));
								rowMap.put("param2", rs.getString("param2"));
								resultsMap.add(rowMap);
							}
							rs.close();
							return resultsMap;
						}
					});
		} catch (Exception e) {
			log.error("unexcepted Exception when {}","procedureExec2",e);
			
			//e.printStackTrace();
		}
		/*
		 * for (int i = 0; i < resultList.size(); i++) { Map rowMap = (Map)
		 * resultList.get(i); String id = rowMap.get("id").toString(); String
		 * name = rowMap.get("name").toString(); System.out.println("id=" + id +
		 * ";name=" + name); }
		 */
		return resultList;
	}
}
