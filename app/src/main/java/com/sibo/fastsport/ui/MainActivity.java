package com.sibo.fastsport.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyFragmentAdapter;
import com.sibo.fastsport.fragment.MakePlanFragment;
import com.sibo.fastsport.fragment.MyHomeMenuFragment;
import com.sibo.fastsport.fragment.StudentFragment;
import com.sibo.fastsport.widgets.MetaballMenu;
import com.sibo.fastsport.widgets.MetaballMenuImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements MetaballMenu.MetaballMenuClickListener {
    //固定的ToolBar
//    android.support.v7.widget.Toolbar rootToolBar;
//
//    private Toolbar toolBar;
    private List<Fragment> list = new ArrayList<Fragment>();//三个主界面的Fragment的list
    private MyFragmentAdapter myFragmentAdapter;//Fragment的适配器
    private MetaballMenu menu;//底部菜单栏
    //底部菜单栏的三个控件 计划、学员、我的
    private MetaballMenuImageView menuMakePlan, menuStudent, menuMyHome;
    private MakePlanFragment makePlan;//主界面---计划
    private StudentFragment student;//主界面---学员
    private MyHomeMenuFragment myHomeMenu;//主界面--我的
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initBmob();
        initView();
//        initTitle();
        initData();
        initListener();
    }

    /**
     * 初始化数据源
     */
    private void initData() {
        makePlan = new MakePlanFragment();
        student = new StudentFragment();
        myHomeMenu = new MyHomeMenuFragment();
        list.add(makePlan);
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
     * 设置布局中的控件，寻找Id
     */
    private void initView() {
        menu = (MetaballMenu) findViewById(R.id.menu);
        viewPager = (ViewPager) findViewById(R.id.MainActivity_ViewPager);
        menuMakePlan = (MetaballMenuImageView) menu.findViewById(R.id.menuPlan);
        menuStudent = (MetaballMenuImageView) menu.findViewById(R.id.menuStudent);
        menuMyHome = (MetaballMenuImageView) menu.findViewById(R.id.menuMyhome);
        /*ivHead = (ImageView) findViewById(R.id.activity_my_main_iv_touxiang);
        ivSetting = (ImageView) findViewById(R.id.activity_my_main_iv_setting);
        rlZhuYe = (RelativeLayout) findViewById(R.id.rl_zhuye);
        rlZhuYe.setOnClickListener(this);
        ivHead.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        //让图片全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
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


}