package com.lijiankun24.architecturepractice.viewmodel

import android.app.Application
import android.arch.core.util.Function
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.lijiankun24.architecturepractice.MyApplication
import com.lijiankun24.architecturepractice.data.DataRepository
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory
import com.lijiankun24.architecturepractice.utils.Util

/**
 * 类 `${CLASS_NAME}`
 *
 *
 * 描述：
 *
 * 创建日期：2017年11月15日
 *
 * @author zhaoyong.chen@ehking.com
 * @version 1.0
 */
class ZhihuListViewModel private constructor(application: Application, dataRepository: DataRepository) : AndroidViewModel(application) {

    // 请求接口中查询的日期参数
    private val mZhihuPageDate = MutableLiveData<String>()

    // Zhihu 列表的数据
    /**
     * 获取 Zhihu 列表数据
     *
     * @return Zhihu 列表数据
     */
    val zhihuList: LiveData<List<ZhihuStory>>

    // 数据源
    private var mDataRepository: DataRepository? = null

    /**
     * 数据请求状态由 DataRepository 控制，包括下拉刷新和上拉加载更多
     *
     * @return 是否在进行数据请求
     */
    val isLoadingZhihuList: LiveData<Boolean>
        get() = mDataRepository!!.isLoadingZhihuList

    init {
        mDataRepository = dataRepository
        // 使用 Transformations.switchMap() 方法，当 View 改变 mZhihuPageDate 参数的值时，则进行 zhihu 列表数据的请求
        zhihuList = Transformations.switchMap(mZhihuPageDate) { input -> mDataRepository!!.getZhihuList(input) }
    }

    /**
     * 下拉刷新，获取最新的 Zhihu 列表数据
     */
    fun refreshZhihusData() {
        mZhihuPageDate.value = "today"
    }

    /**
     * 上拉加载更多时，获取 Zhihu 历史列表数据
     *
     * @param positon 表示列表滑动到最后一项
     */
    fun loadNextPageZhihu(positon: Int) {
        if (!Util.isNetworkConnected(MyApplication.instance)) {
            return
        }
        mZhihuPageDate.value = positon.toString()
    }

    class Factory(private val mApplication: Application, private val mGirlsDataRepository: DataRepository) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ZhihuListViewModel(mApplication, mGirlsDataRepository) as T
        }
    }
}
