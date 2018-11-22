package com.example.lsj.mvpdemo.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsj.mvpdemo.R;
import com.example.lsj.mvpdemo.base.BaseMVPActivity;
import com.example.lsj.mvpdemo.bean.ArticleListBean;
import com.example.lsj.mvpdemo.presenter.SingleInterfacePresenter;

/**
 * @Description: 单个网络接口请求示例
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 09:36
 */
public class SingleInterfaceActivity extends BaseMVPActivity<SingleInterfacePresenter> implements SingleInterfaceIView {

    private Button button;
    private TextView textView;

    @Override
    protected void init() {
        setContentView(R.layout.activity_single_interface);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getData(0);
            }
        });
    }

    @Override
    protected SingleInterfacePresenter createPresenter() {
        return new SingleInterfacePresenter();
    }


    @Override
    public void showArticleSuccess(ArticleListBean bean) {
        textView.setText(bean.data.datas.get(0).title);
    }

    @Override
    public void showArticleFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
