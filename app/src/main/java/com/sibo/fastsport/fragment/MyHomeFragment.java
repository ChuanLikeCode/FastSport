package com.sibo.fastsport.fragment;

import android.content.Context;
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
import com.sibo.fastsport.R;
import com.sibo.fastsport.view.DragScaleImageView;


/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class MyHomeFragment extends BaseFragment {

    private int[] imageNum = {R.drawable.imagefirst, R.drawable.imagesecond, R.drawable.imagethree,
            R.drawable.imagefour, R.drawable.imagefive, R.drawable.imagesix,
            R.drawable.imageseven};
    private DragScaleImageView mDragScaleImageView;
    private int preHeight;
    private View MyHomeFragment;
    private ImageView editHome;
    private LoopRotarySwitchView mLoopRotarySwitchView;
    private int width;

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    protected View initView(LayoutInflater inflater) {
        MyHomeFragment = inflater.inflate(R.layout.fragment_myhome, null);
        initView();
        initData();
        initLinstener();
        return MyHomeFragment;
    }


    /**
     * 图片旋转的
     */
    private void initLinstener() {
        /**
         * 选中回调
         */
        mLoopRotarySwitchView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void selected(int position, View view) {

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

    @Override
    protected void initData() {
        for (int i = 0; i < imageNum.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.myhome_pictureshow, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            image.setImageResource(imageNum[i]);
            mLoopRotarySwitchView.addView(view);
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        mLoopRotarySwitchView
                .setR(width / 3)//设置R的大小
                .setAutoRotation(true)//是否自动切换
                .setAutoRotationTime(2000);//自动切换的时间  单位毫秒
    }


    private void initView() {
        //从View中寻找控件

        mLoopRotarySwitchView = (LoopRotarySwitchView) MyHomeFragment.findViewById(R.id.activity_myhome_loopView);
        mDragScaleImageView = (DragScaleImageView) MyHomeFragment.findViewById(R.id.rl_head);
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        preHeight = dip2px(getActivity(), 223);
        mDragScaleImageView.setImageWidthAndHeight(width, preHeight);
        //setOrChangeTranslucentColor(null, getResources().getColor(R.color.black));
    }

}
