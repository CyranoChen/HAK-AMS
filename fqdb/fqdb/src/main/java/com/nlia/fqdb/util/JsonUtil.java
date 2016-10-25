package com.nlia.fqdb.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.vo.FlightIdentifier;

import net.sf.json.JSONObject;

public class JsonUtil {
	public static StringBuffer sb = new StringBuffer();

	/**
	 * 将HashMap里的所有参数生成json字符串 参数<String，String>的HashMap 返回：json字符串
	 * ps：暂不支持对象里套对象的结构 eg:{"name":"ninja","power":"999"} 
	 * By　Ninja
	 */
	public static String MakeJsonObject(HashMap<String, String> params) {
		sb.setLength(0);
		sb.append("{");
		Set<Entry<String, String>> params_set = params.entrySet();
		for (Iterator<Entry<String, String>> it = params_set.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\"");
			sb.append(":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");

		return sb.toString();
	}

	/**
	 * 因JsonObject里有bug，遇到entity里有java.util.date的时候会转成java.sql.date，报错
	 * 将一个对象里的所有参数生成json字符串 参数 返回：json字符串 eg:{"name":"ninja","power":"999"}
	 * ps：暂不支持对象里套对象的结构 
	 * By　Ninja
	 */
	public static String MakeJsonObject(Object obj) {
		StringBuffer sb = new StringBuffer();
		if (obj == null)
			return null;
		sb.setLength(0);
		sb.append("{");
		Field[] properties = obj.getClass().getDeclaredFields();
		for (Field property : properties) {

			try {
				// 反射get方法
				if (property.getName() == "serialVersionUID") {
					continue;
				}

				Method meth = obj.getClass().getMethod("get" + property.getName().substring(0, 1).toUpperCase() + property.getName().substring(1));
				// 为二级节点添加属性，属性值为对应属性的值
				Object value = meth.invoke(obj);
				if (value != null) {
					sb.append("\"");
					sb.append(property.getName());
					sb.append("\"");
					sb.append(":");
					if (value instanceof FlightBase) {
						sb.append(MakeJsonObject(new FlightIdentifier((FlightBase)value)));
//						sb.append(value.toString());
						sb.append(",");
					} else {
						sb.append("\"");
						sb.append(value.toString());
						sb.append("\",");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");

		return sb.toString();
	}

	/**
	 * 把单个jsonobject转成jsonarray
	 * @param jsonObject
	 * @return　String型的jsonarray
	 */
	public static String ObjectToArray(String jsonObject) {
		sb.setLength(0);
		sb.append("[");
		sb.append(jsonObject);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 把单个jsonobject转成jsonObject
	 * @param jsonObject
	 * @return　String型的jsonObject
	 * eg{"flightbase":{},"flightData":{},"flightResource":{}}
	 */
	public static String ObjectsToObject(List<String> jsonObjects, List<String> objectNames) {
		if (jsonObjects == null || objectNames == null || objectNames.size() != jsonObjects.size())
			return null;
		sb.setLength(0);
		sb.append("{");

		// for loop
		for (int i = 0; i < jsonObjects.size(); i++) {
			if (jsonObjects.get(i) == null)
				continue;
			sb.append("\"");
			sb.append(objectNames.get(i));
			sb.append("\":");
			sb.append(jsonObjects.get(i));
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 把多个jsonobject转成jsonarray
	 * @param jsonObjects
	 * @return　String型的jsonarray
	 * eg[{"flightbase":""},{"flightData":""},{"flightResource":""}]
	 */

	public static String JsonObjectsToArray(List<String> jsonObjects) {
		sb.setLength(0);
		sb.append("[");

		for (int i = 0; i < jsonObjects.size(); i++) {
			sb.append(jsonObjects.get(i));
			sb.append(",");
		}
		try {
			sb.deleteCharAt(sb.lastIndexOf(","));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("]");
		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 把多个entity转成单个jsonObject
	 * @param jsonObject
	 * @return　String型的jsonarray
	 * eg{{"flightbase":""},{"flightData":""},{"flightResource":""}}
	 */
	public static String ObjectsToJsonObject(List<Object> objects, List<String> objectNames) {
		if (objects == null || objectNames == null || objectNames.size() != objects.size())
			return null;
		List<String> jsonObject = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++) {
			jsonObject.add(MakeJsonObject(objects.get(i)));
		}
		return ObjectsToObject(jsonObject, objectNames);
	}

	/**
	 * return "{"test":["123","456"]}"
	 */
	public static void main(String[] argc) {
		JSONObject obj = JSONObject.fromObject("{\"test\":\"123\",\"test\":\"456\"}");
		System.out.println(obj.toString());
		/*
		 * StringBuffer sbbbbb = new StringBuffer(); sbbbbb.append("1");
		 * sbbbbb.append("2"); sbbbbb.append("3"); sbbbbb.setLength(0);
		 * sbbbbb.append("4"); sbbbbb.append("4"); sbbbbb.append("4");
		 */
	}
}
