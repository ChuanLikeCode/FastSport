package com.sibo.fastsport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.ui.NewsActivity;
import com.sibo.fastsport.utils.XmlParseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class ChannelAndTypeGridViewAdapter extends BaseAdapter {

    private List<String> list = new ArrayList<>();
    private Context context;

    public ChannelAndTypeGridViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolders holders;
        if (convertView == null) {
            holders = new ViewHolders();
            convertView = LayoutInflater.from(context).inflate(R.layout.news_label_items, null);
            holders.channel = (TextView) convertView.findViewById(R.id.news_label_items_channel);
            convertView.setTag(holders);
        } else {
            holders = (ViewHolders) convertView.getTag();
        }
        if (position == 0 && XmlParseUtils.isPreSelectFirst) {
            XmlParseUtils.isPreSelectFirst = false;
            NewsActivity.tv_preSelected = holders.channel;
            holders.channel.setBackgroundResource(R.drawable.news_channel_selected_bg);
            holders.channel.setTextColor(context.getResources().getColor(R.color.white));
        }
        holders.channel.setText(list.get(position));
        return convertView;
    }

    class ViewHolders {
        TextView channel;
    }
}
