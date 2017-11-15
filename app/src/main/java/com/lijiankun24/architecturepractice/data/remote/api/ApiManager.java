package com.lijiankun24.architecturepractice.data.remote.api;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiManager.java
 * <p>
 * Created by lijiankun on 17/7/8.
 */

public class ApiManager {

    private static final String GIRL_URL = "http://gank.io/";

    private static final String ZHIHU_URL = "https://news-at.zhihu.com/";

    private static ApiManager INSTANCE;

    private static ApiGirl sApiGirl;

    private static ApiZhihu sApiZhihu;

    private ApiManager() {
    }

    public static ApiManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiManager();
                }
            }
        }
        return INSTANCE;
    }

    public ApiGirl getApiGirl() {
        if (sApiGirl == null) {
            synchronized (ApiManager.class) {
                if (sApiGirl == null) {
                    sApiGirl = new Retrofit.Builder()
                            .client(buildOkHttpClient())
                            .baseUrl(GIRL_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiGirl.class);
                }
            }
        }
        return sApiGirl;
    }
    @NonNull
    private OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .writeTimeout(30, TimeUnit.SECONDS);
        return clientBuilder.build();
    }
    public ApiZhihu getApiZhihu() {
        if (sApiZhihu == null) {
            synchronized (ApiManager.class) {
                if (sApiZhihu == null) {
                    sApiZhihu = new Retrofit.Builder()
                            .client(buildOkHttpClient())
                            .baseUrl(ZHIHU_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiZhihu.class);
                }
            }
        }
        return sApiZhihu;
    }
}
