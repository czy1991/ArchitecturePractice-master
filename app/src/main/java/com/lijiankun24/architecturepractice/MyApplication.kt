package com.lijiankun24.architecturepractice

import android.app.Application

import com.facebook.stetho.Stetho
import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager
import com.lijiankun24.architecturepractice.utils.Consts

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

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDatabaseManager.getInstance().createDB(this)
        if (Consts.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {

        lateinit var  instance: MyApplication
    }
}
