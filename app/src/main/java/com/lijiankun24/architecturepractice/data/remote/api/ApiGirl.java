package com.lijiankun24.architecturepractice.data.remote.api;

import com.lijiankun24.architecturepractice.data.remote.model.GirlData;

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


public interface ApiGirl {

    @GET("api/data/福利/10/{index}")
    Call<GirlData> getGirlsData(@Path("index") int index);
}
