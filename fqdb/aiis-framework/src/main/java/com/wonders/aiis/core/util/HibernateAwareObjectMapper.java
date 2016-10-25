package com.wonders.aiis.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {
	
	public HibernateAwareObjectMapper() {
		this.registerModule(new Hibernate4Module());
	}

}
