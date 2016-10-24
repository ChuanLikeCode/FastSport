package com.sibo.fastsport.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.activity.BodyjudgmentActivity;
import com.sibo.fastsport.domain.Pickers;
import com.sibo.fastsport.view.CircleImageView;
import com.sibo.fastsport.view.PickerScrollView;

import java.util.ArrayList;
import java.util.List;


public class MakePlanFragment extends Fragment implements View.OnClickListener, TextWatcher, PickerScrollView.onSelectListener {
    private static final int IMAGE = 1;
    public static List<Pickers> exercise, pickerHeight, pickerWeight, pickerMuscleMass, pickerBodyFat;
    private static int image = 1;
    private View makePlanFragment;
    private TextView nextStep, userHeight, userWeight, usermuscleMass, userBodyFat, exerciseMass, ok, no;
    private EditText name, sex;
    private LinearLayout llUserHeight, llUserWeight, llUsermuscleMass, llUserBodyFat;
    private CircleImageView touxiang;
    private PickerScrollView pickers;
    private RelativeLayout chooseExercise;
    private ImageView contrastPhoto;
    private int llSelected;
    private View scrollViewLayout;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (makePlanFragment == null) {
            makePlanFragment = inflater.inflate(R.layout.fragment_makeplan, container, false);
        } else {
            return makePlanFragment;
        }
        initView();
        initData();
        initListener();
        return makePlanFragment;
    }

    private void initData() {
        exercise = new ArrayList<>();
        pickerWeight = new ArrayList<>();
        pickerHeight = new ArrayList<>();
        pickerMuscleMass = new ArrayList<>();
        pickerBodyFat = new ArrayList<>();
        for (int i = 0; i < 121; i++) {
            String j = i + 30 + "KG";
            Pickers pickers = new Pickers();
            pickers.setShowId(i + "");
            pickers.setShowConetnt(j);
            pickerWeight.add(pickers);
        }
        for (int i = 0; i < 71; i++) {
            String j = i + 150 + "CM";
            Pickers pickers = new Pickers();
            pickers.setShowId(i + "");
            pickers.setShowConetnt(j);
            pickerHeight.add(pickers);
        }
        for (int i = 0; i < 101; i++) {
            String j = i + "%";
            Pickers pickers = new Pickers();
            pickers.setShowId(i + "");
            pickers.setShowConetnt(j);
            pickerBodyFat.add(pickers);
        }
        for (int i = 0; i < 101; i++) {
            String j = i + "KG";
            Pickers pickers = new Pickers();
            pickers.setShowId(i + "");
            pickers.setShowConetnt(j);
            pickerMuscleMass.add(pickers);
        }
        String[] str = {"从不运动", "偶尔运动", "经常运动"};
        for (int i = 0; i < 3; i++) {
            Pickers pickers = new Pickers();
            pickers.setShowId(i + "");
            pickers.setShowConetnt(str[i]);
            exercise.add(pickers);
        }
    }

    private void initListener() {
        nextStep.setOnClickListener(this);
        sex.addTextChangedListener(this);
        name.addTextChangedListener(this);
        llUserBodyFat.setOnClickListener(this);
        llUserWeight.setOnClickListener(this);
        llUsermuscleMass.setOnClickListener(this);
        llUserHeight.setOnClickListener(this);
        pickers.setOnSelectListener(this);
        touxiang.setOnClickListener(this);
        chooseExercise.setOnClickListener(this);
        contrastPhoto.setOnClickListener(this);
    }

    private void initView() {
        nextStep = (TextView) makePlanFragment.findViewById(R.id.plan_tv_nextStep);
        name = (EditText) makePlanFragment.findViewById(R.id.plan_et_name);
        sex = (EditText) makePlanFragment.findViewById(R.id.plan_et_sex);
        userBodyFat = (TextView) makePlanFragment.findViewById(R.id.plan_tv_bodyFat);
        userHeight = (TextView) makePlanFragment.findViewById(R.id.plan_tv_height);
        usermuscleMass = (TextView) makePlanFragment.findViewById(R.id.plan_tv_muscleMass);
        userWeight = (TextView) makePlanFragment.findViewById(R.id.plan_tv_weight);
        llUserBodyFat = (LinearLayout) makePlanFragment.findViewById(R.id.plan_ll_userBodyFat);
        llUserHeight = (LinearLayout) makePlanFragment.findViewById(R.id.plan_ll_userHeight);
        llUsermuscleMass = (LinearLayout) makePlanFragment.findViewById(R.id.plan_ll_userMuscleMass);
        llUserWeight = (LinearLayout) makePlanFragment.findViewById(R.id.plan_ll_userWeight);
        scrollViewLayout = getActivity().getLayoutInflater().inflate(R.layout.scrollview_select, null);

        pickers = (PickerScrollView) scrollViewLayout.findViewById(R.id.pickers);
        touxiang = (CircleImageView) makePlanFragment.findViewById(R.id.plan_iv_camera);
        chooseExercise = (RelativeLayout) makePlanFragment.findViewById(R.id.plan_rl_exercise);
        exerciseMass = (TextView) makePlanFragment.findViewById(R.id.plan_tv_exerciseMass);
        contrastPhoto = (ImageView) makePlanFragment.findViewById(R.id.plan_iv_contrastPhoto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plan_rl_exercise:
                llSelected = 5;
                showDialog(exercise);
                break;
            case R.id.plan_tv_nextStep:
                startActivity(new Intent(getActivity(), BodyjudgmentActivity.class));
                break;
            case R.id.plan_ll_userBodyFat:
                llSelected = 4;
                showDialog(pickerBodyFat);
                break;
            case R.id.plan_ll_userWeight:
                llSelected = 2;
                showDialog(pickerWeight);
                break;
            case R.id.plan_ll_userHeight:
                llSelected = 1;
                showDialog(pickerHeight);
                break;
            case R.id.plan_ll_userMuscleMass:
                llSelected = 3;
                showDialog(pickerMuscleMass);
                break;
            case R.id.plan_iv_camera:
                image = 1;
                jumpSelectImage();
                break;
            case R.id.plan_iv_contrastPhoto:
                image = 2;
                jumpSelectImage();
                break;
        }
    }

    private void jumpSelectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == IMAGE) && (resultCode == getActivity().RESULT_OK) && (data != null)) {
            Uri selectedImage = data.getData();
            Cursor c = getActivity().getContentResolver().query(selectedImage, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(MediaStore.Images.Media.DATA);
            String imagePath = c.getString(columnIndex);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (image == 1) {
                touxiang.setImageBitmap(bitmap);
            } else {
                contrastPhoto.setImageBitmap(bitmap);
            }

            c.close();
        }

    }

    private void showDialog(List<Pickers> pickerItems) {
        pickers.setData(pickerItems);
        pickers.setSelected(0);
        if (dialog == null) {
            dialog = new Dialog(getActivity());
            Window window = dialog.getWindow();

            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.main_menu_animstyle);
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.getDecorView().setPadding(0, 0, 0, 0);
            Display d = getActivity().getWindow().getWindowManager().getDefaultDisplay();
            //Display d = window.getWindowManager().getDefaultDisplay();
            WindowManager.LayoutParams p = window.getAttributes();
            p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
            window.setAttributes(p);
            // 设置点击外围解散
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(scrollViewLayout);
        }
        // 设置显示动画
        dialog.show();

    }

    /**
     * EditText内容的变化监听,要实现三个方法，添加TextWatcher
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //s--未改变之前的内容
        //start--内容被改变的开始位置
        //count--原始文字被删除的个数
        //after--新添加的内容的个数
        //---------start和count结合从s中获取被删除的内容-------
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //s--改变之后的新内容
        //start--内容被改变的开始位置
        //before--原始文字被删除的个数
        //count--新添加的内容的个数
        //---------start和count结合从s中获取新添加的内容-------
        name.setBackgroundResource(R.drawable.plan_bg_change);
        sex.setBackgroundResource(R.drawable.plan_bg_change);
    }

    @Override
    public void afterTextChanged(Editable s) {
        //s--最终内容

    }

    @Override
    public void onSelect(Pickers pickers) {
        switch (llSelected) {
            case 1:
                userHeight.setText(pickers.getShowConetnt());
                break;
            case 2:
                userWeight.setText(pickers.getShowConetnt());
                break;
            case 3:
                usermuscleMass.setText(pickers.getShowConetnt());
                break;
            case 4:
                userBodyFat.setText(pickers.getShowConetnt());
                break;
            case 5:
                exerciseMass.setText(pickers.getShowConetnt());
                break;
        }
    }
}
