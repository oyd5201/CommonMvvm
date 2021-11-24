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

package com.kunminx.architecture.data.repository;

import com.kunminx.architecture.data.api.APIs;
import com.kunminx.architecture.utils.SPUtils;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Create by oyd at 2021/11/22
 */
public class CommonRetrofit {

    private static final CommonRetrofit S_REQUEST_MANAGER = new CommonRetrofit();
    private CommonRetrofit() {
    }

    public static CommonRetrofit getInstance() {
        return S_REQUEST_MANAGER;
    }

    public Retrofit getRetrofit(){

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //debug模式添加log信息拦截
        if (Logger.isEnabled()) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(interceptor);
        }

        okHttpBuilder.addInterceptor(new ParamsInterceptor());
//        okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
        // 设置网络连接失败时自动重试
        okHttpBuilder.retryOnConnectionFailure(true);
        // 设置连接超时
        okHttpBuilder.connectTimeout(5, TimeUnit.SECONDS);
        // 设置写超时
        okHttpBuilder.writeTimeout(20, TimeUnit.SECONDS);
        // 设置读超时
        okHttpBuilder.readTimeout(10, TimeUnit.SECONDS);

        String baseUrl = SPUtils.getInstance().getString("wfwUrl", APIs.HEAD_BASE_URL);

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit;
    }






}
