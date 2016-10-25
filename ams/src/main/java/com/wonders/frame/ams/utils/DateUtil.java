package com.wonders.frame.ams.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

}
