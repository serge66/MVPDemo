package com.example.lsj.mvpdemo.interfaces;

/**
 * @Description: 所有model中的回调
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 14:53
 */
public interface Callback<K, V> {
    void onSuccess(K data);

    void onFail(V data);
}
