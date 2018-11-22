package com.example.lsj.mvpdemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lsj.mvpdemo.R;
import com.example.lsj.mvpdemo.presenter.SingleInterfacePresenter;

/**
 * @Description: 单个网络接口请求示例
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 09:36
 */
public class SingleInterfaceActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private SingleInterfacePresenter singleInterfacePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_interface);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        singleInterfacePresenter = new SingleInterfacePresenter();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleInterfacePresenter.getData(0);
            }
        });

    }
}
