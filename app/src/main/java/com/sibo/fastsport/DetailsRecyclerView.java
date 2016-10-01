package com.sibo.fastsport;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class DetailsRecyclerView extends RecyclerView.Adapter<DetailsRecyclerView.ViewHolder>{

    public int[] pic = {R.drawable.imagefirst, R.drawable.imagesecond,
            R.drawable.imagethree, R.drawable.imagefour,
            R.drawable.imagefive, R.drawable.imagesix,
            R.drawable.imageseven, R.drawable.imageeight};
    public List<Integer> pics = new ArrayList<Integer>();
    private LayoutInflater mInflater;
    public DetailsRecyclerView(Context context){
        this.mInflater = LayoutInflater.from(context);
        add();
    }

    private void add() {
        for (int i = 0;i < pic.length; i++){
            pics.add(pic[i]);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.xiangqing_item_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.picture.setImageResource(pics.get(position));
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView picture;
        public ViewHolder(View view){
            super(view);
            picture = (ImageView)view.findViewById(R.id.item_recycler_iv_xiangqing);
        }
    }
}