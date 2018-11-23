package com.example.lsj.mvpdemo.presenter;

import com.example.lsj.mvpdemo.base.BasePresenter;
import com.example.lsj.mvpdemo.bean.BannerBean;
import com.example.lsj.mvpdemo.contract.MultipleInterfaceContract;
import com.example.lsj.mvpdemo.interfaces.Callback;
import com.example.lsj.mvpdemo.model.IMultipleInterfaceModel;
import com.example.lsj.mvpdemo.model.MultipleInterfaceModel;
import com.example.lsj.mvpdemo.utils.LP;

/**
 * @Description: Presenter层代码
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 15:14
 */
public class MultipleInterfacePresenter extends BasePresenter<MultipleInterfaceContract.View>
        implements MultipleInterfaceContract.Presenter {

    private final IMultipleInterfaceModel multipleInterfaceModel;

    public MultipleInterfacePresenter() {
        this.multipleInterfaceModel = new MultipleInterfaceModel();
    }

    @Override
    public void getBanner() {
        multipleInterfaceModel.getBanner(new Callback<BannerBean, String>() {
            @Override
            public void onSuccess(BannerBean bannerBean) {
                //如果Model层请求数据成功,则此处应执行通知View层的代码
                //LP.w()是一个简单的log打印
                LP.w(bannerBean.toString());
                if (isViewAttached()) {
                    mView.showMultipleSuccess(bannerBean);
                }
            }

            @Override
            public void onFail(String errorMsg) {
                //如果Model层请求数据失败,则此处应执行通知View层的代码
                LP.w(errorMsg);
                if (isViewAttached()) {
                    mView.showMultipleFail(errorMsg);
                }
            }
        });
    }
}
