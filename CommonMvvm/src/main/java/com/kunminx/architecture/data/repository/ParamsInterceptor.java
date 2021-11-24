package com.kunminx.architecture.data.repository;

import static com.kunminx.architecture.data.config.Configs.getRequestParams;

import com.kunminx.architecture.utils.SignUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Create by oyd at 2021/11/22
 */

public class ParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Map<String, String> params = getRequestParams(oldRequest.url());
        //如果公共请求参数不为空,则构建新的请求
        Request.Builder newRequestBuilder = oldRequest.newBuilder();
        //GET请求则使用HttpUrl.Builder来构建
        if ("GET".equalsIgnoreCase(oldRequest.method()) || "POST".equalsIgnoreCase(oldRequest.method())) {
            Map<String, String> allParams = new HashMap<>();
            HttpUrl httpUrl = oldRequest.url();
            int count = oldRequest.url().querySize();
            for (int i = 0; i < count; i++) {
                allParams.put(httpUrl.queryParameterName(i), httpUrl.queryParameterValue(i));
            }
            HttpUrl.Builder httpUrlBuilder = oldRequest.url().newBuilder();
            for (String key : params.keySet()) {
                httpUrlBuilder.addQueryParameter(key, params.get(key));
                allParams.put(key, params.get(key));
            }
            httpUrlBuilder.addQueryParameter("_at", SignUtils.signTopRequest(allParams));
            httpUrlBuilder.getClass();
            newRequestBuilder.url(httpUrlBuilder.build());
        } else {
            // 如果原请求是表单请求 没有使用
            if (oldRequest.body() instanceof FormBody) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                for (String key : params.keySet()) {
                    formBodyBuilder.add(key, params.get(key));
                }
                FormBody oldFormBody = (FormBody) oldRequest.body();
                int size = oldFormBody.size();
                for (int i = 0; i < size; i++) {
                    formBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i));
                }
                newRequestBuilder.post(formBodyBuilder.build());
            }
        }
        return chain.proceed(newRequestBuilder.build());
    }
}
