package com.nlia.fqdb.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtils {
	
	private static long startTime;
	private static long endTime;

	
	public static Date getDefineDay(Date date, int num){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date defineDay = new Date(date.getTime() + 1000 * 60 * 60 * 24 * num);
		String strDate = df.format(defineDay);
		try {
			defineDay = df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return defineDay;
	}
	/**
	 * 算法计时开始
	 */
	public static void algorithmStart() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * 返回算法消耗的时间（eg1.2秒）
	 * 
	 * @return
	 */
	public static String algorithmFinish() {
		endTime = System.currentTimeMillis();
		return String.valueOf(((endTime - startTime) / 1000.0));
	}
	public static String Date2String(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
			return sdf.format(date);
		}
		return "";
	}

	public static Date String2Date(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		Date currentdate = null;
		try {
			currentdate = sdf.parse(date);
		} catch (ParseException e) {
		}
		return currentdate;
	}

	public static Date Date2Date(Date date) {
		return String2Date(Date2String(date));
	}

	public static String Date2String(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
			return sdf.format(date);
		}
		return "";
	}

	public static Date String2Date(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		Date currentdate = null;
		if (date == null || date.trim().equals("")) {
			return null;
		}
		try {
			currentdate = sdf.parse(date);
		} catch (ParseException e) {
		}
		return currentdate;
	}

	public static Date Date2Date(Date date, String pattern) {
		return String2Date(Date2String(date, pattern), pattern);
	}

	/**
	 * 判断是否符合日期规范
	 * 逻辑可能有问题 byNinja
	 * @param s
	 * @param pattern
	 * @return
	 */
    @Deprecated
	public static boolean isValidDate(String s, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (s == null || s.trim().equals("")) {
			return true;
		}
		try {
			return sdf.format(sdf.parse(s)).equals(s);
		} catch (Exception e) {
			return false;
		}
	}

	public static Long StringToLong(String s) {
		if ("".equals(s) || null == s) {
			return null;
		} else {
			return Long.parseLong(s);
		}
	}

	public static Integer StringToInt(String s) {
		if ("".equals(s) || null == s) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 * @author mengjie
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 获得去年当日日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearBefore(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) - 1);
		return now.getTime();
	}


	/**
	 * 获取两个日期之间相差天数
	 */
	public static int getDifferDay(Date start, Date end) {
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(start);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(end);
		int day = (int) (((long) calendarEnd.getTimeInMillis() - (long) calendarStart.getTimeInMillis()) / (1000 * 60 * 60 * 24));
		return day;
	}
	
	/**
	 * 获取两个日期之间相差小时数
	 */
	public static double getDifferHour(Date start, Date end) {
		if (start == null || end == null) {
			return 0;
		}
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(start);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(end);
		double hour = ((double) calendarEnd.getTimeInMillis() - (double) calendarStart.getTimeInMillis()) / (1000 * 60 * 60);
		return hour;
	}
	
	/**
	 * 获取两个日期之间相差分钟数
	 */
	public static double getDifferMinute(Date start, Date end) {
		if (start == null || end == null) {
			return 0;
		}
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(start);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(end);
		double Minute = ((double) calendarEnd.getTimeInMillis() - (double) calendarStart.getTimeInMillis()) / (1000 * 60);
		return Minute;
	}
	
	/**
	 * 判断日期是否在2个日期之间
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return 
	 * 如果等于开始时间或者结束时间，也返回true
	 */
	public static boolean isDateDuringPeriod(Date date, Date startDate, Date endDate){
		if(date.equals(startDate) || date.equals(endDate))
			return true;
		if(date.before(endDate) && date.after(startDate))
			return true;
		return false;
	}
	/**
	 * 获取i小时之后的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateAfterHour(Date date, int hour) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
		return now.getTime();
	}
	
	/**
	 * 获取运营时间（凌晨5点之前，计算为前一天，只取年月日）
	 * 
	 * @param date
	 * @return
	 */
	
	public static Date getRunningDate(Date date){
		if(date==null){
			return null;
		}
		Date dayOf5HourAge=getDateBeforeHour(date,5);
		Date d1=getZeroOfOneday(dayOf5HourAge);
		Date d2=getZeroOfOneday(date);
		if(getDifferDay(d1,d2)!=0){
			return d1;
		}else{
			return d2;
		}
		
	}
	
	public static void main(String[] args){
		
		Date d=String2Date("2016-10-01 04:59:45");
		Date d2=getRunningDate(d);
		System.out.println(d2);
	}

	/**
	 * 获得今天的00:00分
	 */
	public static Date getZeroOfToday() {
		return getZeroOfOneday(new Date());
	}
	/**
	 * 获得今天的23:59分
	 */
	public static Date getLatestTimeOfToday() {
		return getLatestTimeOfOneday(new Date());
	}
	/**
	 * 获得某一天的23:59分
	 */
	public static Date getLatestTimeOfOneday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取i小时之前的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateBeforeHour(Date date, int hour) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR, now.get(Calendar.HOUR) - hour);
		return now.getTime();
	}
	
	/**
	 * 获得某一天的00:01分
	 */
	public static Date getFirstMinuteOfOneday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 1);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	

	/**
	 * 获得某一天的00:00分
	 */
	public static Date getZeroOfOneday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * date1是否比date2早
	 * 
	 * @param date1
	 * @param date2
	 *            如果date1是null
	 * @return
	 */
	public static boolean isBefore(Date date1, Date date2) {
		boolean isBefore = false;
		if (date1 != null && date2 == null) {
			isBefore = true;
		}

		if (date1 != null && date2 != null) {
			isBefore = date1.before(date2);
		}

		if (date1 == null && date2 != null) {
			isBefore = false;
		}

		if (date1 == null && date2 == null) {
			isBefore = false;
		}

		// try{
		// isBefore = date1.before(date2);
		// }catch(NullPointerException e){
		// return isBefore;
		// }
		return isBefore;
	}
	
	public static boolean isAfter(Date date1, Date date2) {
		boolean isAfter = false;
		if (date1 != null && date2 == null) {
			isAfter = true;
		}

		if (date1 != null && date2 != null) {
			isAfter = date1.after(date2);
		}

		if (date1 == null && date2 != null) {
			isAfter = false;
		}

		if (date1 == null && date2 == null) {
			isAfter = false;
		}
		//
		// try{
		// isAfter = date1.after(date2);
		// }catch(NullPointerException e){
		// return isAfter;
		// }
		return isAfter;
	}
	
	/**
	 * 获得第二天的23:59分
	 */
	public static Date getLatestTimeOfTomorrow() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND,59);
		return calendar.getTime();
	}
	
	/**
	 * 获得某一天的23:59分
	 */
	public static Date getLastMinuteOfOneday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	/**
	 * 是否数字
	 */
    public static boolean isNumeric(String str) {
           for (int i = str.length(); --i >= 0;) {
               if (!Character.isDigit(str.charAt(i))) {
                   return false;
               }
           }
           return true;
       }
    
    /**
     * 判断是否为数字，包含小数
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str)
                .matches();
    } 
    
    /**
     * double型的数据保留2位小数
     */
    public static double get2Double(double a){  
        DecimalFormat df=new DecimalFormat("0.00");  
        return new Double(df.format(a).toString());  
    }  
}
