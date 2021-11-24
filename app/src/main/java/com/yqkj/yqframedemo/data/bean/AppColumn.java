package com.yqkj.yqframedemo.data.bean;

/**
 * Create by oyd at 2021/11/22
 */

public class AppColumn {
    private String id;//
    private String columnName;//
    private String columnId;//
    private String columnType;//
    private String columnCode;//
    private int imageReso;
    private boolean hasWarning;//是否需要展示预警右方的红点
    private String count;//示警个数

    public AppColumn(String columnCode, int imageReso, String columnName, String columnType) {
        this.columnCode = columnCode;
        this.columnName = columnName;
        this.imageReso = imageReso;
        this.columnType = columnType;
    }

    public boolean isHasWarning() {
        return hasWarning;
    }

    public void setHasWarning(boolean hasWarning) {
        this.hasWarning = hasWarning;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public int getImageReso() {
        return imageReso;
    }

    public void setImageReso(int imageReso) {
        this.imageReso = imageReso;
    }
}
