package com.sibo.fastsport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyFragmentAdapter;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.base.*;
import com.sibo.fastsport.fragment.MakePlanFragment;
import com.sibo.fastsport.fragment.MyHomeMenuFragment;
import com.sibo.fastsport.fragment.MyPlanFragment;
import com.sibo.fastsport.fragment.StudentFragment;
import com.sibo.fastsport.utils.MakePlanUtils;
import com.sibo.fastsport.widgets.MetaballMenu;
import com.sibo.fastsport.widgets.MetaballMenuImageView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends com.sibo.fastsport.base.BaseActivity implements MetaballMenu.MetaballMenuClickListener {

    private List<Fragment> list = new ArrayList<Fragment>();//三个主界面的Fragment的list
    private MyFragmentAdapter myFragmentAdapter;//Fragment的适配器
    private MetaballMenu menu;//底部菜单栏
    //底部菜单栏的三个控件 计划、学员、我的
    private MetaballMenuImageView menuMakePlan, menuStudent, menuMyHome;
    private MakePlanFragment makePlan;//主界面---计划
    private StudentFragment student;//主界面---学员
    private MyHomeMenuFragment myHomeMenu;//主界面--我的
    private MyPlanFragment myPlanFragment;//健身计划列表
    private ViewPager viewPager;
    //viewPager切换时需要做的事情，viewPager监听事件
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * ViewPager选择后需要做的事
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    menu.selectedView(menuMakePlan);
                    break;
                case 1:
                    menu.selectedView(menuStudent);
                    break;
                case 2:
                    menu.selectedView(menuMyHome);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null){
            if (intent.getIntExtra("finish",0)==111){

                if (makePlan !=null){

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("finish",true);
                    makePlan.setArguments(bundle);
                }//发送扫描到的数据给健身计划界面Fragment
            }else if (intent.getIntExtra("scanner",0)==888){
                Bundle bundle = intent.getExtras();
                if (bundle == null){
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS){
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //Toast.makeText(this,"扫描成功",Toast.LENGTH_SHORT).show();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("scanner",result);
                    myPlanFragment.setArguments(bundle1);

                }else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED){
                    //Toast.makeText(this,"扫描失败",Toast.LENGTH_SHORT).show();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("scanner","failed");
                    myPlanFragment.setArguments(bundle1);
                }
            }
        }
    }

    @Override
    protected void findViewByIDS() {
        menu = (MetaballMenu) findViewById(R.id.menu);
        viewPager = (ViewPager) findViewById(R.id.MainActivity_ViewPager);
        menuMakePlan = (MetaballMenuImageView) menu.findViewById(R.id.menuPlan);
        menuStudent = (MetaballMenuImageView) menu.findViewById(R.id.menuStudent);
        menuMyHome = (MetaballMenuImageView) menu.findViewById(R.id.menuMyhome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initListener();
        setStatusBarColor(R.color.title);
        MakePlanUtils.isFirst = true;
    }

    /**
     * 初始化数据源
     */
    private void initData() {
        makePlan = new MakePlanFragment();
        student = new StudentFragment();
        myHomeMenu = new MyHomeMenuFragment();
        myPlanFragment = new MyPlanFragment();
        String str = MyApplication.mUser.getType();
        if (str.equals("1")){
            list.add(makePlan);
        }else {
            list.add(myPlanFragment);
        }

        list.add(student);
        list.add(myHomeMenu);
        //设置Fragment适配器
        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(myFragmentAdapter);
        //使ViewPager默认显示第一个界面
        viewPager.setCurrentItem(0);

    }

    /**
     * 设置监听事件
     */
    private void initListener() {
        menu.setMenuClickListener(this);
        viewPager.setOnPageChangeListener(listener);
    }


    /**
     * 底部菜单栏的点击效果，跟随着切换viewPager显示界面
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuPlan:
                viewPager.setCurrentItem(0);
                break;
            case R.id.menuStudent:
                viewPager.setCurrentItem(1);
                break;
            case R.id.menuMyhome:
                viewPager.setCurrentItem(2);
                break;
        }
    }


}
