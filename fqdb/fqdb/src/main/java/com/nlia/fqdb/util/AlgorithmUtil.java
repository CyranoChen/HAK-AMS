package com.nlia.fqdb.util;

import java.text.DecimalFormat;


public class AlgorithmUtil {
	private static long startTime;
	private static long endTime;
	
	public static void algorithmStart(){
	 startTime  = System.currentTimeMillis();
	}
	
	/**
	 * 返回算法话费时间
	 * @return
	 */
	public static String algorithmFinish(){
		DecimalFormat df = new DecimalFormat("#.#");
		 endTime  = System.currentTimeMillis();
		 return df.format((endTime - startTime) / 1000.0);
		}
}
