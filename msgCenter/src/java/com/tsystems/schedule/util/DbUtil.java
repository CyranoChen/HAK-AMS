package com.tsystems.schedule.util;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("dbUtil")
public class DbUtil implements ApplicationContextAware {
	private static ApplicationContext ctx = null;
	private static Map<String, JdbcTemplate> jdbcTemplateMap = new HashMap<String, JdbcTemplate>(
			0);

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		DbUtil.ctx = ctx;
	}

	public static Object getBean(String prop) {
		Object obj = ctx.getBean(prop);
		return obj;
	}

	public static JdbcTemplate getJdbcTemplate(String source) {
		if (source == null || "".equals(source)) {
			DataSource dataSource = (DataSource) getBean("stptdemo");
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplateMap.put(source, jdbcTemplate);
			return jdbcTemplate;
		}
		if (jdbcTemplateMap.containsKey(source))
			return jdbcTemplateMap.get(source);
		DataSource dataSource = (DataSource) getBean(source);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplateMap.put(source, jdbcTemplate);
		return jdbcTemplate;
	}
}
