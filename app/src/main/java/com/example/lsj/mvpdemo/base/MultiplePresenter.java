package com.example.lsj.mvpdemo.base;

import com.example.lsj.mvpdemo.presenter.IPresenter;
import com.example.lsj.mvpdemo.view.IView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 单页面多网络请求时 presenter容器
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 21:14
 */
public class MultiplePresenter<T extends IView> extends BasePresenter<T> {
    private T mView;

    private List<IPresenter> presenters = new ArrayList<>();

    @SafeVarargs
    public final <K extends IPresenter<T>> void addPresenter(K... addPresenter) {
        for (K ap : addPresenter) {
            ap.attachView(mView);
            presenters.add(ap);
        }
    }

    public MultiplePresenter(T mView) {
        this.mView = mView;
    }

    @Override
    public void detachView() {
        for (IPresenter presenter : presenters) {
            presenter.detachView();
        }
        mView = null;
    }

}
