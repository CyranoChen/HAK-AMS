package com.wonders.frame.ams.model.basic;

/**
 * Created by VideoMonster on 2016/12/8.
 */
public class RuleExpModel {
    private String id;
    private String key;
    private String logic;
    private String value;
    private String pid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "RuleExpModel{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", logic='" + logic + '\'' +
                ", value='" + value + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}
