package com.sibo.fastsport.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sibo.fastsport.R;
import com.sibo.fastsport.ui.MyHomeActivity;
import com.sibo.fastsport.ui.NewsActivity;
import com.sibo.fastsport.ui.SettingActivity;
import com.sibo.fastsport.ui.WxCollectedActivity;

/**
 * Created by Administrator on 2016/10/31.
 */
public class MyHomeMenuFragment extends BaseFragment implements View.OnClickListener {
    private View myhomemenu;
    private RelativeLayout myHome;//我的主页
    private RelativeLayout news;//新闻资讯
    private RelativeLayout collection;//我的收藏
    private ImageView setting;
    @Override
    protected void initData() {
        //提前获取微信的Token
       // WXArticleUtils.getAccessToken();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        myhomemenu = inflater.inflate(R.layout.fragment_myhomemenu, container, false);
        findById();
        initListener();
        return myhomemenu;
    }

    private void initListener() {
        myHome.setOnClickListener(this);
        news.setOnClickListener(this);
        collection.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    private void findById() {
        setting = (ImageView) myhomemenu.findViewById(R.id.myhomemenu_iv_setting);
        myHome = (RelativeLayout) myhomemenu.findViewById(R.id.myhomemenu_rl_myhome);
        news = (RelativeLayout) myhomemenu.findViewById(R.id.myhomemenu_rl_news);
        collection = (RelativeLayout) myhomemenu.findViewById(R.id.myhomemenu_rl_shoucang);
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
                startActivity(new Intent(getActivity(), WxCollectedActivity.class));
                break;
            case R.id.myhomemenu_iv_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }

    }
}
