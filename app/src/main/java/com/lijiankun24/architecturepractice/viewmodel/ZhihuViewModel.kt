package com.lijiankun24.architecturepractice.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.lijiankun24.architecturepractice.data.DataRepository
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail

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
class ZhihuViewModel private constructor(application: Application, dataRepository: DataRepository, private val mZhihuId: String) : AndroidViewModel(application) {

    private var mDataRepository: DataRepository? = null

    val zhihuDetail: LiveData<ZhihuStoryDetail>
        get() = mDataRepository!!.getZhihuDetail(mZhihuId)

    init {
        mDataRepository = dataRepository
    }

    class Factory(private val mApplication: Application, private val mDataRepository: DataRepository, private val mGirlId: String) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ZhihuViewModel(mApplication, mDataRepository, mGirlId) as T
        }
    }
}
