package com.sibo.fastsport.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dalong.library.listener.OnItemClickListener;
import com.dalong.library.listener.OnItemSelectedListener;
import com.dalong.library.listener.OnLoopViewTouchListener;
import com.dalong.library.view.LoopRotarySwitchView;
import com.sibo.fastsport.EditHomePageActivity;
import com.sibo.fastsport.R;
import com.sibo.fastsport.view.BaseTranslucentActivity;


/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class MyHomeActivity extends BaseTranslucentActivity implements View.OnClickListener {

    int[] imageNum = {R.drawable.imagefirst, R.drawable.imagesecond, R.drawable.imagethree,
            R.drawable.imagefour, R.drawable.imagefive, R.drawable.imagesix,
            R.drawable.imageseven, R.drawable.imageeight};
    private ImageView editHome;
    private LoopRotarySwitchView mLoopRotarySwitchView;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhome);

        initView();
        initData();
        initLinstener();

    }

    private void initLinstener() {
        /**
         * 选中回调
         */
        mLoopRotarySwitchView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void selected(int position, View view) {
//                Toast.makeText(MainActivity.this, "setOnItemSelectedListener－－－i="+position, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 触摸回调
         */
        mLoopRotarySwitchView.setOnLoopViewTouchListener(new OnLoopViewTouchListener() {
            @Override
            public void onTouch(MotionEvent event) {
            }
        });
        /**
         * 点击事件
         */
        mLoopRotarySwitchView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int item, View view) {


            }
        });
    }

    private void initData() {
        for (int i = 0; i < imageNum.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.myhome_pictureshow, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            image.setImageResource(imageNum[i]);
            mLoopRotarySwitchView.addView(view);
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        mLoopRotarySwitchView
                .setR(width / 3)//设置R的大小
                .setAutoRotation(true)//是否自动切换
                .setAutoRotationTime(2000);//自动切换的时间  单位毫秒
    }

    private void initView() {
        mLoopRotarySwitchView = (LoopRotarySwitchView) findViewById(R.id.activity_myhome_loopView);

        editHome = (ImageView) findViewById(R.id.activity_mylhome_iv_touxiang);
        editHome.setOnClickListener(this);


        setOrChangeTranslucentColor(null, getResources().getColor(R.color.turquoise));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_mylhome_iv_touxiang:
                Intent editHomeIntent = new Intent(MyHomeActivity.this, EditHomePageActivity.class);
                startActivity(editHomeIntent);
                finish();
        }

    }
}
