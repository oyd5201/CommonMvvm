package com.yqkj.yqframedemo.data.bean;

import java.io.Serializable;

/**
 * 版本信息
 * Create by oyd at 2021/11/22
 */

public class VersionInfoBean implements Serializable {

    private Integer fid;
    private String version_info;//当前版本描述信息
    private String frenew_time;//发布时间
    private String fstatus;//状态 1已发布,2已保存为发布
    private String ftype;//类型(android,ios)  1 android ,2 ios
    private String fdownloadAddress;//下载地址 文件服务器地址不要
    private String version_num;//版本号

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getVersion_info() {
        return version_info;
    }

    public void setVersion_info(String version_info) {
        this.version_info = version_info;
    }

    public String getFrenew_time() {
        return frenew_time;
    }

    public void setFrenew_time(String frenew_time) {
        this.frenew_time = frenew_time;
    }

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getFdownloadAddress() {
        return fdownloadAddress;
    }

    public void setFdownloadAddress(String fdownloadAddress) {
        this.fdownloadAddress = fdownloadAddress;
    }

    public String getVersion_num() {
        return version_num;
    }

    public void setVersion_num(String version_num) {
        this.version_num = version_num;
    }
}
