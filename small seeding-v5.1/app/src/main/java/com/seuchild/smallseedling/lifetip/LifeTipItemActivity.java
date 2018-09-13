package com.seuchild.smallseedling.lifetip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.SmallSeedlingApplication;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: created by Ginger on 2018/9/12 15 16
 * E-Mail: 1020072294@qq.com
 */
public class LifeTipItemActivity extends AppCompatActivity{
    // 内容请求url
    private String content_url = "http://api.shujuzhihui.cn/api/news/detail?appKey="+"896b8aa91b68436696621591b83f69b2"+"&newsId=";
    Gson gson = new Gson();

    // 固定的内容
    private String image_url = "http://39.105.38.48/images/2018/09/12/f4a9230f8709f9a3.gif";
    private String newstitle = "新闻标题";
    private String newsId = "c5c146abfb62304d13a1b19eff0312ef";
    private String newscontent = "请求加载中...";// 通过newsId获取新闻内容

    // 内容详细数据类
    private NewsDetail newsDetail;
    private RESULT result;

    // activity中的一些控件
    private ImageView newsBackground;
    private TextView content;

    private Context mcontext;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    // 请求线程
    private ContentThread contentThread = new ContentThread();
    private LifeTipItemHandler lifeTipItemHandler = new LifeTipItemHandler(LifeTipItemActivity.this);

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_lifetipitem);
        mcontext = LifeTipItemActivity.this;

        // 获取activity传递的参数
        Intent intent=getIntent();
        newsId = intent.getStringExtra("newsId");
        newstitle = intent.getStringExtra("newsTitle");
        image_url = intent.getStringExtra("newsImage");

        // 获取具体新闻中的组件
        newsBackground = findViewById(R.id.activitylifetip_image);
        collapsingToolbarLayout = findViewById(R.id.activityitemlifetip_colltoolbarlo);
        content = findViewById(R.id.activitylifetipitem_body);

        // 设置组件内容
        Glide.with(mcontext).load(image_url).into(newsBackground);
        collapsingToolbarLayout.setTitle(newstitle);

        content.setText(Html.fromHtml(newscontent));
        // 请求详细新闻的api
        content_url = content_url + newsId;
        new Thread(contentThread).start();

    }

    //  返回上一个界面
    public void onBack(View view){
        finish();
    }

    // 根据NewsId获取新闻内容
    private class ContentThread implements Runnable{
        @Override
        public void run() {
            Request request = new Request.Builder()
                    .url(content_url)
                    .build();

            // 异步加载 回调方法
            Call call = SmallSeedlingApplication.client.newCall(request);
            call.enqueue(new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("请求详细新闻 Fail");
                    Message message = Message.obtain();
                    message.obj = "请求失败";
                    lifeTipItemHandler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    // Json --> Gson
                    newsDetail = gson.fromJson(response.body().string(),NewsDetail.class);
                    result = newsDetail.getRESULT();
                    newscontent = result.getContent();
                    Message message = Message.obtain();
                    message.obj = newscontent;
                    lifeTipItemHandler.sendMessage(message);
                }
            });
        }
    }

    // 设置新闻内容
    private void setNewsContent(){
        content.setText(Html.fromHtml(newscontent));
    }

    private static class LifeTipItemHandler extends Handler {
        // 相当于语境
        private final WeakReference<LifeTipItemActivity> lifetipitemactivity;

        // 构造函数
        public LifeTipItemHandler(LifeTipItemActivity activity) {
            lifetipitemactivity = new WeakReference<>(activity);
        }

        // 处理message的函数
        @Override
        public void handleMessage(Message msg) {
            LifeTipItemActivity activity = lifetipitemactivity.get();
            activity.setNewsContent();
        }

    }

}
