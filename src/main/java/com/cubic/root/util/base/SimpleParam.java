package com.cubic.root.util.base;


import java.util.Map;

public class SimpleParam {
    private String className;
    private Map<String,Object> set;
    private Map<String,Object> where;
    private int limit;
    private int num;
    private String[] fields;
    private String[] orders;
    private Boolean[] ascs;

    public Map<String, Object> getSet() {
        return set;
    }

    public void setSet(Map<String, Object> set) {
        this.set = set;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Object> getWhere() {
        return where;
    }

    public void setWhere(Map<String, Object> where) {
        this.where = where;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

    public Boolean[] getAscs() {
        return ascs;
    }

    public void setAscs(Boolean[] ascs) {
        this.ascs = ascs;
    }
}
