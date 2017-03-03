package com.sibo.fastsport.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sibo.fastsport.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class ScannerActivity extends AppCompatActivity {

    private CaptureFragment captureFragment;
    private CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING,result);
            resultIntent.putExtras(bundle);
            resultIntent.putExtra("scanner",888);
            resultIntent.setClass(ScannerActivity.this,MainActivity.class);
            startActivity(resultIntent);
//            ScannerActivity.this.setResult(1,resultIntent);
//            ScannerActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            resultIntent.putExtra("scanner",777);
            resultIntent.setClass(ScannerActivity.this,MainActivity.class);
            startActivity(resultIntent);
//            ScannerActivity.this.setResult(RESULT_OK, resultIntent);
//            ScannerActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment,R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.scanner,captureFragment).commit();
    }

}