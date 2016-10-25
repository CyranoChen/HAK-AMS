package com.nlia.fqdb.configuration;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.BeanUtils;

import com.nlia.fqdb.entity.FlightBase;

public class DataHandler<T> {

	public List<T> findBy(List<T> list, Map<?, ?> filters) {
		List<T> validList = filter(list, filters);

		return validList;
	}

	public List<T> findBy(List<T> list, Map<?, ?> filters, List<String> sorters) {
		List<T> validList = filter(list, filters);
		validList = sort(validList, sorters);
		return validList;
	}

	public void save(List<T> list, Collection<T> entities) {
		List<T> t_add = new ArrayList<T>();
		for (T t_save : entities) {
			if (list.contains(t_save)) {
				for (T t_all : list) {
					if (t_all.equals(t_save)) {
						BeanUtils.copyProperties(t_save, t_all);
					}
				}
			} else {
				t_add.add(t_save);
			}
		}
		list.addAll(t_add);
	}

	public void save(List<T> list, T entity) {
		if (list.contains(entity)) {
			for (T t_all : list) {
				if (t_all.equals(entity)) {
					BeanUtils.copyProperties(entity, t_all);
				}
			}
		} else {
			list.add(entity);
		}
	}

	public void remove(List<T> list, Collection<T> entities) {
		for (T t_all : list) {
			for (T t_remove : entities) {
				if (t_all.equals(t_remove)) {
					BeanUtils.copyProperties(t_remove, t_all);
				}
			}
		}
	}

	public void remove(List<T> list, T entity) {
		for (T t_all : list) {
			if (t_all.equals(entity)) {
				BeanUtils.copyProperties(entity, t_all);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<T> filter(List<T> list, Map<?, ?> filters) {
		List<T> validList = new ArrayList<>();
		List<String> conditions = new ArrayList<>();
		List<Object> objects = new ArrayList<>();
		Iterator iterator = filters.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
			conditions.add(entry.getKey());
			objects.add(entry.getValue());
		}
		for (T t : list) {
			T matched = null;
			for (int i = 0; i < conditions.size(); i++) {
				String[] keys = conditions.get(i).split("_");
				String[] fields = keys[0].split("\\.");
				Object fieldValue = t;
				String operator = keys[1];
				Object value = objects.get(i);
				try {
					String type = "";
					for (String field : fields) {
						String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
						type = MethodUtils.getAccessibleMethod(fieldValue.getClass(), methodName).getGenericReturnType().toString();
						fieldValue = MethodUtils.invokeExactMethod(fieldValue, methodName);
					}
					if (type.contains("String") && fieldValue == null) {
						fieldValue = "";
					}

					if (operator.equals("equal") && fieldValue.equals(value)) {
						matched = t;
					} else if (operator.equals("like") && ((String) fieldValue).contains((String) value)) {
						matched = t;
					} else if (operator.equals("greaterThanOrEqualTo") && dateColumnComparator(value, fieldValue) <= 0) {
						matched = t;
					} else if (operator.equals("lessThanOrEqualTo") && dateColumnComparator(value, fieldValue) >= 0) {
						matched = t;
					} else if((operator.equals("in") && !"".equals((String) value)) && ((String) value).contains((String) fieldValue)){//add by march
                        matched = t;
                    } else {
						matched = null;
						break;
					}

				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			if (matched != null) {
				validList.add(matched);
			}
		}
		return validList;
	}

	@SuppressWarnings({ "unchecked" })
	public List<T> sort(List<T> list, List<String> sorters) {
		ComparatorChain cc = new ComparatorChain();
		for (final String sorter : sorters) {
			Comparator<T> c = new Comparator<T>() {

				@Override
				public int compare(T o1, T o2) {
					String[] fields = sorter.split("_")[0].split("\\.");

					String direction = sorter.split("_")[1];
					try {
						String type = "";
						Object v1 = o1;
						Object v2 = o2;
						for (String field : fields) {
							String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
							type = MethodUtils.getAccessibleMethod(v1.getClass(), methodName).getGenericReturnType().toString();
							v1 = MethodUtils.invokeExactMethod(v1, methodName);
							v2 = MethodUtils.invokeExactMethod(v2, methodName);
						}

						if (direction.equals("asc")) {
							if (type.contains("String")) {
								return stringColumnComparator(v1, v2);
							} else if (type.contains("Integer")) {
								return integerColumnComparator(v1, v2);
							} else if (type.contains("Long")) {
								return longColumnComparator(v1, v2);
							} else if (type.contains("Float")) {
								return floatColumnComparator(v1, v2);
							} else if (type.contains("Double")) {
								return doubleColumnComparator(v1, v2);
							} else if (type.contains("Date")) {
								return dateColumnComparator(v1, v2);
							}
						} else if (direction.contains("desc")) {
							if (type.contains("String")) {
								return stringColumnComparator(v2, v1);
							} else if (type.contains("Integer")) {
								return integerColumnComparator(v2, v1);
							} else if (type.contains("Long")) {
								return longColumnComparator(v2, v1);
							} else if (type.contains("Float")) {
								return floatColumnComparator(v2, v1);
							} else if (type.contains("Double")) {
								return doubleColumnComparator(v2, v1);
							} else if (type.contains("Date")) {
								return dateColumnComparator(v2, v1);
							}
						}
					} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					return 0;
				}
			};

			cc.addComparator(c);
		}
		if (cc.size() > 0) {
			Collections.sort(list, cc);
		}
		return list;
	}

	private int stringComparator(Object s1, Object s2) {
		if (s1 != null && s2 != null)
			return ((String) s1).compareTo((String) s2);
		else if (s1 != null && s2 == null)
			return ((String) s1).compareTo("");
		else if (s1 == null && s2 != null)
			return "".compareTo((String) s2);
		else
			return 0;
	}

	private int integerComparator(Object s1, Object s2) {
		if (s1 != null && s2 != null)
			return ((Integer) s1).compareTo((Integer) s2);
		else if (s1 != null && s2 == null)
			return ((Integer) s1).compareTo(Integer.MIN_VALUE);
		else if (s1 == null && s2 != null)
			return ((Integer) Integer.MIN_VALUE).compareTo((Integer) s2);
		else
			return 0;
	}

	private int longComparator(Object s1, Object s2) {
		if (s1 != null && s2 != null)
			return ((Long) s1).compareTo((Long) s2);
		else if (s1 != null && s2 == null)
			return ((Long) s1).compareTo(Long.MIN_VALUE);
		else if (s1 == null && s2 != null)
			return ((Long) Long.MIN_VALUE).compareTo((Long) s2);
		else
			return 0;
	}

	private int floatComparator(Object s1, Object s2) {
		if (s1 != null && s2 != null)
			return ((Float) s1).compareTo((Float) s2);
		else if (s1 != null && s2 == null)
			return ((Float) s1).compareTo(Float.MIN_VALUE);
		else if (s1 == null && s2 != null)
			return ((Float) Float.MIN_VALUE).compareTo((Float) s2);
		else
			return 0;
	}

	private int doubleComparator(Object s1, Object s2) {
		if (s1 != null && s2 != null)
			return ((Double) s1).compareTo((Double) s2);
		else if (s1 != null && s2 == null)
			return ((Double) s1).compareTo(Double.MIN_VALUE);
		else if (s1 == null && s2 != null)
			return ((Double) Double.MIN_VALUE).compareTo((Double) s2);
		else
			return 0;
	}

	private int dateComparator(Object s1, Object s2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		try {
			if (s1 != null && s2 != null)
				return ((Date) s1).compareTo((Date) s2);
			else if (s1 != null && s2 == null)
				return ((Date) s1).compareTo(sdf.parse("1000-01-01"));
			else if (s1 == null && s2 != null)
				return (sdf.parse("1000-01-01")).compareTo((Date) s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private int dateColumnComparator(Object o1, Object o2) throws IllegalAccessException, InvocationTargetException {
		if (o1 != null && o2 != null)
			return dateComparator(o1, o2);
		else if (o1 == null && o2 != null)
			return dateComparator(null, o2);
		else if (o1 != null && o2 == null)
			return dateComparator(o1, null);
		else
			return 0;
	}

	private int doubleColumnComparator(Object o1, Object o2) throws IllegalAccessException, InvocationTargetException {
		if (o1 != null && o2 != null)
			return doubleComparator(o1, o2);
		else if (o1 == null && o2 != null)
			return doubleComparator(null, o2);
		else if (o1 != null && o2 == null)
			return doubleComparator(o1, null);
		else
			return 0;
	}

	private int floatColumnComparator(Object o1, Object o2) throws IllegalAccessException, InvocationTargetException {
		if (o1 != null && o2 != null)
			return floatComparator(o1, o2);
		else if (o1 == null && o2 != null)
			return floatComparator(null, o2);
		else if (o1 != null && o2 == null)
			return floatComparator(o1, null);
		else
			return 0;
	}

	private int longColumnComparator(Object o1, Object o2) throws IllegalAccessException, InvocationTargetException {
		if (o1 != null && o2 != null)
			return longComparator(o1, o2);
		else if (o1 == null && o2 != null)
			return longComparator(null, o2);
		else if (o1 != null && o2 == null)
			return longComparator(o1, null);
		else
			return 0;
	}

	private int integerColumnComparator(Object o1, Object o2) throws IllegalAccessException, InvocationTargetException {
		if (o1 != null && o2 != null)
			return integerComparator(o1, o2);
		else if (o1 == null && o2 != null)
			return integerComparator(null, o2);
		else if (o1 != null && o2 == null)
			return integerComparator(o1, null);
		else
			return 0;
	}

	private int stringColumnComparator(Object o1, Object o2) throws IllegalAccessException, InvocationTargetException {
		if (o1 != null && o2 != null)
			return stringComparator(o1, o2);
		else if (o1 == null && o2 != null)
			return stringComparator(null, o2);
		else if (o1 != null && o2 == null)
			return stringComparator(o1, null);
		else
			return 0;
	}
	/*
	public void saveFlightServiceInfo(List<FlightBase> cache, FlightServiceInfo entity) {
		if (cache.contains(entity.getFlightBase())) {
			for (FlightBase flightBase : cache) {
				if (flightBase.equals(entity.getFlightBase())) {
					// 如遍历命中，则修改
					flightBase.setFlightServiceInfo(entity);
					// BeanUtils.copyProperties(entity, flightBase);
					return;
				}
			}
		} else {
			// 出错
			return;
		}
	}
	*/
}
