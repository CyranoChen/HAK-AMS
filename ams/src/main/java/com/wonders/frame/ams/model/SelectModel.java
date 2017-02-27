package com.wonders.frame.ams.model;

/**
 * 此对象专供选择框的键值对设置
 */
public class SelectModel {
    // 选项的key
    private String key;
    // 选项的value
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SelectModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
