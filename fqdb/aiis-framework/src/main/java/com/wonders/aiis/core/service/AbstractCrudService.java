package com.wonders.aiis.core.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.aiis.core.dao.GenericDao;

@Transactional(readOnly = true)
public abstract class AbstractCrudService<T, PK> {

	protected abstract GenericDao<T, PK> getDao();

	public T find(PK id) {
		return getDao().find(id);
	}

	@Transactional
	public T save(T entity) {
		return getDao().save(entity);
	}
	
	@Transactional
	public Collection<T> save(Collection<T> entities) {
		List<T> result = new ArrayList<>(entities.size());
		if (entities != null && !entities.isEmpty()) {
			for (T entity : entities) {
				T e = save(entity);
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * 物理删除
	 * @param id
	 */
	@Transactional
	public void delete(PK id) {
		getDao().remove(id);
	}
	
	/**
	 * 逻辑删除
	 * @param entity
	 * @return
	 */
	@Transactional
	public T remove(T entity) {
		try {
			MethodUtils.invokeMethod(entity, "setRemoveFlag", "0");
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return getDao().save(entity);
	}
	
	@Transactional
	public Collection<T> remove(Collection<T> entities) {
		List<T> result = new ArrayList<>(entities.size());
		if (entities != null && !entities.isEmpty()) {
			for (T entity : entities) {
				try {
					MethodUtils.invokeMethod(entity, "setRemoveFlag", "0");
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				T e = save(entity);
				result.add(e);
			}
		}
		return result;
	}

	public long countBy(Map<?, ?> filters) {
		return getDao().countBy(filters);
	}
	
	public List<T> findBy(Map<?, ?> filters) {
		return getDao().findBy(filters, null, -1, -1);
	}
	
	public List<T> findBy(Map<?, ?> filters, List<String> sorters) {
		return getDao().findBy(filters, sorters, -1, -1);
	}

	public List<T> findBy(Map<?, ?> filters, List<String> sorters, int start,
			int limit) {

		return getDao().findBy(filters, sorters, start, limit);
	}

}
