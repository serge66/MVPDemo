package com.example.lsj.mvpdemo.model;

import com.example.lsj.mvpdemo.api.Api;
import com.example.lsj.mvpdemo.bean.BannerBean;
import com.example.lsj.mvpdemo.interfaces.Callback;
import com.example.lsj.mvpdemo.utils.LP;
import com.example.lsj.mvpdemo.utils.NetUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MultipleInterfaceModel implements IMultipleInterfaceModel {

    @Override
    public void getBanner(final Callback callback) {
        NetUtils.getRetrofit()
                .create(Api.class)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BannerBean>() {
                    @Override
                    public void onCompleted() {
                        LP.w("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail("出现错误");
                    }

                    @Override
                    public void onNext(BannerBean bean) {
                        if (null == bean) {
                            callback.onFail("出现错误");
                        } else if (bean.errorCode != 0) {
                            callback.onFail(bean.errorMsg);
                        } else {
                            callback.onSuccess(bean);
                        }
                    }
                });
    }
}
