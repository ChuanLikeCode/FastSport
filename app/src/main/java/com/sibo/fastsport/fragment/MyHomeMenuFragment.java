package com.sibo.fastsport.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.sibo.fastsport.R;
import com.sibo.fastsport.ui.MyHomeActivity;
import com.sibo.fastsport.ui.NewsActivity;

/**
 * Created by Administrator on 2016/10/31.
 */
public class MyHomeMenuFragment extends BaseFragment implements View.OnClickListener {
    View myhomemenu;
    RelativeLayout myHome;//我的主页
    RelativeLayout news;//新闻资讯

    @Override
    protected void initData() {
        //提前获取微信的Token
       // WXArticleUtils.getAccessToken();
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        myhomemenu = inflater.inflate(R.layout.fragment_myhomemenu, null);
        findById();
        initListener();
        return myhomemenu;
    }

    private void initListener() {
        myHome.setOnClickListener(this);
        news.setOnClickListener(this);
    }

    private void findById() {
        myHome = (RelativeLayout) myhomemenu.findViewById(R.id.myhomemenu_rl_myhome);
        news = (RelativeLayout) myhomemenu.findViewById(R.id.myhomemenu_rl_news);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myhomemenu_rl_news:
                startActivity(new Intent(getActivity(), NewsActivity.class));
                break;
            case R.id.myhomemenu_rl_myhome:
                startActivity(new Intent(getActivity(), MyHomeActivity.class));
                break;
            case R.id.myhomemenu_rl_shoucang:
                break;
        }

    }
}
