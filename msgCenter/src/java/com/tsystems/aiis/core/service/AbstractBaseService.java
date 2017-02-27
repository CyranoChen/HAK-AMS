package com.tsystems.aiis.core.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AbstractBaseService<T, PK> {

	T find(PK id);

	T save(T entity);
	
	Collection<T> save(Collection<T> entities);

	void delete(PK id);
	
	T remove(T entity);
	
	Collection<T> remove(Collection<T> entities);

	long countBy(Map<?, ?> filters);
	
	List<T> findBy(Map<?, ?> filters);
	
	List<T> findBy(Map<?, ?> filters, List<String> sorters);

	List<T> findBy(Map<?, ?> filters, List<String> sorters, int start, int limit);

}
