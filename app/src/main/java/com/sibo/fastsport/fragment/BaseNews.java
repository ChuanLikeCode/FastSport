package com.sibo.fastsport.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sibo.fastsport.R;

/**
 * Created by Administrator on 2016/11/02.
 */
@SuppressLint("ValidFragment")
public class BaseNews extends BaseNewsFragment {
    private TextView tv;
    private View news;
    private int index;

    @SuppressLint("ValidFragment")
    public BaseNews(int index) {
        super(index);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    protected void initData() {
        switch (index) {
            case 0:
                tv.setText("第" + index + "个");
                break;
            case 1:
                tv.setText("第" + index + "个");
                break;
            case 2:
                tv.setText("第" + index + "个");
                break;
            case 3:
                tv.setText("第" + index + "个");
                break;
            case 4:
                tv.setText("第" + index + "个");
                break;
            case 5:
                tv.setText("第" + index + "个");
                break;
            case 6:
                tv.setText("第" + index + "个");
                break;
            case 7:
                tv.setText("第" + index + "个");
                break;
        }
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        news = inflater.inflate(R.layout.news_details, null);
        findById();
        return news;
    }

    private void findById() {
        tv = (TextView) news.findViewById(R.id.wenzi);
    }
}
