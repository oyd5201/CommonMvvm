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

package com.kunminx.architecture.data.config;

import android.os.Environment;

import com.kunminx.architecture.utils.KvSpUtil;
import com.kunminx.architecture.utils.SPUtils;
import com.kunminx.architecture.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;

/**
 * Create by oyd at 2021/11/22
 */
public class Configs {

    public static final String COVER_PATH = Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();

    public static final String TOKEN = "token";

    /**
     * 配置网络请求头
     */
    public static Map<String, String> getRequestHeader() {
        return new HashMap<String, String>() {{

        }};
    }

    /**
     * 配置网络请求参数
     * @param url
     */
    public static Map<String, String> getRequestParams(HttpUrl url) {

        return new HashMap<String, String>() {{
            //可以在这里配置公共参数
            if(!url.toString().contains("userLogin/loginGetUrlByLoginName")&&!url.toString().contains("systemUser/login/blockCy")) {
                put("token",  KvSpUtil.INSTANCE.decodeString("token", ""));
                put("userId", KvSpUtil.INSTANCE.decodeInt("id")+"");
            }
        }};
    }
}
