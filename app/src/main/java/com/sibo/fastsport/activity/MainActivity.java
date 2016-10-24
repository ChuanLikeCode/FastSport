package com.sibo.fastsport.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.sibo.fastsport.R;
import com.sibo.fastsport.fragment.MakePlanFragment;
import com.sibo.fastsport.fragment.MyHomeFragment;
import com.sibo.fastsport.fragment.StudentFragment;
import com.sibo.fastsport.view.BaseTranslucentActivity;
import com.sibo.fastsport.widgets.MetaballMenu;

public class MainActivity extends BaseTranslucentActivity implements MetaballMenu.MetaballMenuClickListener {
    //固定的ToolBar
//    android.support.v7.widget.Toolbar rootToolBar;
//
//    private Toolbar toolBar;

    MetaballMenu menu;
    private MakePlanFragment makePlan;
    private StudentFragment student;
    private MyHomeFragment myHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //       initBmob();
        initView();
//        initTitle();
//        initData();
        setDefaultFragment();

    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        makePlan = new MakePlanFragment();
        transaction.replace(R.id.MainActivity_FrameLayout, makePlan);
        transaction.commit();
    }



    /* private void initData() {
       final User obj = new User();
        obj.setName("豆豆");
        obj.setSex("女");
        obj.setAge("18");
        obj.setHeight("165");
        obj.setWeight("55");
        obj.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"数据添加成功"+s,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"数据添加失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.getObject("dda707bfce", new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"查询失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        obj.setName("更新的豆豆");
        obj.update("dda707bfce", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"更新成功"+obj.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"更新失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        obj.setObjectId("89b099c073");
        obj.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"删除成功"+obj.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"删除失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

  /*  private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(this, "f79d34f38040f7e7512a4228ea4d0c7a");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this);
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }*/

   /* private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.title);
        toolBar = (Toolbar) rootToolBar.findViewById(R.id.act_title_bar);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        ivBack = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        tvText = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);
        ivClose.setVisibility(View.INVISIBLE);
        tvText.setText("我的主页");
        ivBack.setVisibility(View.VISIBLE);
        setOrChangeTranslucentColor(toolBar, null, getResources().getColor(R.color.title));

    }*/

    private void initView() {
        menu = (MetaballMenu) findViewById(R.id.menu);
        menu.setMenuClickListener(this);

        /*ivHead = (ImageView) findViewById(R.id.activity_my_main_iv_touxiang);
        ivSetting = (ImageView) findViewById(R.id.activity_my_main_iv_setting);
        rlZhuYe = (RelativeLayout) findViewById(R.id.rl_zhuye);
        rlZhuYe.setOnClickListener(this);
        ivHead.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        //让图片全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
    }

    //隐藏Fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (makePlan != null) {
            transaction.hide(makePlan);
        }
        if (student != null) {
            transaction.hide(student);
        }
        if (myHome != null) {
            transaction.hide(myHome);
        }

    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (v.getId()) {
            case R.id.menuPlan:
                if (makePlan == null) {
                    makePlan = new MakePlanFragment();
                    transaction.add(R.id.MainActivity_FrameLayout, makePlan);
                } else {
                    transaction.show(makePlan);
                }
                break;
            case R.id.menuStudent:
                if (student == null) {
                    student = new StudentFragment();
                    transaction.add(R.id.MainActivity_FrameLayout, student);
                } else {
                    transaction.show(student);
                }
                break;
            case R.id.menuMyhome:
                if (myHome == null) {
                    myHome = new MyHomeFragment();
                    transaction.add(R.id.MainActivity_FrameLayout, myHome);
                } else {
                    transaction.show(myHome);
                }
                break;
        }
        transaction.commit();
    }


}
