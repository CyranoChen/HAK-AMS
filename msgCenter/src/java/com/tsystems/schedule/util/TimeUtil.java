/**
 * 
 */
package com.tsystems.schedule.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsystems.schedule.exception.InvalidConfigException;

/**
 * @ClassName: TimeUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-4 上午11:00:46
 * 
 */
public class TimeUtil {
	private static final Logger log = LoggerFactory.getLogger(TimeUtil.class);
	/**
	 * 日期格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 时间格式
	 */
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/*public static void main(String[] args){
		Date now = new Date();
		System.out.println(now.getTime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		System.out.println(cal.getTimeInMillis());
	}*/
	
	/** 
	* @Title: getMills 
	* @Description: TODO(返回指定字符串类型 时间距离UTC时间的毫秒数) 
	* @param @param time
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws 
	*/
	public static long getMills(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long mills = 0;
		try {
			Date last = sdf.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(last);
			mills = cal.getTimeInMillis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("unexcepted Exception when {}","getMills",e);
			//e.printStackTrace();
		}

		return mills;

	}

	/**
	 * 获取上次执行是否成功
	 * 
	 * @return
	 */
	public static boolean isExec(String lastTime, String time, String interval) {
		if(lastTime == null){
			return false;
		}
		Calendar cal = Calendar.getInstance();
		long intervalMills = getIntervalSec(interval) * 1000;
		long now = cal.getTimeInMillis();
		long last = getMills(lastTime);
		if (now - last < intervalMills) {
			return true;
		}
		return false;
	}

	/**
	 * 计算从当前时间currentDate开始，下一次执行的时间差 （按小时 分钟 秒 轮询） 若当前时间小于目标时间，则两时间相减；反之，天数+1后
	 * 两时间相减
	 * 
	 * @return
	 */
	public static long getDelay(String time, String interval) {
		long excMills = getTimeSec(time);
		long intervalMills = getIntervalSec(interval);

		Calendar cal = Calendar.getInstance();
		long now = (cal.get(Calendar.HOUR_OF_DAY) * 60L + cal
				.get(Calendar.MINUTE))
				* 60L + cal.get(Calendar.SECOND); // 当前时间在当天的秒数
		long delay = 0;
		if (now == excMills) {
			// 不休眠，立即运行
		} else if (now > excMills) {
			// 在可运行时间范围内
			delay = intervalMills - (now - excMills) % intervalMills;
		} else if (now < excMills) {
			// 在可运行时间范围前
			delay = excMills - now;
		} else {
			// 在可运行时间范围后
			delay = 24 * 60 * 60 - now + excMills;
		}
		return delay;
	}

	/**
	 * 将HH:MM格式输入的字符串转为秒数
	 */
	public static long getTimeSec(String s) throws InvalidConfigException {
		Pattern pattern = Pattern.compile("^[0-2]\\d\\:\\d{2}$");
		Matcher matcher = pattern.matcher(s);
		if (matcher.matches()) {
			String h = s.substring(0, 2);
			String m = s.substring(3, 5);
			long lh = Long.parseLong(h);
			long lm = Long.parseLong(m);
			return (lh * 60 + lm) * 60;
		} else {
			throw new InvalidConfigException("无效字符串：" + s);
		}
	}

	/**
	 * 将例如 2H 3M 4S的字符串转换为Long的秒数
	 */
	public static long getIntervalSec(String s) throws InvalidConfigException {
		if (s == null || s.length() == 0)
			throw new InvalidConfigException("无效字符串：" + s);

		s = s.toUpperCase();
		String num = s;
		String unit = "S"; // 默认情况下为单位为秒的一串数字
		if (s.endsWith("H") || s.endsWith("M") || s.endsWith("S")) {
			// 配置的值包含单位的情况
			num = s.substring(0, s.length() - 1);
			unit = s.substring(s.length() - 1, s.length());
		}

		// 单位
		long lunit = 0;
		if ("H".equals(unit)) {
			lunit = 60 * 60;
		} else if ("M".equals(unit)) {
			lunit = 60;
		} else if ("S".equals(unit)) {
			lunit = 1;
		} else {
			throw new InvalidConfigException("无效字符串：" + s);
		}

		// 值
		long lnum = 0;
		try {
			lnum = Long.parseLong(num);
		} catch (NumberFormatException e) {
			throw new InvalidConfigException(e);
		}

		return lnum * lunit;
	}

	public static String getNow() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
			String date = sdf.format(new Date());
			return date;
		} catch (Exception e) {
		}
		return null;
	}
}
