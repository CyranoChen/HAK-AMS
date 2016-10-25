package com.wonders.frame.ams.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * Created by 3701 on 2015/10/27.
 */
public class ReflectUtil {
    /**
     * 暴力反射获取字段值
     * @param propertyName
     * @param obj
     * @return
     */
    public static <T>T getValue(String propertyName, Object obj) {
        if(obj == null){
            return null;
        }
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过GET 方法获取字段值
     * @param propertyName
     * @param obj
     * @return
     */
    public static <T> T getValueByGet(String propertyName, Object obj) {
        if(obj == null){
            return null;
        }
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(propertyName,obj.getClass());
            return (T) descriptor.getReadMethod().invoke(obj);
        } catch (Exception e) {
            return getValue(propertyName,obj);
//            e.printStackTrace();
//            return null;
        }


    }

    /**
     *暴利设置值
     * @param propertyName
     * @param obj
     * @param value
     * @return
     */
    public static boolean setValue(String propertyName, Object obj,Object value) {
        if(obj == null){
            return false;
        }
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     *通过set方法设置
     * @param propertyName
     * @param obj
     * @param value
     * @return
     */
    public static boolean setValueBySet(String propertyName, Object obj,Object value) {
        if(obj == null){
            return false;
        }
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(propertyName,obj.getClass());
            descriptor.getWriteMethod().invoke(obj,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }



}
