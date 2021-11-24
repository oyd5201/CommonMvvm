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

package com.yqkj.yqframedemo.domain.usecase;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.domain.usecase.UseCase;
import com.yqkj.yqframedemo.data.bean.DownloadFile;
import com.yqkj.yqframedemo.data.repository.DataRepository;


/**
 * UseCase 示例，实现 LifeCycle 接口，单独服务于 有 “叫停” 需求 的业务
 * <p>
 * TODO tip：
 * 同样是“下载”，我不是在数据层分别写两个方法，
 * 而是遵循开闭原则，在 ViewModel 和 数据层之间，插入一个 UseCase，来专门负责可叫停的情况，
 * 除了开闭原则，使用 UseCase 还有个考虑就是避免内存泄漏，
 * <p>
 * Create by oyd at 2021/11/22
 */
public class CanBeStoppedUseCase extends UseCase<CanBeStoppedUseCase.RequestValues,
        CanBeStoppedUseCase.ResponseValue> implements DefaultLifecycleObserver {

    private final DownloadFile mDownloadFile = new DownloadFile();

    //TODO tip：让 CanBeStoppedUseCase 可观察页面生命周期，
    // 从而在页面即将退出、且下载请求尚未完成时，
    // 及时通知数据层取消本次请求，以避免资源浪费和一系列不可预期的问题。

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        if (getRequestValues() != null) {
            mDownloadFile.setForgive(true);
            mDownloadFile.setProgress(0);
            mDownloadFile.setFile(null);
            getUseCaseCallback().onError();
        }
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        //访问数据层资源，在 UseCase 中处理带叫停性质的业务

        DataRepository.getInstance().downloadFile(mDownloadFile, dataResult -> {
            getUseCaseCallback().onSuccess(new ResponseValue(dataResult));
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final DataResult<DownloadFile> mDataResult;

        public ResponseValue(DataResult<DownloadFile> dataResult) {
            mDataResult = dataResult;
        }

        public DataResult<DownloadFile> getDataResult() {
            return mDataResult;
        }
    }
}
