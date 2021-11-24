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

package com.yqkj.yqframedemo.data.repository;

import android.util.Log;

import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.data.response.ResponseStatus;
import com.kunminx.architecture.data.response.ResultSource;
import com.kunminx.architecture.utils.SPUtils;
import com.yqkj.yqframedemo.data.api.APIs;
import com.yqkj.yqframedemo.data.api.UrlPramsService;
import com.yqkj.yqframedemo.data.bean.DownloadFile;
import com.yqkj.yqframedemo.data.bean.HttpUrl;
import com.yqkj.yqframedemo.data.bean.LoginUserBean;
import com.yqkj.yqframedemo.data.bean.MzZcBean;
import com.yqkj.yqframedemo.data.bean.User;


import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by oyd at 2021/11/22
 */
public class DataRepository {

    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    private final Retrofit retrofit;

    {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//            .connectTimeout(8, TimeUnit.SECONDS)
//            .readTimeout(8, TimeUnit.SECONDS)
//            .writeTimeout(8, TimeUnit.SECONDS)
//            .addInterceptor(logging)
//            .build();
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

        String baseUrl = SPUtils.getInstance().getString("wfwUrl",APIs.HEAD_BASE_URL);

        retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    /**
     * TODO: 建议在 DataRepository 使用 DataResult 而非 LiveData 来返回结果：
     * liveData 是专用于页面开发的、用于解决生命周期安全问题的组件，
     * 有时候数据并非一定是通过 liveData 来分发给页面，也可能是通过别的组件去通知给非页面的东西，
     * 这时候 repo 方法中内定通过 liveData 分发就不太合适，不如一开始就规定不在数据层通过 liveData 返回结果。
     * <p>
     * @param result result
     */

    /**
     * TODO：模拟下载任务:
     * 可分别用于 普通的请求，和可跟随页面生命周期叫停的请求，
     * 具体可见 ViewModel 和 UseCase 中的使用。
     *
     * @param result 从 Request-ViewModel 或 UseCase 注入 LiveData，用于 控制流程、回传进度、回传文件
     */
    public void downloadFile(DownloadFile downloadFile, DataResult.Result<DownloadFile> result) {

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //模拟下载，假设下载一个文件要 10秒、每 100 毫秒下载 1% 并通知 UI 层
                if (downloadFile.getProgress() < 100) {
                    downloadFile.setProgress(downloadFile.getProgress() + 1);
                    Log.d("TAG", "下载进度 " + downloadFile.getProgress() + "%");
                } else {
                    timer.cancel();
                }
                if (downloadFile.isForgive()) {
                    timer.cancel();
                    downloadFile.setProgress(0);
                    downloadFile.setForgive(false);
                    return;
                }
                result.onResult(new DataResult<>(downloadFile, new ResponseStatus()));
            }
        };

        timer.schedule(task, 100, 100);
    }
    //登录之前获取登录返回域名地址
    public void getHttpUrl(String loginName, DataResult.Result<CommonResponse<HttpUrl>> result) {
        Call<CommonResponse<HttpUrl>> mUserCall = retrofit.create(UrlPramsService.class).getHttpUrl(loginName);
        mUserCall.enqueue(new Callback<CommonResponse<HttpUrl>>() {
            @Override
            public void onResponse(@NotNull Call<CommonResponse<HttpUrl>> call, @NotNull Response<CommonResponse<HttpUrl>> response) {


                ResponseStatus responseStatus = new ResponseStatus(
                        String.valueOf(response.body().code), response.isSuccessful(),String.valueOf(response.body().msg), ResultSource.NETWORK);
                result.onResult(new DataResult<>(response.body(), responseStatus));

            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse<HttpUrl>> call, @NotNull Throwable t) {

                result.onResult(new DataResult<>(null,
                        new ResponseStatus("", false,t.toString(), ResultSource.NETWORK)));
            }
        });

    }

    //TODO tip：模拟可取消的登录请求：
    //
    // Call 上升为成员实例，配合可观察页面生命周期的 accountRequest，
    // 从而在页面即将退出、且登录请求由于网络延迟尚未完成时，
    // 及时通知数据层取消本次请求，以避免资源浪费和一系列不可预期的问题。

    private Call<CommonResponse<LoginUserBean>> mUserCall;

    /**
     * TODO 模拟登录的网络请求
     *
     * @param user   ui 层填写的用户信息
     * @param result 模拟网络请求返回的 token
     */
    public void login(User user, DataResult.Result<CommonResponse<LoginUserBean>> result) {
        mUserCall = retrofit.create(UrlPramsService.class).login(user.getName(), user.getPassword());
        mUserCall.enqueue(new Callback<CommonResponse<LoginUserBean>>() {
            @Override
            public void onResponse(Call<CommonResponse<LoginUserBean>> call, Response<CommonResponse<LoginUserBean>> response) {
                ResponseStatus responseStatus = new ResponseStatus(
                        String.valueOf(response.body().code), response.isSuccessful(),String.valueOf(response.body().msg), ResultSource.NETWORK);

                result.onResult(new DataResult<>(response.body(), responseStatus));
                mUserCall = null;
            }

            @Override
            public void onFailure(Call<CommonResponse<LoginUserBean>> call, Throwable t) {
                result.onResult(new DataResult<>(null,
                        new ResponseStatus("", false,t.getMessage(), ResultSource.NETWORK)));
                mUserCall = null;
            }

        });
    }

    //每周自查列表
    public void getMzZcList(Map<String,String> map, DataResult.Result<CommonListResponse<MzZcBean>> result) {
        Call<CommonListResponse<MzZcBean>> mUserCall = retrofit.create(UrlPramsService.class).getList(map.get("organizationId"),
                map.get("year"),map.get("month"),map.get("page"),map.get("pageSize"));
        mUserCall.enqueue(new Callback<CommonListResponse<MzZcBean>>() {
            @Override
            public void onResponse(Call<CommonListResponse<MzZcBean>> call, Response<CommonListResponse<MzZcBean>> response) {
                ResponseStatus responseStatus = new ResponseStatus(
                        String.valueOf(response.body().code), response.isSuccessful(),String.valueOf(response.body().msg), ResultSource.NETWORK);
                result.onResult(new DataResult<>(response.body(), responseStatus));

            }

            @Override
            public void onFailure(Call<CommonListResponse<MzZcBean>> call, Throwable t) {
                result.onResult(new DataResult<>(null,
                        new ResponseStatus("", false,t.getMessage(), ResultSource.NETWORK)));

            }

        });
    }


    public void cancelLogin() {
        if (mUserCall != null && !mUserCall.isCanceled()) {
            mUserCall.cancel();
            mUserCall = null;
        }
    }


}
