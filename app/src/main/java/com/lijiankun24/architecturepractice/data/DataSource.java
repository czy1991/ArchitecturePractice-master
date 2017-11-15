package com.lijiankun24.architecturepractice.data;

import android.arch.lifecycle.LiveData;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail;

import java.util.List;

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

public interface DataSource {

    /**
     * Girl 相关方法
     */
    LiveData<List<Girl>> getGirlList(int index);

    LiveData<Boolean> isLoadingGirlList();


    /**
     * Zhihu 相关方法
     */
    LiveData<List<ZhihuStory>> getLastZhihuList();

    LiveData<List<ZhihuStory>> getMoreZhihuList(String date);

    LiveData<ZhihuStoryDetail> getZhihuDetail(String id);

    LiveData<Boolean> isLoadingZhihuList();
}
