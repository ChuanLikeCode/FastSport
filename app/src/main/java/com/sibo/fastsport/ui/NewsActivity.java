package com.sibo.fastsport.ui;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sibo.fastsport.R;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class NewsActivity extends BaseTranslucentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //返回键
    private ImageView back;
    //下拉刷新，上拉加载控件  适配器
    private PullToRefreshListView pfl;

    //PullToRefreshListView得到的ListView
    private ListView listView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        //adapter = new NewsItemAdapter(this,list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getAccessToken = NetUtils.doGet(Constant.getAccessToken);
                try {
                    JSONObject object = new JSONObject(getAccessToken);
                    Log.e("object", object.toString());
                    String token = object.getString(Constant.ACCESSTOKEN);
                    Log.e("NewsActivity", "token-------------" + token);
                    MediaType MEDIA_TYPE_MARKDOWN
                            = MediaType.parse("text/x-markdown; charset=utf-8");
                    String postBody = "{\n" +
                            "    \"type\":\"news\",\n" +
                            "    \"offset\":0,\n" +
                            "    \"count\":10\n" +
                            "}";
                    //利用OkHttp来作为网络请求的框架，它的优点有很多
                    // 1.Android6.0版本之后不支持httpclient，而他是封装的httpurlconnection
                    //2.它支持https请求
                    //3.非常高效，支持SPDY、连接池、GZIP和 HTTP 缓存。默认情况下，OKHttp会自动处理常见的网络问题，像二次连接、SSL的握手问题。
                    // 如果你的应用程序中集成了OKHttp，Retrofit默认会使用OKHttp处理其他网络层请求。OkHttp是一个相对成熟的解决方案，
                    // 据说Android4.4的源码中可以看到HttpURLConnection已经替换成OkHttp实现了。所以我们更有理由相信OkHttp的强大
                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Constant.getMaterial + token)
                            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                            .build();
                    mOkHttpClient.connectTimeoutMillis();
                    Response response = mOkHttpClient.newCall(request).execute();
                    String responseResult = response.body().string();
                    Log.e("gaolei", "responseresult--------------MessageActivity------" + responseResult);
                    JSONObject object1 = new JSONObject(responseResult);
                    JSONArray jsonArray = object1.getJSONArray("item");
                    String content = jsonArray.getJSONObject(0).getString("content");
                    Log.e("content", content + "");
                    String[] str = content.split("data-type", 2);
                    content = str[0];
                    Log.e("content", content + "");
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initListener() {
        //listView.setOnItemClickListener(this);
    }

    private void initView() {
        setOrChangeTranslucentColor(findViewById(R.id.news_rl), getResources().getColor(R.color.dip_red));
//        pfl = (PullToRefreshListView) findViewById(R.id.news_pfl);
//        listView = pfl.getRefreshableView();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
