package com.example.lsj.mvpdemo.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsj.mvpdemo.R;
import com.example.lsj.mvpdemo.base.BaseMVPActivity;
import com.example.lsj.mvpdemo.base.MultiplePresenter;
import com.example.lsj.mvpdemo.bean.ArticleListBean;
import com.example.lsj.mvpdemo.bean.BannerBean;
import com.example.lsj.mvpdemo.contract.MultipleInterfaceContract;
import com.example.lsj.mvpdemo.contract.SingleInterfaceContract;
import com.example.lsj.mvpdemo.presenter.MultipleInterfacePresenter;
import com.example.lsj.mvpdemo.presenter.SingleInterfacePresenter;

/**
 * @Description: 单页面多网络请求
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 21:02
 */
public class MultipleInterfaceActivity extends BaseMVPActivity<MultiplePresenter>
        implements SingleInterfaceContract.View, MultipleInterfaceContract.View {

    private Button button;
    private TextView textView;
    private Button btn;
    private TextView tv;
    private SingleInterfacePresenter singleInterfacePresenter;
    private MultipleInterfacePresenter multipleInterfacePresenter;


    @Override
    protected void init() {
        setContentView(R.layout.activity_multiple_interface);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleInterfacePresenter.getData(0);
            }
        });


        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleInterfacePresenter.getBanner();
            }
        });
    }

    @Override
    protected MultiplePresenter createPresenter() {
        MultiplePresenter multiplePresenter = new MultiplePresenter(this);

        singleInterfacePresenter = new SingleInterfacePresenter();
        multipleInterfacePresenter = new MultipleInterfacePresenter();

        multiplePresenter.addPresenter(singleInterfacePresenter);
        multiplePresenter.addPresenter(multipleInterfacePresenter);
        return multiplePresenter;
    }

    @Override
    public void showArticleSuccess(ArticleListBean bean) {
        textView.setText(bean.data.datas.get(0).title);
    }

    @Override
    public void showArticleFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMultipleSuccess(BannerBean bean) {
        tv.setText(bean.data.get(0).title);
    }

    @Override
    public void showMultipleFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
