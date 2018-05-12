package com.apache.android.bean;

/**
 * Created by 01370340 on 2018/5/5.
 */

public interface Favorite {

    <T> void  putFavorite(Class<T> type, T t);

    <T> T getFavorite(Class<T> type);

}
