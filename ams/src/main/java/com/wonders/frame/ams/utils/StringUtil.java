package com.wonders.frame.ams.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 3701 on 2015/12/14.
 */
public class StringUtil {

    public static String nvl(String str, String replace) {
        return str == null ? replace : str;
    }

    public static String nullOrEmptyTo(String str, String replace) {
        return ! Chk.spaceCheck(str) ? replace : str;
    }

    public static String decode(String str, String value, String yreplace, String nreplace) {
        return (value == null ? str == value : value.equals(str)) ? yreplace : nreplace;
    }

    public static String nullToEmptyString(Object str) {
        return str == null ? "" : str.toString();
    }

    public static String fromCamelCase(String str) {
        if (!Chk.spaceCheck(str)) {
            return "";
        }
        return str.replaceAll("(\\S)([A-Z])", "$1_$2").toLowerCase();
    }


    public static String toCamelCase(String str) {
        if (!Chk.spaceCheck(str)) {
            return "";
        }
        Matcher matcher = Pattern.compile("(\\S)[_](\\S)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1) + matcher.group(2).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String parseCamelCase(String str) {
        if (!Chk.spaceCheck(str)) {
            return "";
        }
        Matcher matcher = Pattern.compile("([A-Z]|[0-9]+)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
//            System.out.println( matcher.group(1));
            matcher.appendReplacement(sb, "_" + matcher.group(1).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
       String x = parseCamelCase("wrwerwe323423423#sswT");
        System.out.println(x);
    }



}
