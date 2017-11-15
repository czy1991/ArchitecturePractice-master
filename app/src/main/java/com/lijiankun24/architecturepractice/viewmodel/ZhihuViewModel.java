package com.lijiankun24.architecturepractice.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.DataRepository;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail;

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
public class ZhihuViewModel extends AndroidViewModel {

    private DataRepository mDataRepository = null;

    private final String mZhihuId;

    private ZhihuViewModel(Application application, DataRepository dataRepository, String zhihuId) {
        super(application);
        this.mZhihuId = zhihuId;
        mDataRepository = dataRepository;
    }

    public LiveData<ZhihuStoryDetail> getZhihuDetail(){
        return mDataRepository.getZhihuDetail(mZhihuId);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final DataRepository mDataRepository;

        private final String mGirlId;

        public Factory(@NonNull Application application,DataRepository dataRepository, String girlId) {
            mDataRepository = dataRepository;
            mApplication = application;
            mGirlId = girlId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ZhihuViewModel(mApplication, mDataRepository, mGirlId);
        }
    }
}
