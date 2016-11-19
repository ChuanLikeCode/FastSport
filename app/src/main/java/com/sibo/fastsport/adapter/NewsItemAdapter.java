package com.sibo.fastsport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.domain.NewsDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class NewsItemAdapter extends BaseAdapter {

    private List<NewsDetails> list = new ArrayList<>();
    private Context context;

    public NewsItemAdapter(Context context, List<NewsDetails> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.news_items, null);
            holders.title = (TextView) convertView.findViewById(R.id.news_item_title);
            holders.content = (TextView) convertView.findViewById(R.id.news_item_text);
            holders.date = (TextView) convertView.findViewById(R.id.news_item_time);
            convertView.setTag(holders);
        } else {
            holders = (ViewHolders) convertView.getTag();
        }
//        Log.e("title",list.get(position).getTitle());
//        Log.e("content",list.get(position).getDescription());
//        Log.e("date",list.get(position).getPubDate());
        holders.title.setText(list.get(position).getTitle());
        holders.content.setText(list.get(position).getDescription());
        holders.date.setText(list.get(position).getPubDate());
        return convertView;
    }

    class ViewHolders {
        TextView title;
        TextView content;
        TextView date;
    }
}
