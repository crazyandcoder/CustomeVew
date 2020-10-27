package com.crazyandcoder.top.customeview.bean;

public class ViewItemBean {

    @AView
    private int type;
    private String name;

    public ViewItemBean(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
