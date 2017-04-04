package com.sibo.fastsport.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.ImgAdapter;
import com.sibo.fastsport.adapter.LabelAdapter;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.base.BaseActivity;
import com.sibo.fastsport.listener.OnItemClickListener;
import com.sibo.fastsport.utils.ImageLoaderUtils;
import com.sibo.fastsport.utils.ImageUploadUtil;
import com.sibo.fastsport.utils.ImageUtil;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.utils.ToastUtils;
import com.sibo.fastsport.view.ActionSheet;
import com.sibo.fastsport.view.CircleImageView;
import com.sibo.fastsport.view.MyAlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class EditHomePageActivity extends BaseActivity implements View.OnClickListener, ActionSheet.OnSheetItemClickListener {
    //拍照、从相册选择、照片保存成功
    private final int TAKE_PHOTO = 1, CHOOSE_PHOTO = 2, SAVE_IMAGE_SUCCESS = 3;
    public List<String> labelList = new ArrayList<>();
    public List<BmobFile> ImgList = new ArrayList<>();
    public BmobFile headUri = new BmobFile();
    public boolean headOrImg = false;//标记上传头像还是照片  true 头像 false 照片
    private RecyclerView labelRecyclerView;
    private LabelAdapter labelAdapter;
    private RecyclerView imgRecyclerView;
    private ImgAdapter imgAdapter;
    private CircleImageView head;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.UPLOAD_SUCCESS:
                    if (headOrImg) {
                        loginuser.setHead(headUri);
                        MyApplication.getInstance().saveUserInfo(loginuser);
                        ImageLoaderUtils.initImage(EditHomePageActivity.this, headUri.getFileUrl(), head, R.mipmap.loading);

                    } else {
                        imgAdapter.notifyDataSetChanged();
                    }
                    dialog.dismiss();
                    break;
                case Constant.FAILED:
                    ToastUtils.shortToast(EditHomePageActivity.this, "上传失败");
                    break;
                case 123:
                    dialog.dismiss();
                    MyApplication.getInstance().saveUserInfo(loginuser);
                    ToastUtils.shortToast(EditHomePageActivity.this, "保存成功");
                    startActivity(MyHomeActivity.class);
                    finish();
                    break;
            }
        }
    };
    //    private LinearLayout ll_age, ll_height, ll_weight, ll_jiaoling;
//    private List<Pickers> agePickers, heightPickers, weightPickers, jiaolingPickers;
    private TextView name, age, height, weight, jiaoling, phone;
    private ImageView sex, Back;
    private MyBombUtils bombUtils;
    private int select = 0;
    private ImageView[] level = new ImageView[5];
    private int[] levelIds = {R.id.x1, R.id.x2, R.id.x3, R.id.x4, R.id.x5};
    private String[] items = {"拍照", "我的相册"};
    // 头像文件、上传头像的名称、本地图片uri
    private File imageFile;
    private Uri imageUri; // 图片路径

    @Override
    protected void findViewByIDS() {
        labelRecyclerView = (RecyclerView) findViewById(R.id.label_recycler);
        imgRecyclerView = findViewsById(R.id.img_recycler);
        head = findViewsById(R.id.activity_editzhuye_iv_touxiang);
//        ll_age = findViewsById(R.id.age_ll);
//        ll_height = findViewsById(R.id.height_ll);
//        ll_weight = findViewsById(R.id.weight_ll);
//        ll_jiaoling = findViewsById(R.id.jiaoling_ll);
        age = findViewsById(R.id.age);
        name = findViewsById(R.id.activity_editzhuye_tv_name);
        height = findViewsById(R.id.height);
        weight = findViewsById(R.id.weight);
        jiaoling = findViewsById(R.id.jiaoling);
        phone = findViewsById(R.id.activity_editzhuye_tv_phonenum);
        sex = findViewsById(R.id.activity_editzhuye_iv_man);
        Back = findViewsById(R.id.activity_editzhuye_iv_back);
        for (int i = 0; i < levelIds.length; i++) {
            level[i] = findViewsById(levelIds[i]);
        }
    }

    private void setTransparentBar() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentBar();
        setContentView(R.layout.activity_editzhuye);
        initData();
        bind();
//        Log.e("onCreate", "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageLoaderUtils.initImage(this, loginuser.getHead().getFileUrl(), head, R.mipmap.logo);
        name.setText(loginuser.getNikeName());
        phone.setText(loginuser.getAccount());
        age.setText(loginuser.getAge());
        height.setText(loginuser.getHeight());
        weight.setText(loginuser.getWeight());
        jiaoling.setText(loginuser.getJiaoling());
        for (int i = 0; i < loginuser.getLevel(); i++) {
            level[i].setImageResource(R.mipmap.quanxing);
        }
        if (loginuser.getSex().equals("女")) {
            sex.setImageResource(R.mipmap.girl);
        } else {
            sex.setImageResource(R.mipmap.man);
        }
//        Log.e("onResume", loginuser.getGoodAt() + "");
//        Log.e("onResume", loginuser.getImg() + "");
        if (loginuser.getGoodAt() != null && loginuser.getGoodAt().size() != 0) {
            labelList.clear();
            labelList.addAll(loginuser.getGoodAt());
            labelList.add("添加");
            labelAdapter.setList(labelList);
//            labelAdapter.notifyDataSetChanged();
        } else {
            labelList.clear();
            labelList.add("添加");
            labelAdapter.setList(labelList);
        }
        if (loginuser.getImg() != null && loginuser.getImg().size() != 0) {
            ImgList.clear();
            ImgList.addAll(loginuser.getImg());
            File file = new File("http://bmob-cdn-6840.b0.upaiyun.com/2017/04/03/e5c07322d731405c9589f1ed80204d26.png");
            BmobFile bmobFile = new BmobFile(file);
            ImgList.add(bmobFile);
            imgAdapter.setList(ImgList);
//            imgAdapter.notifyDataSetChanged();
        } else {
            ImgList.clear();
            File file = new File("http://bmob-cdn-6840.b0.upaiyun.com/2017/04/03/e5c07322d731405c9589f1ed80204d26.png");
            BmobFile bmobFile = new BmobFile(file);
            ImgList.add(bmobFile);
            imgAdapter.setList(ImgList);
        }
    }

    private void bind() {
//        head.setOnClickListener(this);
//        name.setOnClickListener(this);
//        ll_age.setOnClickListener(this);
//        ll_height.setOnClickListener(this);
//        ll_weight.setOnClickListener(this);
//        ll_jiaoling.setOnClickListener(this);
//        pickers.setOnSelectListener(this);
        Back.setOnClickListener(this);
    }

    /**
     * 初始化Pickers
     */
//    public void initPickers() {
//        agePickers = new ArrayList<>();
//        heightPickers = new ArrayList<>();
//        weightPickers = new ArrayList<>();
//        jiaolingPickers = new ArrayList<>();
//        for (int i = 0; i < 121; i++) {
//            String j = i + 30 + "KG";
//            Pickers pickers = new Pickers();
//            pickers.setShowId(i + "");
//            pickers.setShowConetnt(j);
//            weightPickers.add(pickers);
//        }
//        for (int i = 0; i < 71; i++) {
//            String j = i + 150 + "CM";
//            Pickers pickers = new Pickers();
//            pickers.setShowId(i + "");
//            pickers.setShowConetnt(j);
//            heightPickers.add(pickers);
//        }
//        for (int i = 0; i < 101; i++) {
//            String j = i + "岁";
//            Pickers pickers = new Pickers();
//            pickers.setShowId(i + "");
//            pickers.setShowConetnt(j);
//            agePickers.add(pickers);
//        }
//        for (int i = 0; i < 101; i++) {
//            String j = i + "年";
//            Pickers pickers = new Pickers();
//            pickers.setShowId(i + "");
//            pickers.setShowConetnt(j);
//            jiaolingPickers.add(pickers);
//        }
//    }

    private void initData() {
        //initPickers();
        bombUtils = new MyBombUtils(this);
        labelAdapter = new LabelAdapter(this, labelList);
        labelRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        labelRecyclerView.setItemAnimator(new DefaultItemAnimator());
        labelRecyclerView.setAdapter(labelAdapter);
        labelAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == labelList.size() - 1) {
                    addGoodAt();
                } else {
                    deleteGoodAt(position);
                }
            }
        });

        imgAdapter = new ImgAdapter(this, ImgList);
        imgRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imgRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imgRecyclerView.setAdapter(imgAdapter);
        imgAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == ImgList.size() - 1) {
                    addImg();
                } else {
                    deleteImg(position);
                }
            }


        });
    }

    /**
     * 删除照片
     *
     * @param position
     */
    private void deleteImg(final int position) {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.builder().setTitle("删除此照片")
                .setMsg("确定删除？")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImgList.remove(position);
                        imgAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

    /**
     * 添加照片
     */
    private void addImg() {
        headOrImg = false;
        showActionSheetDialog(items);
    }

    /**
     * 删除擅长领域
     *
     * @param position
     */
    public void deleteGoodAt(final int position) {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.builder().setTitle("删除此标签")
                .setMsg("确定删除？")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        labelList.remove(position);
                        labelAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

    /**
     * 添加擅长领域
     */
    public void addGoodAt() {
        final EditText editText = new EditText(EditHomePageActivity.this);
        MyAlertDialog dialog = new MyAlertDialog(EditHomePageActivity.this);
        dialog.builder().setTitle("请输入擅长的领域")
                .setView(editText)
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText.getText().toString().equals("")) {
                            labelList.add(0, editText.getText().toString());
                            labelAdapter.setList(labelList);
//                            Log.e("labelList", labelList + "");
                        } else {
                            ToastUtils.shortToast(EditHomePageActivity.this, "内容不能为空");
                        }
                    }
                }).show();
    }

    //头像选择对话框
    private void showActionSheetDialog(String[] items) {
        ActionSheet actionSheet = new ActionSheet(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true);
        for (int i = 0; i < items.length; i++) {
            actionSheet.addSheetItem(items[i], ActionSheet.SheetItemColor.Blue, EditHomePageActivity.this);
        }
        actionSheet.show();

    }

    @Override
    public void onClick(int which) {
        switch (which) {
            case TAKE_PHOTO:
                //拍照
                imageFile = ImageUploadUtil.createImageFile(ImageUploadUtil.CAMERA_SAVEDIR2, ImageUploadUtil.createImageName());
                startActivityForResult(ImageUploadUtil.intentImageCapture(imageFile), ImageUploadUtil.TAKE_PHOTO);
                break;
            case CHOOSE_PHOTO:
                //从相册选择
                startActivityForResult(ImageUtil.intentChooseImg(),
                        CHOOSE_PHOTO);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == 0) {
                    return;
                }
                //拍照
                ImageUtil.paizhaocreateImagefile(this, imageFile);
                if (imageFile == null) {
                    ToastUtils.shortToast(this, "图片太大无法上传");
                    return;
                }
                doSendThread();//上传图片
                break;
            case CHOOSE_PHOTO:
                //相册
                if (resultCode == 0) return;
                imageUri = data.getData();
                imageFile = MyApplication.getInstance().createimagefile(imageUri, getVmWidth(), getVmHeight());
                if (imageFile == null) {
                    ToastUtils.shortToast(this, "imageFile为空2");
                    return;
                }
                doSendThread();//上传图片
                break;
        }

    }

    /**
     * 上传头像
     */
    private void doSendThread() {
        dialog = ProgressDialog.show(this, null, "正在上传图片...");
        BmobFile bmobFile = new BmobFile(imageFile);
        bombUtils.upLoadImg(bmobFile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.activity_editzhuye_iv_touxiang://头像
//                headOrImg = true;
//                showActionSheetDialog(items);
//                break;
//            case R.id.activity_editzhuye_tv_name://昵称
//                showDialogName();
//                break;
//            case R.id.age_ll:
//                select = 2;
//                showDialog(agePickers);
//                break;
//            case R.id.weight_ll:
//                select = 4;
//                showDialog(weightPickers);
//                break;
//            case R.id.height_ll:
//                select = 3;
//                showDialog(heightPickers);
//                break;
//            case R.id.jiaoling_ll:
//                select = 5;
//                showDialog(jiaolingPickers);
//                break;
            case R.id.activity_editzhuye_iv_back:
                back();
                break;
        }
    }

    /**
     * //昵称
     */
//    private void showDialogName() {
//        final EditText editText = new EditText(EditHomePageActivity.this);
//        MyAlertDialog dialog = new MyAlertDialog(EditHomePageActivity.this);
//        dialog.builder().setTitle("请输入昵称")
//                .setView(editText)
//                .setNegativeButton("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//                .setPositiveButton("确定", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!editText.getText().toString().equals("")) {
//                            name.setText(editText.getText().toString());
//                            loginuser.setNikeName(editText.getText().toString());
//                            MyApplication.getInstance().saveUserInfo(loginuser);
//                        } else {
//                            ToastUtils.shortToast(EditHomePageActivity.this, "内容不能为空");
//                        }
//                    }
//                }).show();
//    }
//
//    @Override
//    public void onSelect(Pickers pickers) {
//        switch (select) {
//            case 2:
//                age.setText(pickers.getShowConetnt());
//                break;
//            case 3:
//                height.setText(pickers.getShowConetnt());
//                break;
//            case 4:
//                weight.setText(pickers.getShowConetnt());
//                break;
//            case 5:
//                jiaoling.setText(pickers.getShowConetnt());
//                break;
//        }
//    }
// 捕获返回键的方法1
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        && event.getRepeatCount() == 0
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 按下BACK，同时没有重复
            back();
        }

        return super.onKeyDown(keyCode, event);
    }
    private void back() {
        MyAlertDialog dialog = new MyAlertDialog(EditHomePageActivity.this);
        dialog.builder().setTitle("退出")
                .setMsg("确定退出?")
                .setNegativeButton("仅退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(MyHomeActivity.class);
                        EditHomePageActivity.this.finish();
                    }
                })
                .setPositiveButton("保存并退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveChange();
                    }
                }).show();
    }

    private void saveChange() {
        dialog = ProgressDialog.show(this, null, "正在保存...");
//        loginuser.setHead(headUri);
        loginuser.setNikeName(name.getText().toString());
        loginuser.setAge(age.getText().toString());
        loginuser.setHeight(height.getText().toString());
        loginuser.setWeight(weight.getText().toString());
        loginuser.setJiaoling(jiaoling.getText().toString());
//        Log.e("saveChange", loginuser.getGoodAt() + "");
//        Log.e("saveChange", loginuser.getImg() + "");
        if (labelList.size() != 1) {
            labelList.remove(labelList.size() - 1);
            loginuser.setGoodAt(labelList);
        }
        if (ImgList.size() != 1) {
            ImgList.remove(ImgList.size() - 1);
            loginuser.setImg(ImgList);
        }

        bombUtils.updateInfo(loginuser);
    }
}
