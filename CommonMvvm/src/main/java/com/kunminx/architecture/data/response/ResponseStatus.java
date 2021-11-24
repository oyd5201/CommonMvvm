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

package com.kunminx.architecture.data.response;

import com.kunminx.architecture.R;
import com.kunminx.architecture.utils.ToastUtils;
import com.kunminx.architecture.utils.Utils;

/**
 * TODO：本类仅用作示例参考，请根据 "实际项目需求" 配置自定义的 "响应状态元信息"
 * <p>
 * Create by oyd at 2021/11/22
 */
public class ResponseStatus {

    private String responseCode = "";
    private String msg = "";
    private boolean success = true;
    private Enum source = ResultSource.NETWORK;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseStatus() {
    }

    public ResponseStatus(String responseCode, boolean success,String msg) {
        this.responseCode = responseCode;
        this.msg = msg;
        if (responseCode.equals("200")||responseCode.equals("10000"))
            this.success = success;
        else if(responseCode.equals("404")){
            this.success = false;
            ToastUtils.showLongToast(Utils.getApp().getApplicationContext(),Utils.getApp().getString(R.string.network_not_good));
            return;
        } else {
            this.success = false;
            ToastUtils.showLongToast(Utils.getApp().getApplicationContext(),msg);
            return;
        }
    }

    public ResponseStatus(String responseCode, boolean success,String msg, Enum source) {
        this(responseCode, success,msg);
        this.source = source;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public Enum getSource() {
        return source;
    }
}
