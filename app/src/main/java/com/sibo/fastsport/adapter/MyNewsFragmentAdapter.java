package com.sibo.fastsport.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sibo.fastsport.fragment.BaseNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/02.
 */
public class MyNewsFragmentAdapter extends FragmentPagerAdapter {

    List<BaseNews> list = new ArrayList<BaseNews>();

    public MyNewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyNewsFragmentAdapter(FragmentManager fm, List<BaseNews> list) {
        super(fm);
        this.list = list;
        // TODO Auto-generated constructor stub
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getIndex() + "";
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
