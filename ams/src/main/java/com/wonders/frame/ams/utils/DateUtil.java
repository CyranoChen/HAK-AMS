package com.wonders.frame.ams.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wsq on 2015/12/13.
 */
public class DateUtil {

    public static SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyy/MM/dd");

    public static int getIntervalDay(String d1,String d2,String format){
        Long d = 0L;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            d =  ( sdf.parse(d1).getTime() - sdf.parse(d2).getTime() ) / 86400000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d.intValue();
    }
    
    private static SimpleDateFormat getFormat(String format){
		return new SimpleDateFormat(format); 
	}
    
    public static String getDateString(Date date){
		if(date == null){
			return  null;
		}
		return getFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
    
    public static String getDateString(){
		return getDateString(new Date());
	}
    
	/**
	 * @author fanyu
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return  两个时间相差天数
	 * @throws ParseException 
	 */
	public static int getDaysBetween(String strStart,String strEnd) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse(strStart);
		Date end = sdf.parse(strEnd);
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(start);
		long startTime = calendar.getTimeInMillis();
		calendar.setTime(end);
		long endTime = calendar.getTimeInMillis();
		long days = (endTime-startTime)/(1000*3600*24);
		return Integer.valueOf(String.valueOf(days));
	}
	
	
	public static String addDay(String date,int d,String format){
		return add(date, Calendar.DAY_OF_MONTH, d, format);
	}
	
	
	public static String add(String date,int type,int value,String format){
		Date d = parse(date, format);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(type, value);
		return getDateString(c.getTime(), format);	
	}
	
	public static Date parse(String date,String format){
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getDateString(Date date,String format){
		if(date == null || !spaceCheck(format)){
			return "";
		}
		String d = "";
		try{
			d = getFormat(format).format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return d;
	}
	
	public static boolean spaceCheck(String str){
		return str != null && str.trim().length() > 0 ;
	}
	
	
	public static String parseDate(String d) {
		
		String formattedDate = "";
	    try {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date date = simpleDateFormat.parse(d);
	        
	        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        formattedDate = simpleDateFormat.format(date);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return formattedDate;
	}
	

}
