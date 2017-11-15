package com.lijiankun24.architecturepractice;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.utils.Consts;

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

public class MyApplication extends Application {

    private static MyApplication INSTANCE = null;

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        AppDatabaseManager.getInstance().createDB(this);
        if (Consts.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
