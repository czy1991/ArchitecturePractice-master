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
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl
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
class GirlListViewModel private constructor(application: Application, girlsDataRepository: DataRepository) : AndroidViewModel(application) {

    private val mGirlPageIndex = MutableLiveData<Int>()

    val gilrsLiveData: LiveData<List<Girl>>

    private var mGirlsDataRepository: DataRepository? = null

    val loadMoreState: LiveData<Boolean>
        get() = mGirlsDataRepository!!.isLoadingGirlList

    init {
        mGirlsDataRepository = girlsDataRepository

        gilrsLiveData = Transformations.switchMap(mGirlPageIndex) { input -> mGirlsDataRepository!!.getGirlList(input!!) }
    }

    fun refreshGrilsData() {
        mGirlPageIndex.value = 1
    }

    fun loadNextPageGirls() {
        if (!Util.isNetworkConnected(MyApplication.instance)) {
            return
        }
        mGirlPageIndex.value = if (mGirlPageIndex.value == null) 1 else mGirlPageIndex.value!! + 1
    }

    class Factory(private val mApplication: Application, private val mGirlsDataRepository: DataRepository) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GirlListViewModel(mApplication, mGirlsDataRepository) as T
        }
    }
}
