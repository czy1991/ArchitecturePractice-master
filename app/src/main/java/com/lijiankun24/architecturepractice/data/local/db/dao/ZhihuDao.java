package com.lijiankun24.architecturepractice.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory;

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


@Dao
public interface ZhihuDao {

    @Query("SELECT * FROM zhihustorys")
    List<ZhihuStory> loadAllZhihus();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertZhihuList(List<ZhihuStory> girls);
}
