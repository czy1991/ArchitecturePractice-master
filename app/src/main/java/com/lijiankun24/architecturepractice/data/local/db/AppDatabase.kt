package com.lijiankun24.architecturepractice.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import com.lijiankun24.architecturepractice.data.local.db.dao.GirlDao
import com.lijiankun24.architecturepractice.data.local.db.dao.ZhihuDao
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory


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


@Database(entities = arrayOf(Girl::class, ZhihuStory::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun girlDao(): GirlDao

    abstract fun zhihuDao(): ZhihuDao
}
