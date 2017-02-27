package com.tsystems.aiis.core.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.aiis.core.service.AbstractCrudService;

public abstract class AbstractCrudController<T, PK> {
	
	@Resource
	private ObjectMapper objectMapper;

	protected abstract AbstractCrudService<T, PK> getCrudService();

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Map<?, ?> list(String filter, String sort, int start, int limit) {

		Map<?, ?> filters = createFilters(filter);

		long total = getCrudService().countBy(filters);

		List<T> rows = getCrudService().findBy(filters, null, start, limit);

		return createModelMap(rows, total);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	T find(@PathVariable PK id) {
		return getCrudService().find(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	String add(@RequestBody T entity) {
		getCrudService().save(entity);
		return "{success: true}";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	String update(@RequestBody T entity) {
		getCrudService().save(entity);
		return "{success: true}";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@SuppressWarnings("unchecked")
	public @ResponseBody
	void remove(@PathVariable String id) {
		getCrudService().delete((PK) id);
	}

	@SuppressWarnings("unchecked")
	protected Map<?, ?> createFilters(String filter) {
		
		Map<String, Object> filters = null;
		if (StringUtils.isNotEmpty(filter)) {
			
			filters = new LinkedHashMap<String, Object>();
			try {
				List<Map<String, Object>> filterList = objectMapper.readValue(filter, List.class);
				for (Map<String, Object> f : filterList) {
					String property = (String) f.get("property");
					Object value = f.get("value");
					filters.put(property, value);
				}
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		return filters;
	}
	
	protected Map<?, ?> createModelMap(Collection<?> rows, long total) {

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("rows", rows);
		modelMap.put("total", total);

		return modelMap;
	}

}
