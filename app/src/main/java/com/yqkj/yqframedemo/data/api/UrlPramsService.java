package com.yqkj.yqframedemo.data.api;

import com.kunminx.architecture.data.repository.CommonListResponse;
import com.kunminx.architecture.data.repository.CommonResponse;
import com.yqkj.yqframedemo.data.bean.HttpUrl;
import com.yqkj.yqframedemo.data.bean.KqXdJlBean;
import com.yqkj.yqframedemo.data.bean.LoginUserBean;
import com.yqkj.yqframedemo.data.bean.MzZcBean;
import com.yqkj.yqframedemo.data.bean.RlBean;

import java.util.List;

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

    @POST("zsaledger/Inspection/getMergeInspectionDetailByDate")
    @FormUrlEncoded
    Call<CommonListResponse<MzZcBean>> getList(
            @Field("organizationId") String orgId,
            @Field("inspectionDate") String date

    );
    @POST("zsaledger/ledger/homePage/getLedgerCalendar")
    @FormUrlEncoded
    Call<CommonResponse<RlBean>> getRlBean(
            @Field("organizationId") String orgId,
            @Field("year") String year,
            @Field("month") String month,
            @Field("ledgerType") String ledgerType


    );
    @POST("zsaledger/dftionAir/selectDftionAirListByDay")
    @FormUrlEncoded
    Call<CommonResponse<List<KqXdJlBean>>> getKqXdList(
            @Field("organizationId") String orgId,
            @Field("year") String year,
            @Field("month") String month,
            @Field("day") String day


    );

}
