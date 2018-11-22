package com.example.lsj.mvpdemo.presenter;

import com.example.lsj.mvpdemo.base.BasePresenter;
import com.example.lsj.mvpdemo.bean.ArticleListBean;
import com.example.lsj.mvpdemo.interfaces.Callback;
import com.example.lsj.mvpdemo.model.ISingleInterfaceModel;
import com.example.lsj.mvpdemo.model.SingleInterfaceModel;
import com.example.lsj.mvpdemo.utils.LP;
import com.example.lsj.mvpdemo.view.SingleInterfaceIView;

/**
 * @Description: Presenter层代码
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 15:14
 */
public class SingleInterfacePresenter extends BasePresenter<SingleInterfaceIView> implements ISingleInterfacePresenter {
    private final ISingleInterfaceModel singleInterfaceModel;

    public SingleInterfacePresenter() {
        this.singleInterfaceModel = new SingleInterfaceModel();
    }

    @Override
    public void getData(int curPage) {
        singleInterfaceModel.getData(curPage, new Callback<ArticleListBean, String>() {
            @Override
            public void onSuccess(ArticleListBean loginResultBean) {
                //如果Model层请求数据成功,则此处应执行通知View层的代码
                //LP.w()是一个简单的log打印
                LP.w(loginResultBean.toString());
                if (isViewAttached()) {
                    mView.showArticleSuccess(loginResultBean);
                }
            }

            @Override
            public void onFail(String errorMsg) {
                //如果Model层请求数据失败,则此处应执行通知View层的代码
                LP.w(errorMsg);
                if (isViewAttached()) {
                    mView.showArticleFail(errorMsg);
                }
            }
        });
    }
}
