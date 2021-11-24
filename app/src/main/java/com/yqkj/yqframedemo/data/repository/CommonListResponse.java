package com.yqkj.yqframedemo.data.repository;

import java.util.List;

/**
 * Create by oyd at 2021/11/22
 */

public class CommonListResponse<T> extends BaseModel {
    public int code;
    public String msg;
    public ListMo<T> data;

    public CommonListResponse(String msgInfo) {
        this.msg = msgInfo;
    }


    public class ListMo<T> {
        public int total;
        public List<T> list;
    }
}
