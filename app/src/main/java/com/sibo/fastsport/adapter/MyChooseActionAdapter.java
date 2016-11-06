package com.sibo.fastsport.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sibo.fastsport.R;
import com.sibo.fastsport.domain.SportName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 */
public class MyChooseActionAdapter extends BaseAdapter {
    int levelIds[] = {R.id.choose_item_iv_level1, R.id.choose_item_iv_level2,
            R.id.choose_item_iv_level3, R.id.choose_item_iv_level4, R.id.choose_item_iv_level5};
    List<SportName> list = new ArrayList<SportName>();
    Context context;

    public MyChooseActionAdapter(Context context, List<SportName> list) {
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
        ChooseViewHolder holder = null;
        if (convertView == null) {
            holder = new ChooseViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.choose_action_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.choose_item_img);
            holder.name = (TextView) convertView.findViewById(R.id.choose_item_name);
            for (int i = 0; i < levelIds.length; i++) {
                holder.level[i] = (ImageView) convertView.findViewById(levelIds[i]);
            }
            convertView.setTag(holder);
        } else {
            holder = (ChooseViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon().getFileUrl())
                .asGif().diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.loading)
                .centerCrop()
                .error(R.drawable.failed).into(holder.img);
        Log.e(list.get(position).getName() + "level---", list.get(position).getLevel() + "");
        for (int i = 0; i < list.get(position).getLevel(); i++) {
            holder.level[i].setVisibility(View.VISIBLE);
        }
        //Log.e("url",list.get(position).getIcon().getFileUrl());
        return convertView;
    }

    class ChooseViewHolder {
        ImageView img;
        TextView name;
        ImageView[] level = new ImageView[5];
    }
}
