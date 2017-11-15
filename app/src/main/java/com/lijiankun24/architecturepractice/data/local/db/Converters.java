package com.lijiankun24.architecturepractice.data.local.db;

import android.arch.persistence.room.TypeConverter;

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

public class Converters {
    @TypeConverter
    public static String fromTimestamp(String[] arrays) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : arrays) {
            stringBuilder.append(s).append(",");
        }
        int length = stringBuilder.toString().length();
        if (length > 0) {
            stringBuilder = stringBuilder.deleteCharAt(length - 1);
        }
        return stringBuilder.toString();
    }

    @TypeConverter
    public static String[] dateToTimestamp(String string) {
        return string.split(",");
    }
}