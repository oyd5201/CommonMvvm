package com.yqkj.yqframedemo.data.bean;


import android.text.TextUtils;

/**
 * Created by lenovo on 2018/3/27.
 * 字典类
 */

public class DictionaryBean {
    private String id;//子id
    private String sysDataGroupId;//父id
    private String keyValue;//类型名称
    private String keyName;//
    private String isFinal;//
    private String description;//描述
    private String value;//类型名称
    private String type;//
    private String chooseType;//0未选中，1选中


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return isChecked;
    }


    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private String name;//标签名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysDataGroupId() {
        return sysDataGroupId;
    }

    public void setSysDataGroupId(String sysDataGroupId) {
        this.sysDataGroupId = sysDataGroupId;
    }

    public String getKeyValue() {
        if (TextUtils.isEmpty(keyValue)) {
            return getName();
        }
        return keyValue;
    }

    public String getChooseType() {
        return chooseType;
    }

    public void setChooseType(String chooseType) {
        this.chooseType = chooseType;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(String isFinal) {
        this.isFinal = isFinal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int icon;//手动添加，专项检查中使用
    public boolean isChecked;//手动添加，人员考核中，筛选条件时使用
}
