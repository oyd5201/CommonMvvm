package com.yqkj.yqframedemo.data.bean;

public class MzZcBean {
    public String id;
    public String checkPersonName;
    private String checkPersonDutyName;
    private String isQualifiedName;
    public String creatTime;
    public MzZcBean(){

    }

    public MzZcBean(String checkPersonName, String checkPersonDutyName, String isQualifiedName, String creatTime) {
        this.checkPersonName = checkPersonName;
        this.checkPersonDutyName = checkPersonDutyName;
        this.isQualifiedName = isQualifiedName;
        this.creatTime = creatTime;
    }

    public String getCheckPersonName() {
        return checkPersonName;
    }

    public void setCheckPersonName(String checkPersonName) {
        this.checkPersonName = checkPersonName;
    }

    public String getCheckPersonDutyName() {
        return checkPersonDutyName;
    }

    public void setCheckPersonDutyName(String checkPersonDutyName) {
        this.checkPersonDutyName = checkPersonDutyName;
    }

    public String getIsQualifiedName() {
        return isQualifiedName;
    }

    public void setIsQualifiedName(String isQualifiedName) {
        this.isQualifiedName = isQualifiedName;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }
}
