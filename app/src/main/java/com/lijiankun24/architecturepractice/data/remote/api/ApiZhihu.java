package com.lijiankun24.architecturepractice.data.remote.api;


import android.database.Observable;

import com.lijiankun24.architecturepractice.data.remote.model.ZhihuData;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * 类 <code>${CLASS_NAME}</code>
 * <p>
 * 描述：
 * </p>
 * 创建日期：2017年11月15日
 *
 * @author zhaoyong.chen@ehking.com
 * @version 1.0
 */


public interface ApiZhihu {

    @GET("api/4/news/latest")
    Call<ZhihuData> getLatestNews();

    @GET("/api/4/news/before/{date}")
    Call<ZhihuData> getTheDaily(@Path("date") String date);

    @GET("api/4/news/{id}")
    Call<ZhihuStoryDetail> getZhiHuStoryDetail(@Path("id") String id);
}
