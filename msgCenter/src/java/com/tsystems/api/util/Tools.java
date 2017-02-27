package com.tsystems.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: Tools 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author lushuaifeng
 * @date 2013-4-11 下午05:29:46 
 *  
 */
public class Tools {
	private static final Logger log = LoggerFactory.getLogger(Tools.class);
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String TIME_FORMAT2 = "HH:mm:ss";	

	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";	
	
	public static final String TIME_FORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss";		
	
	public static String getMD5(String value) {  
        String result = null;  
        try{  
            byte[] valueByte = value.getBytes();  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(valueByte);  
            result = toHex(md.digest());  
        }catch(NoSuchAlgorithmException e1){  
            e1.printStackTrace();  
        }  
        return result;  
    }  
    // 将传递进来的字节数组转换成十六进制的字符串形式并返回  
    private static String toHex(byte[] buffer){  
        StringBuffer sb = new StringBuffer(buffer.length * 2);  
        for (int i = 0; i < buffer.length; i++){  
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));  
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));  
        }  
        return sb.toString();  
    }  
    
	public static String json2Xml(JSONObject json,String rootName,HashMap<String,String> hm){
		String sXml="";
		XMLSerializer xmlSerializer = new XMLSerializer();				
		xmlSerializer.setTypeHintsEnabled( false );       
		xmlSerializer.setRootName(rootName); 
		String sContent=xmlSerializer.write(json);
		try {
			
			Document doc = DocumentHelper.parseText(sContent);	
			doc.setXMLEncoding("UTF-8");
			Element root=doc.getRootElement();
	        Set<String> key = hm.keySet();
	        for (Iterator<String> it = key.iterator(); it.hasNext();) {
	            String s = it.next();
	            root.addAttribute(s, hm.get(s));
	        }
	
	        sXml=doc.asXML();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			log.error("unexcepted Exception when {}","json2Xml",e);
			//e.printStackTrace();
		}
		return sXml;
	}
	
	public static String xml2Json(String xmlString){
		  XMLSerializer xmlSerializer = new XMLSerializer();
		  JSON json = xmlSerializer.read(xmlString);		 
		  return json.toString(1);
	}  
	
	public static String getCurrentDate() {
		return timeFormat(DATE_FORMAT,new Date());
	}	
	
	public static String getCurrentDateTime() {
		return timeFormat(TIME_FORMAT,new Date());
	}	
	
	public static String formatDateTime(String s) {
		 Date date=timeParse(TIME_FORMAT_TZ,s);
		 if(date==null){
			 date=timeParse(TIME_FORMAT,s);
		 }
		 if(date==null){
			 return s;
		 }else{
			 return timeFormat(TIME_FORMAT,date);
		 }
	}	
	
	public static String timeFormat(String format,Date day) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String date = sdf.format(day);
			return date;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Date timeParse(String format,String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(time);
			return date;
		} catch (Exception e) {
		}
		return null;
	}
		
	public static String getDay(int num) {	
		Date date=new Date();
		if(num!=0){
			Calendar calendar = Calendar.getInstance();		
			calendar.setTime(date);		
			calendar.add(Calendar.DAY_OF_MONTH, num);		
			date = calendar.getTime();		
		}
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		String day=formatter.format(date);

		return day;
	}
	
	public static String getTime(String interval) {	
		Date date=new Date();
		String timeInterval=interval.toUpperCase();
		int intervalType;
		if(timeInterval.endsWith("H")){
			interval=timeInterval.replace("H", "");
			intervalType=Calendar.HOUR;
		}else if(timeInterval.endsWith("M")){
			interval=timeInterval.replace("M", "");
			intervalType=Calendar.MINUTE;
		}else{
			interval=timeInterval.replace("S", "");	
			intervalType=Calendar.SECOND;
		}
		if(!interval.equals("")){
			Calendar calendar = Calendar.getInstance();		
			calendar.setTime(date);					
			calendar.add(intervalType, Integer.parseInt(interval));					
			date = calendar.getTime();		
		}
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT2);
		String time=formatter.format(date);

		return time;
	}	
	
    public static void main(String[] args){
    	String s=getTime("-15M");
    	System.out.println(s);
	}
}
