package com.tsystems.aiis.core.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

public abstract class GenericDao<T, PK> {

	private final Class<T> entityClass;

	protected EntityManager entityManager;
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	protected GenericDao() {

		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		Type[] args = type.getActualTypeArguments();

		this.entityClass = (Class<T>) args[0];
	}

	public T find(PK id) {
		return entityManager.find(entityClass, id);
	}

	public T save(T entity) {
		return entityManager.merge(entity);
	}

	public void remove(PK id) {
		entityManager.remove(find(id));
	}

	public long countBy(Map<?, ?> filters) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<T> root = cq.from(entityClass);
		cq.select(cb.count(root));

		if (filters != null) {
			buildWhereClause(filters, cb, cq, root);
		}

		return entityManager.createQuery(cq).getSingleResult();
	}

	public List<T> findBy(Map<?, ?> filters, List<String> sorters, int start,
			int limit) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);

		Root<T> root = cq.from(entityClass);
		cq.select(root);

		if (filters != null) {
			buildWhereClause(filters, cb, cq, root);
		}
		
		if (sorters != null) {
			buildOrderByClause(sorters, cb, cq, root);
		}
		
		TypedQuery<T> query = entityManager.createQuery(cq);
		if (start >= 0) {
			query.setFirstResult(start);
		}
		if (limit > 0) {
			query.setMaxResults(limit);
		}
		
		return query.getResultList();
	}

	protected void buildWhereClause(Map<?, ?> filters, CriteriaBuilder cb,
			CriteriaQuery<?> cq, Root<T> root) {

		Predicate p = cb.conjunction();
		for (Entry<?, ?> entry : filters.entrySet()) {

			String key = (String) entry.getKey();
			String prop = StringUtils.substringBefore(key, "_");
			String oper = StringUtils.substringAfter(key, "_");
			Object value = entry.getValue();

			if (StringUtils.equals(oper, "fetch")) {
				if (cq.getSelection() == root) {
					JoinType jt = JoinType.INNER;
					String joinType = StringUtils.upperCase((String) value);
					if (StringUtils.isNotEmpty(joinType)) {
						jt = EnumUtils.getEnum(JoinType.class, joinType);
					}
					root.fetch(prop, jt);
				}
				continue;
			}

			try {
				Path<Object> path = buildPath(root, prop);
				if (StringUtils.equals(oper, "like")) {
					p = cb.and(p, (Predicate) MethodUtils.invokeMethod(cb,
							oper, path, "%" + value + "%"));
				} else if (StringUtils.startsWith(oper, "is")) {
					
					p = cb.and(p, (Predicate) MethodUtils.invokeMethod(cb,
							oper, path));
				} else if (StringUtils.startsWith(oper, "lessThan")
						|| StringUtils.startsWith(oper, "greaterThan")) {
					
					if (value instanceof String) {
						value = buildPath(root, (String) value);
					}
					
					p = cb.and(p, (Predicate) MethodUtils.invokeMethod(cb,
							oper, path, value));
				} else {
					p = cb.and(p, (Predicate) MethodUtils.invokeMethod(cb,
							oper, path, value));
				}
			} catch (Exception e) {	
				
				e.printStackTrace();
			}

			cq.where(p);
		}
	}
	
	protected void buildOrderByClause(List<String> sorters, CriteriaBuilder cb,
			CriteriaQuery<T> cq, Root<T> root) {

		List<Order> orders = new ArrayList<Order>();
		for (String sorter : sorters) {
			String prop = StringUtils.substringBefore(sorter, "_");
			String dir = StringUtils.substringAfter(sorter, "_");
			
			String[] fields = StringUtils.split(prop, ".");
			Path<?> path = root.get(fields[0]);
			for(int i = 1; i < fields.length; i++){
				path = path.get(fields[i]);
			}
			
			if (StringUtils.equalsIgnoreCase(dir, "asc")) {
				orders.add(cb.asc(path));
			} else {
				orders.add(cb.desc(path));
			}
		}
		cq.orderBy(orders);
	}
	
	@SuppressWarnings("unchecked")
	protected Path<Object> buildPath(Root<T> root, String property) {

		if (StringUtils.contains(property, ".")) {
			String[] entityName = StringUtils.split(property, ".");
			int length = entityName.length;
			Join<Object, Object> entityJoin = null;
			String joinName = entityName[0];
			String propName = entityName[1];
			if(length == 2){
				Set<Fetch<T, ?>> fetches = root.getFetches();
				for (Fetch<T, ?> fetch : fetches) {
					String name = fetch.getAttribute().getName();
					if (StringUtils.equals(name, joinName)) {
						return ((Join<?, ?>) fetch).get(propName);
					}
				}

				Set<Join<T, ?>> joins = root.getJoins();
				for (Join<T, ?> join : joins) {
					String name = join.getAttribute().getName();
					if (StringUtils.equals(name, joinName)) {
						return join.get(propName);
					}
				}
				return root.join(joinName).get(propName);
			}else{
				boolean joinFlag = false;
				entityJoin = root.join(joinName);
				for(int i = 1; i < length - 1; i++){
					joinName = entityName[i];
					propName = entityName[i + 1];
					Set<Fetch<Object,?>> fetches = entityJoin.getFetches();
					for (Fetch<Object, ?> fetch : fetches) {
						String name = fetch.getAttribute().getName();
						if (StringUtils.equals(name, joinName)) {
							entityJoin = (Join<Object, Object>) fetch;
							joinFlag = true;
							if(i == length - 2){
								return ((Join<?, ?>) fetch).get(propName);
							}
						}
					}

					Set<Join<Object, ?>> joins = entityJoin.getJoins();
					for (Join<Object, ?> join : joins) {
						String name = join.getAttribute().getName();
						if (StringUtils.equals(name, joinName)) {
							entityJoin = (Join<Object, Object>) join;
							joinFlag = true;
							if(i == length - 2){
								return join.get(propName);
							}
						}
					}
					if(!joinFlag){
						entityJoin = entityJoin.join(joinName);
					}
					joinFlag = false;
					
				}
				return entityJoin.get(propName);
			}
		}
			
		return root.get(property);
	}

//	protected Path<Object> buildPath(Root<T> root, String property) {
//
//		if (StringUtils.contains(property, ".")) {
//
//			String joinName = StringUtils.substringBefore(property, ".");
//			String propName = StringUtils.substringAfter(property, ".");
//
//			Set<Fetch<T, ?>> fetches = root.getFetches();
//			for (Fetch<T, ?> fetch : fetches) {
//				String name = fetch.getAttribute().getName();
//				if (StringUtils.equals(name, joinName)) {
//					return ((Join<?, ?>) fetch).get(propName);
//				}
//			}
//
//			Set<Join<T, ?>> joins = root.getJoins();
//			for (Join<T, ?> join : joins) {
//				String name = join.getAttribute().getName();
//				if (StringUtils.equals(name, joinName)) {
//					return join.get(propName);
//				}
//			}
//
//			return root.join(joinName).get(propName);
//		}
//
//		return root.get(property);
//	}
	
}
