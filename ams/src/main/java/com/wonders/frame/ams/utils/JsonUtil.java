package com.wonders.frame.ams.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;





/**
 * @see JSON相关工具类
 * @author wangsq
 *
 */
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper(); 
	static{
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	/**
	 * @see JSON格式字符串 转 JAVA对象(此方法不能转换集合类型)
	 * @param jsonStr
	 * @param TypeReference
	 * @return
	 */
	public static <T> T toObject(String jsonStr,TypeReference tr){
		if( ! Chk.spaceCheck(jsonStr)){
			return null;
		}
		T json = null;
		try {
			json = mapper.readValue(jsonStr, tr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
