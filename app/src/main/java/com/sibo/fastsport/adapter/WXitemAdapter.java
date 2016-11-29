package com.sibo.fastsport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.domain.WXItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyuand on 2016.11.28.
 */

public class WXitemAdapter extends BaseAdapter {
    private List<WXItem> list = new ArrayList<>();
    private Context context;
    public WXitemAdapter(Context context,List<WXItem> list){
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
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.wx_items,null);
            holder.author = (TextView) convertView.findViewById(R.id.wx_author);
            holder.title = (TextView) convertView.findViewById(R.id.wx_item_title);
            holder.updataTime = (TextView) convertView.findViewById(R.id.wx_time);
            holder.img1 = (ImageView) convertView.findViewById(R.id.wx_img1);
            holder.img2 = (ImageView) convertView.findViewById(R.id.wx_img2);
            holder.img3 = (ImageView) convertView.findViewById(R.id.wx_img3);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        holder.author.setText(list.get(position).getAuthor());
        holder.updataTime.setText(list.get(position).getUpdateTime());
        //Log.e("img1",list.get(position).getTitle());
        Picasso.with(context).load(list.get(position).getImg().get(0))
                .resize(80,80)
                .centerCrop()
                .placeholder(R.mipmap.loading)
                .error(R.drawable.failed)
                .into(holder.img1);
       // Log.e("img1",list.get(position).getImg().get(0));
        Picasso.with(context).load(list.get(position).getImg().get(1))
                .resize(80,80)
                .centerCrop()
                .placeholder(R.mipmap.loading)
                .error(R.drawable.failed)
                .into(holder.img2);
       // Log.e("img1",list.get(position).getImg().get(1));
        Picasso.with(context).load(list.get(position).getImg().get(2))
                .resize(80,80)
                .centerCrop()
                .placeholder(R.mipmap.loading)
                .error(R.drawable.failed)
                .into(holder.img3);
        //Log.e("img1",list.get(position).getImg().get(2));
        return convertView;
    }

    class ViewHolder{
        TextView title;
        ImageView img1,img2,img3;
        TextView author;
        TextView updataTime;
    }
}
