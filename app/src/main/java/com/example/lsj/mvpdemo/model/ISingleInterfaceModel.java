package com.example.lsj.mvpdemo.model;

import com.example.lsj.mvpdemo.interfaces.Callback;

public interface ISingleInterfaceModel extends IModel {
    void getData(int curPage, final Callback callback);
}
