package com.sibo.fastsport.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sibo.fastsport.adapter.MakePlanAdapter;
import com.sibo.fastsport.domain.SportName;
import com.sibo.fastsport.fragment.BaseDay;
import com.sibo.fastsport.ui.ChooseActionActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * MakePlan界面的工具类
 * Created by Administrator on 2016/11/12.
 */
public class MakePlanUtils {
    public static int dayId;//设置当前是第几天
    public static boolean isFirst = true;//是否第一次执行这个界面
    public static Context context;
    public static List<SportName> list = new ArrayList<>();
    /**
     * 添加健身动作的监听事件
     */
    public static View.OnClickListener mainActionAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChooseActionActivity.class);
            context.startActivity(intent);
        }
    };
    /**
     * 添加健身动作的监听事件
     */
    public static View.OnClickListener relaxActionAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChooseActionActivity.class);

            context.startActivity(intent);
        }
    };
    /**
     * 添加健身动作的监听事件
     */
    public static View.OnClickListener stretchingAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChooseActionActivity.class);

            context.startActivity(intent);
        }
    };
    /**
     * 添加健身动作的监听事件
     */
    public static View.OnClickListener warmUpAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChooseActionActivity.class);

            context.startActivity(intent);
        }
    };
    private static List<BaseDay> list_day = new ArrayList<>();

    public MakePlanUtils(Context context, List<BaseDay> list_day) {
        MakePlanUtils.context = context;
        MakePlanUtils.list_day = list_day;
    }

    /**
     * 设置返回的健身动作，显示在listView上面
     */
    public void getResult() {
        Log.e("listSize", list.size() + "");
        MakePlanAdapter adapter = new MakePlanAdapter(list_day.get(dayId).getActivity(), list);
        list_day.get(dayId).warmUpListView.setAdapter(adapter);
        setListViewHeight(list_day.get(dayId).warmUpListView);
        list_day.get(dayId).warmUpListView.setVisibility(View.VISIBLE);
    }

    /**
     * 由于ScrollView中嵌套ListView显示的时候，高度只显示一行数据的高度
     * 所以应该计算ListView的原本高度，使用getCount()*item.getMeasuredHeight()
     * 计算高度，重新设置ListView的高度
     *
     * @param listView
     */
    public void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        MakePlanAdapter adapter = (MakePlanAdapter) listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        int len = adapter.getCount();
        View item = adapter.getView(0, null, listView);
        item.measure(0, 0);
        totalHeight = item.getMeasuredHeight() * len;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }
}
