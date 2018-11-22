package com.example.lsj.mvpdemo.model;

import com.example.lsj.mvpdemo.api.Api;
import com.example.lsj.mvpdemo.bean.ArticleListBean;
import com.example.lsj.mvpdemo.interfaces.Callback;
import com.example.lsj.mvpdemo.utils.LP;
import com.example.lsj.mvpdemo.utils.NetUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SingleInterfaceModel implements ISingleInterfaceModel {

    @Override
    public void getData(int curPage, final Callback callback) {
        NetUtils.getRetrofit()
                .create(Api.class)
                .getData(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArticleListBean>() {
                    @Override
                    public void onCompleted() {
                        LP.w("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail("出现错误");
                    }

                    @Override
                    public void onNext(ArticleListBean bean) {
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
