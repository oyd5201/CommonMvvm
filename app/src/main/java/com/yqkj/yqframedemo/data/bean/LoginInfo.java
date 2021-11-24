package com.yqkj.yqframedemo.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 *  Create by oyd at 2021/11/22
 */

public class LoginInfo implements Serializable {

    private String ifCity;//1是绍兴市学校食堂 2不是
    public List<QiyeMo> organizations;//企业列表

    public String getIfCity() {
        return ifCity;
    }

    public void setIfCity(String ifCity) {
        this.ifCity = ifCity;
    }

    public class QiyeMo implements Serializable {
        public int id;
        public String name;
    }
}
