package com.yqkj.yqframedemo.data.bean;

import java.io.Serializable;

/**
 * Create by oyd at 2021/11/22
 */

public class Organization implements Serializable {
    private int id;//企业ID
    private String name;
    private String fullName;//企业名称
    private String operateImg;//企业门面
    private String province;//
    private String provinceName;//
    private String city;//
    private String cityName;//
    private String area;//
    private String areaName;//
    private String parentId; //学校id
    private String street;//
    private String streetName;//
    private String detailAddress;//
    private String full_address;//
    private String tel;//
    private String chargeArea;//分管区域
    private String chargeType;//分管类型

//    private String titleName;//
    private String appTitleName;//标题名称
    private String corporate;//负责人
    private String permitnumberno;//许可证号
    private String aramlev;//你度评级
//    private int dynamicRating;//动态评级
    private String dynamicRating;
    private int monitorStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(int monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperateImg() {
        return operateImg;
    }

    public void setOperateImg(String operateImg) {
        this.operateImg = operateImg;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getChargeArea() {
        return chargeArea;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setChargeArea(String chargeArea) {
        this.chargeArea = chargeArea;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

//    public String getTitleName() {
//        return titleName;
//    }
//
//    public void setTitleName(String titleName) {
//        this.titleName = titleName;
//    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public String getPermitnumberno() {
        return permitnumberno;
    }

    public void setPermitnumberno(String permitnumberno) {
        this.permitnumberno = permitnumberno;
    }

    public String getAramlev() {
        return aramlev;
    }

    public void setAramlev(String aramlev) {
        this.aramlev = aramlev;
    }

//    public int getDynamicRating() {
//        return dynamicRating;
//    }
//
//    public void setDynamicRating(int dynamicRating) {
//        this.dynamicRating = dynamicRating;
//    }


    public String getDynamicRating() {
        return dynamicRating;
    }

    public void setDynamicRating(String dynamicRating) {
        this.dynamicRating = dynamicRating;
    }


    public String getAppTitleName() {
        return appTitleName;
    }

    public void setAppTitleName(String appTitleName) {
        this.appTitleName = appTitleName;
    }

    public boolean isCheck;//是否选中
}
