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

package com.yqkj.yqframedemo.domain.request;


import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.domain.request.BaseRequest;
import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;
import com.yqkj.yqframedemo.data.bean.HttpUrl;
import com.yqkj.yqframedemo.data.bean.LoginUserBean;
import com.yqkj.yqframedemo.data.bean.MzZcBean;
import com.yqkj.yqframedemo.data.bean.User;
import com.yqkj.yqframedemo.data.repository.CommonListResponse;
import com.yqkj.yqframedemo.data.repository.CommonResponse;
import com.yqkj.yqframedemo.data.repository.DataRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * 用户账户 Request
 * <p>
 * TODO tip 1：Request 通常按业务划分
 * 一个项目中通常存在多个 Request 类，
 * 每个页面配备的 state-ViewModel 实例可根据业务需要持有多个不同的 Request 实例。
 * <p>
 * request 的职责仅限于 "业务逻辑处理" 和 "Event 分发"，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 * <p>
 * Create by oyd at 2021/11/22
 */
public class UrlPramsRequest extends BaseRequest
    implements DefaultLifecycleObserver {

    //TODO tip：👆👆👆 让 accountRequest 可观察页面生命周期，
    // 从而在页面即将退出、且登录请求由于网络延迟尚未完成时，
    // 及时通知数据层取消本次请求，以避免资源浪费和一系列不可预期的问题。

    private final UnPeekLiveData<DataResult<CommonResponse<LoginUserBean>>> tokenLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<CommonResponse<HttpUrl>>> httpUrlLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<CommonListResponse<MzZcBean>>> mzZcLiveData = new UnPeekLiveData<>();

    //TODO tip 2：向 ui 层提供的 request LiveData，使用 "父类的 LiveData" 而不是 "Mutable 的 LiveData"，
    //如此达成了 "唯一可信源" 的设计，也即通过访问控制权限实现 "读写分离"，
    //并且进一步地，使用 ProtectedUnPeekLiveData 类，而不是 LiveData 类，
    //以此来确保消息分发的可靠一致，及 "事件" 场景下的防倒灌特性。


    public ProtectedUnPeekLiveData<DataResult<CommonResponse<LoginUserBean>>> getTokenLiveData() {

        //TODO tip 3：与此同时，为了方便语义上的理解，故而直接将 DataResult 作为 LiveData value 回推给 UI 层，
        //而不是将 DataResult 的泛型实体拆下来单独回推，如此
        //一方面使 UI 层有机会基于 DataResult 的 responseStatus 来分别处理 请求成功或失败的情况下的 UI 表现，
        //另一方面从语义上强调了 该数据是请求得来的结果，是只读的，与 "可变状态" 形成明确的区分，
        //从而方便团队开发人员自然而然遵循 "唯一可信源"/"单向数据流" 的开发理念，规避消息同步一致性等不可预期的错误。


        return tokenLiveData;
    }
    public ProtectedUnPeekLiveData<DataResult<CommonResponse<HttpUrl>>> getHttpUrlLiveData() {

        //TODO tip 3：与此同时，为了方便语义上的理解，故而直接将 DataResult 作为 LiveData value 回推给 UI 层，
        //而不是将 DataResult 的泛型实体拆下来单独回推，如此
        //一方面使 UI 层有机会基于 DataResult 的 responseStatus 来分别处理 请求成功或失败的情况下的 UI 表现，
        //另一方面从语义上强调了 该数据是请求得来的结果，是只读的，与 "可变状态" 形成明确的区分，
        //从而方便团队开发人员自然而然遵循 "唯一可信源"/"单向数据流" 的开发理念，规避消息同步一致性等不可预期的错误。


        return httpUrlLiveData;
    }
    public ProtectedUnPeekLiveData<DataResult<CommonListResponse<MzZcBean>>> getMzZcLiveData() {

        //TODO tip 3：与此同时，为了方便语义上的理解，故而直接将 DataResult 作为 LiveData value 回推给 UI 层，
        //而不是将 DataResult 的泛型实体拆下来单独回推，如此
        //一方面使 UI 层有机会基于 DataResult 的 responseStatus 来分别处理 请求成功或失败的情况下的 UI 表现，
        //另一方面从语义上强调了 该数据是请求得来的结果，是只读的，与 "可变状态" 形成明确的区分，
        //从而方便团队开发人员自然而然遵循 "唯一可信源"/"单向数据流" 的开发理念，规避消息同步一致性等不可预期的错误。


        return mzZcLiveData;
    }
    //登录页面调用
    public void requestLogin(User user) {

        //TODO Tip：lambda 语句只有一行时可简写，具体可结合实际情况选择和使用

        /*DataRepository.getInstance().login(user, dataResult -> {
            tokenLiveData.postValue(dataResult);
        });*/

        DataRepository.getInstance().login(user, tokenLiveData::postValue);
    }

    //登录页面调用
    public void requestHttpUrl(String loginName){
        DataRepository.getInstance().getHttpUrl(loginName, httpUrlLiveData::postValue);
    }
    //登录页面调用
    private void cancelLogin() {
        DataRepository.getInstance().cancelLogin();
    }
    //登录页面调用
    public void requestMzZcList(Map<String,String> map){
        DataRepository.getInstance().getMzZcList(map,mzZcLiveData::postValue);
    }


    //TODO tip：让 accountRequest 可观察页面生命周期，
    // 从而在页面即将退出、且登录请求由于网络延迟尚未完成时，
    // 及时通知数据层取消本次请求，以避免资源浪费和一系列不可预期的问题。

    @Override
    public void onStop(@NonNull @NotNull LifecycleOwner owner) {
        cancelLogin();
    }
}
