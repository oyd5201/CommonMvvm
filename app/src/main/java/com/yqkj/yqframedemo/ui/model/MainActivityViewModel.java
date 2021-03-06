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

package com.yqkj.yqframedemo.ui.model;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yqkj.yqframedemo.domain.request.DownloadRequest;


/**
 * TODO tip：每个页面都要单独准备一个 state-ViewModel，
 * 来托管 DataBinding 绑定的临时状态，以及视图控制器重建时状态的恢复。
 * <p>
 * 此外，state-ViewModel 的职责仅限于 状态托管，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 * <p>
 * Create by oyd at 2021/11/22
 */
public class MainActivityViewModel extends ViewModel {

    public final ObservableBoolean isDrawerOpened = new ObservableBoolean();

    //TODO 此处用于绑定的状态，使用 LiveData 而不是 ObservableField，
    // 主要是考虑到 ObservableField 具有防抖的特性，不适合该场景。

    public final MutableLiveData<Boolean> openDrawer = new MutableLiveData<>(false);

    public final MutableLiveData<Boolean> allowDrawerOpen = new MutableLiveData<>(true);

    //TODO tip 2：将 request 作为 ViewModel 的成员暴露给 Activity/Fragment，
    // 如此便于语义的明确，以及实现多个 request 在 ViewModel 中的组合和复用。

    public final DownloadRequest downloadRequest = new DownloadRequest();

}
