package com.example.lsj.mvpdemo.view;

import com.example.lsj.mvpdemo.bean.ArticleListBean;


public interface SingleInterfaceIView extends IView {
    void showArticleSuccess(ArticleListBean bean);

    void showArticleFail(String errorMsg);
}
