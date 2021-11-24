package com.yqkj.yqframedemo.ui.page;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.kunminx.architecture.ui.page.BaseActivity;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.utils.BarUtils;
import com.kunminx.architecture.utils.SPUtils;
import com.kunminx.architecture.utils.ToastUtils;
import com.yqkj.yqframedemo.BR;
import com.yqkj.yqframedemo.R;
import com.yqkj.yqframedemo.data.bean.HttpUrl;
import com.yqkj.yqframedemo.data.bean.LoginUserBean;
import com.yqkj.yqframedemo.data.bean.User;

import com.yqkj.yqframedemo.domain.message.DrawerCoordinateManager;
import com.yqkj.yqframedemo.ui.model.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginInfoActivity extends BaseActivity {
    private ProgressDialog progressDialog ;
    //TODO tip 1：每个页面都要单独配备一个 state-ViewModel，职责仅限于 "状态托管和恢复"，
    //event-ViewModel 则是用于在 "跨页面通信" 的场景下，承担 "唯一可信源"，

    private LoginViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(LoginViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
        // 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。

        return new DataBindingConfig(R.layout.activity_login_info, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = BarUtils.createSpinnerProgressDialog(this,"提示",getString(R.string.login_text));

        //TODO tip：让 urlPramsRequest 可观察页面生命周期，
        // 从而在页面即将退出、且登录请求由于网络延迟尚未完成时，
        // 及时通知数据层取消本次请求，以避免资源浪费和一系列不可预期的问题。
        getLifecycle().addObserver(mState.urlPramsRequest);
        //TODO tip 2：将 request 作为 state-ViewModel 的成员暴露给 Activity/Fragment，
        // 如此便于语义的明确，以及实现多个 request 在 state-ViewModel 中的组合和复用。


        mState.urlPramsRequest.getHttpUrlLiveData().observe(this,stringDataResult -> {
            progressDialog.dismiss();
            if (!stringDataResult.getResponseStatus().isSuccess()) {
                progressDialog.dismiss();
                return;
            }
            HttpUrl httpUrl = stringDataResult.getResult().data;
            SPUtils.getInstance().put( "wfwUrl", "http://" + httpUrl.getUrl());
            User user = new User(mState.name.get(), mState.password.get());
            mState.urlPramsRequest.requestLogin(user);


        });

        mState.urlPramsRequest.getTokenLiveData().observe(this, dataResult -> {
            progressDialog.dismiss();
            if (!dataResult.getResponseStatus().isSuccess()) {
//
                return;
            }

            LoginUserBean loginUserBean = dataResult.getResult().data;


            SPUtils.getInstance().put("token", loginUserBean.getUser().getToken());//TOKEN
            SPUtils.getInstance().put( "id", loginUserBean.getUser().getId());// 用户资料id
            if (loginUserBean.getOrganizations().size() > 0) {

                //企业ID
                SPUtils.getInstance().put("jydId", loginUserBean.getOrganizations().get(0).getId() + "");// 经营点id

                SPUtils.getInstance().put("cityName", loginUserBean.getOrganizations().get(0).getCityName());

            } else {
                ToastUtils.showShortToast(this, "尚未关联相关企业");
                return;
            }
            //TODO 登录成功后进行的下一步操作...
            startActivity(new Intent(this,ListActivity.class));
        });
    }



    public class ClickProxy {

        public void back() {
            finish();
        }

        public void login() {

            //TODO tip 3：通过 xml 中的双向绑定，使得能够通过 state-ViewModel 中与控件发生绑定的"可观察数据"拿到控件的数据，
            // 避免直接接触控件实例而埋下视图实例 null 安全的一致性隐患。

            if (TextUtils.isEmpty(mState.name.get()) || TextUtils.isEmpty(mState.password.get())) {
                ToastUtils.showLongToast(getApplicationContext(), getString(R.string.username_or_pwd_incomplete));
                return;
            }
            progressDialog.show();
            mState.urlPramsRequest.requestHttpUrl(mState.name.get());

        }

    }

}
