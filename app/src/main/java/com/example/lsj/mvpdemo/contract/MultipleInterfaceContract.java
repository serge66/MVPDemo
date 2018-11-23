package com.example.lsj.mvpdemo.contract;

import com.example.lsj.mvpdemo.bean.BannerBean;
import com.example.lsj.mvpdemo.view.IView;

public interface MultipleInterfaceContract {


    interface View extends IView {
        void showMultipleSuccess(BannerBean bean);

        void showMultipleFail(String errorMsg);
    }

    interface Presenter {
        void getBanner();
    }


}
