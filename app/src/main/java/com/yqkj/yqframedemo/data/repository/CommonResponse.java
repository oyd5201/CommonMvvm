package com.yqkj.yqframedemo.data.repository;

import java.util.List;

/**
 * Create by oyd at 2021/11/22
 */

public class CommonResponse<T> extends BaseModel {
    public int code;
    public String msg;
    public T data;

    public CommonResponse(String msgInfo) {
        this.msg = msgInfo;
    }

    public boolean isSuccess() {
        return code == 10000;
    }

    public boolean isNeedLogin() {
        return code == 67;
    }
    class ListMo {
        private int total;

        private List<T> list;
    }
}
