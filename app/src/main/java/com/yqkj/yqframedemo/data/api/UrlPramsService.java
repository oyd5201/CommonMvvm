package com.yqkj.yqframedemo.data.api;

import com.kunminx.architecture.data.repository.CommonListResponse;
import com.kunminx.architecture.data.repository.CommonResponse;
import com.yqkj.yqframedemo.data.bean.HttpUrl;
import com.yqkj.yqframedemo.data.bean.LoginUserBean;
import com.yqkj.yqframedemo.data.bean.MzZcBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Create by oyd at 2021/11/22
 */
public interface UrlPramsService {

    @POST("zsabasedata/userLogin/loginGetUrlByLoginName")
    @FormUrlEncoded
    Call<CommonResponse<HttpUrl>> getHttpUrl(
        @Field("loginName") String loginName
    );


    @POST("zsabasedata/systemUser/login/blockCy")
    @FormUrlEncoded
    Call<CommonResponse<LoginUserBean>> login(
            @Field("loginName") String loginName,
            @Field("password") String password
    );

    @POST("zsacheck/checkData/getOrgLastCheckListByMonth")
    @FormUrlEncoded
    Call<CommonListResponse<MzZcBean>> getList(
            @Field("organizationId") String orgId,
            @Field("year") String year,
            @Field("month") String month,
            @Field("page") String page,
            @Field("pageSize") String pageSize

    );

}
