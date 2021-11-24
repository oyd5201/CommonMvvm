package com.yqkj.yqframedemo.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 *  Create by oyd at 2021/11/22
 */

public class LoginUserBean implements Serializable {
    private List< AppColumn> appColumnList;
    private List< AppColumn> appColumnAll;
    private  Organization organization;
    private String isZh;//分管类型
    private  OrganizationDetail organizationDetail;
    private  RoleOrganization roleOrganization;
    private LoginInfo loginInfo;
    private User user;
    private List< Organization> organizations;
    private String type;

    public String getIsZh() {
        return isZh;
    }

    public void setIsZh(String isZh) {
        this.isZh = isZh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOrganizations(List< Organization> organizations) {
        this.organizations = organizations;
    }
    public List< Organization> getOrganizations() {
        return organizations;
    }

    public  Organization getOrganization() {
        return organization;
    }

    public void setOrganization( Organization organization) {
        this.organization = organization;
    }

    public  RoleOrganization getRoleOrganization() {
        return roleOrganization;
    }

    public void setRoleOrganization( RoleOrganization roleOrganization) {
        this.roleOrganization = roleOrganization;
    }

    public List< AppColumn> getAppColumnList() {
        return appColumnList;
    }

    public void setAppColumnList(List< AppColumn> appColumnList) {
        this.appColumnList = appColumnList;
    }

    public List< AppColumn> getAppColumnAll() {
        return appColumnAll;
    }

    public void setAppColumnAll(List< AppColumn> appColumnAll) {
        this.appColumnAll = appColumnAll;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public  OrganizationDetail getOrganizationDetail() {
        return organizationDetail;
    }

    public void setOrganizationDetail( OrganizationDetail organizationDetail) {
        this.organizationDetail = organizationDetail;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
