package com.wonders.frame.ams.utils;

import com.wonders.frame.ams.utils.Chk;

import java.lang.reflect.Array;
import java.util.Collection;

public class ArrayUtil {
    public static <T> T [] toArray(Collection<T> c,Class<T> clz) {
        T [] arr = (T[]) Array.newInstance(clz, Chk.emptyCheck(c) ? c.size() : 0);
        return arr.length > 0 ? c.toArray(arr) : arr;
    }

    public static String join(Object [] arr){
        return join(arr, ",");



    }

    public static String join(Object [] arr,String joinChar){
        if(arr == null || arr.length == 0 || ! Chk.spaceCheck(joinChar)){
            return "";
        }

        StringBuffer sb = new StringBuffer();

        for(int i = 0 ,len= arr.length ; i < len ; i++){
            sb.append(String.valueOf(arr[i]));
            if( i < len - 1){
                sb.append(joinChar);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String [] ww = new String [] {"333","333"};

        String x = ArrayUtil.join(ww);
        System.out.println(x);

    }


}