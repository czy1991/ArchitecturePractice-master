package com.lijiankun24.architecturepractice.data;

import android.app.Application;

import com.lijiankun24.architecturepractice.data.local.LocalDataSource;
import com.lijiankun24.architecturepractice.data.remote.RemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

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
public class Injection {

    public static DataRepository getDataRepository(Application application) {
        return DataRepository.getInstance(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(), application);
    }
}
