package com.yqkj.yqframedemo.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.architecture.ui.page.BaseActivity;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.utils.SPUtils;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yqkj.yqframedemo.BR;
import com.yqkj.yqframedemo.R;
import com.yqkj.yqframedemo.data.bean.MzZcBean;
import com.yqkj.yqframedemo.databinding.ActivityListBinding;
import com.yqkj.yqframedemo.ui.model.MzZcListViewModel;
import com.yqkj.yqframedemo.ui.page.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends BaseActivity {

    //TODO tip 1：每个页面都要单独配备一个 state-ViewModel，职责仅限于 "状态托管和恢复"，
    //event-ViewModel 则是用于在 "跨页面通信" 的场景下，承担 "唯一可信源"，

    private ActivityListBinding activityListBinding;

    private MzZcListViewModel mState;
    private int page = 1;
    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(MzZcListViewModel.class);
    }
    private ListAdapter listAdapter ;
    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
        // 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。
        listAdapter = new ListAdapter(this);
        DataBindingConfig dataBindingConfig = new DataBindingConfig(R.layout.activity_list, BR.vm, mState)
                .addBindingParam(BR.adapter,listAdapter);

        return  dataBindingConfig;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityListBinding = (ActivityListBinding) getBinding();

        getLifecycle().addObserver(mState.urlPramsRequest);



        mState.urlPramsRequest.getMzZcLiveData().observe(this, dataResult -> {

            if (!dataResult.getResponseStatus().isSuccess()) {

                return;
            }

            List<MzZcBean> mzZcBeanList = new ArrayList<>();
            mzZcBeanList.addAll(dataResult.getResult().data.list);
            mState.list.setValue(mzZcBeanList);
        });
        if (mState.urlPramsRequest.getMzZcLiveData().getValue() == null) {
            getTzList();
        }


        activityListBinding.refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.FixedBehind).setPrimaryColorId(R.color.black).setAccentColorId(android.R.color.white));
        activityListBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();
                        page = 1;
                        getTzList();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = page + 1;
                        getTzList();
                        refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                    }
                }, 0);
            }
        });

        activityListBinding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this,DownloadActivity.class));
            }
        });
    }

    private void getTzList(){
        Map<String ,String> map = new HashMap<>();
        map.put("organizationId",SPUtils.getInstance().getString("jydId"));
        map.put("year","2021");
        map.put("month","11");
        map.put("page",page+"");
        map.put("pageSize","11");
        mState.urlPramsRequest.requestMzZcList(map);

    }


}
