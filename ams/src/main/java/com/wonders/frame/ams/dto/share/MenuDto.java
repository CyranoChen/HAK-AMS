package com.wonders.frame.ams.dto.share;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by 3701 on 2015/9/19.
 */
public class MenuDto {
    private String id;
    private String pid;
    private String name;
    private String path;
    private String type;

    private List<MenuDto> children = new ArrayList<MenuDto>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDto> children) {
        this.children = children;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
