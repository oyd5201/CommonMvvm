/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yqkj.yqframedemo.data.bean;

import java.io.Serializable;

/**
 * Create by oyd at 2021/11/22
 */
public class User implements Serializable {
    private int id;
    private String loginName;//用户名
    private String zhName;//姓名
    private String icons;//头像
    private String sex;//
    private String birth;//
    private String email;//
    private String phone;//
    private String passwordSalt;//
    private String icard;//
    private String icardImg;//
    private String age;//
    private String isEmail;//
    private String isPhone;//
    private String rank;
    private String dutyName;//职务名
    private String healthImg;//健康证
    private String healthBegindate;
    private String healthTime;//健康证到期日
    private String entryTime;//入职时间
    private String seniority;//资历
    private String birthdayTime;//
    private String userOrganizationId;
    private String initial;
    private String healthStatus;
    private String appUserToken;
    private String name;
    private String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getIcard() {
        return icard;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public void setIcard(String icard) {
        this.icard = icard;
    }

    public String getIcardImg() {
        return icardImg;
    }

    public void setIcardImg(String icardImg) {
        this.icardImg = icardImg;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(String isEmail) {
        this.isEmail = isEmail;
    }

    public String getIsPhone() {
        return isPhone;
    }

    public void setIsPhone(String isPhone) {
        this.isPhone = isPhone;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getHealthImg() {
        return healthImg;
    }

    public void setHealthImg(String healthImg) {
        this.healthImg = healthImg;
    }

    public String getHealthTime() {
        return healthTime;
    }

    public void setHealthTime(String healthTime) {
        this.healthTime = healthTime;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public String getBirthdayTime() {
        return birthdayTime;
    }

    public void setBirthdayTime(String birthdayTime) {
        this.birthdayTime = birthdayTime;
    }

    public String getUserOrganizationId() {
        return userOrganizationId;
    }

    public void setUserOrganizationId(String userOrganizationId) {
        this.userOrganizationId = userOrganizationId;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getHealthBegindate() {
        return healthBegindate;
    }

    public void setHealthBegindate(String healthBegindate) {
        this.healthBegindate = healthBegindate;
    }

    public String getAppUserToken() {
        return appUserToken;
    }

    public void setAppUserToken(String appUserToken) {
        this.appUserToken = appUserToken;
    }
}