package com.example.lsj.mvpdemo.model;

import com.example.lsj.mvpdemo.interfaces.Callback;

public interface IMultipleInterfaceModel extends IModel {
    void getBanner(final Callback callback);
}
