package com.nlia.fqdb.service;

import java.util.List;

public interface IRestfulDataManager<T> {
	public List<T> getData(String sys, String[] paths, Class<?> clazz);
}
