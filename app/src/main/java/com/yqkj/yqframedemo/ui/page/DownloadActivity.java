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

package com.yqkj.yqframedemo.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.architecture.ui.page.BaseActivity;
import com.kunminx.architecture.ui.page.BaseFragment;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.yqkj.yqframedemo.BR;
import com.yqkj.yqframedemo.R;
import com.yqkj.yqframedemo.ui.model.DownLoadViewModel;
import com.yqkj.yqframedemo.ui.model.MainActivityViewModel;

/**
 * Create by oyd at 2021/11/22
 */
public class DownloadActivity extends BaseActivity {

    //TODO tip 1：每个页面都要单独配备一个 state-ViewModel，职责仅限于 "状态托管和恢复"，
    //event-ViewModel 则是用于在 "跨页面通信" 的场景下，承担 "唯一可信源"，

    private DownLoadViewModel mState;
    private MainActivityViewModel mActivityScopeState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(DownLoadViewModel.class);
        mActivityScopeState = getActivityScopeViewModel(MainActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip 1: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
        // 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。

        return new DataBindingConfig(R.layout.activity_download, BR.vm, mState)
            .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //TODO tip 2：绑定跟随视图控制器生命周期的、可叫停的、单独放在 UseCase 中处理的业务
        getLifecycle().addObserver(mState.downloadRequest.getCanBeStoppedUseCase());

        mActivityScopeState.downloadRequest.getDownloadFileLiveData()
                .observe(this, dataResult -> {
                    if (dataResult.getResponseStatus().isSuccess()) {
                        mState.progress.set(dataResult.getResult().getProgress());
                    }
                });

        mState.downloadRequest.getDownloadFileCanBeStoppedLiveData()
                .observe(this, dataResult -> {
                    if (dataResult.getResponseStatus().isSuccess()) {
                        mState.progress_cancelable.set(dataResult.getResult().getProgress());
                    }
                });
    }


    public class ClickProxy {

        public void back() {
           finish();
        }

        public void testDownload() {
            mActivityScopeState.downloadRequest.requestDownloadFile();
        }

        //TODO tip 4: 在 UseCase 中 执行可跟随生命周期中止的下载任务

        public void testLifecycleDownload() {
            mState.downloadRequest.requestCanBeStoppedDownloadFile();
        }
    }
}
