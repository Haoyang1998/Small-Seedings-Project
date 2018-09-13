package com.seuchild.smallseedling.schoolnews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.SmallSeedlingApplication;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;

public class SchoolNewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private List<String> title = new ArrayList<>();
    private List<String> type = new ArrayList<>();
    private List<String> time = new ArrayList<>();
    private List<String> place = new ArrayList<>();
    private List<String> introduce = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<String> webUrl = new ArrayList<>();
    schoolThread sThread = new schoolThread();

    private WebView webView;

    private SwipeRefreshLayout refresh;
    private boolean isRefresh = false;//是否刷新中

    // handler
    schoolNewsHandler handler = new schoolNewsHandler(this);

    private ImageView back;
    private List<EXAMPLE> exampleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolnews);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.schoolnews_recview);
        // 下拉组件 设置下拉刷新的监听
        refresh = findViewById(R.id.schoolnews_swiperefreshlo);
        refresh.setOnRefreshListener(this);

        refresh.setRefreshing(true);
        isRefresh = true;

        initView();
        new Thread(sThread).start();

    }

    public void playSchoolNews(View view){
        String weburl = ((TextView)view.findViewById(R.id.webTextView)).getText().toString();
        webView=findViewById(R.id.SchoolnewsWebView);
        Uri uri = Uri.parse(weburl);  //url为你要链接的地址
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

        webView.loadUrl(weburl);

//        Intent intent = new Intent(SchoolNewsActivity.this,SchoolNewsMessage.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("webUrl",weburl);
//        intent.putExtras(bundle);
//        startActivity(intent);

    }

    @Override
    public void onRefresh() {
        if(!isRefresh){
            isRefresh = true;
            initView();
        }
    }

    public void initView(){
        initData();

        recyclerView.setLayoutManager(new LinearLayoutManager(SchoolNewsActivity.this));
        recyclerView.setAdapter(new schoolNewsAdapter(SchoolNewsActivity.this,title,type,time,place,introduce,images,webUrl));

        refresh.setRefreshing(false);
        isRefresh = false;
        title=null;
        title=new ArrayList<>();
        type=null;
        type=new ArrayList<>();
        time=null;
        time=new ArrayList<>();
        place=null;
        place=new ArrayList<>();
        images=null;
        images=new ArrayList<>();
        webUrl=null;
        webUrl=new ArrayList<>();
    }

    private void initData() {
        System.out.println(exampleList.size()+"????!??!??!?!!!!!!!!!!!!!???");

        for (int i=0;i<exampleList.size();i++){
            title.add(exampleList.get(i).getNewsTitle());
            type.add(exampleList.get(i).getNewsType());
            time.add(exampleList.get(i).getNewsTime());
            place.add(exampleList.get(i).getNewsPalce());
            introduce.add(exampleList.get(i).getIntroduce());
            images.add(exampleList.get(i).getImage());
            webUrl.add(exampleList.get(i).getWeburl());
            System.out.println("活动里面 网页的Url!!!!!!!!"+webUrl.get(i));
        }
    }

    private static class schoolNewsHandler extends Handler {
        private final WeakReference<SchoolNewsActivity> schoolNewsActivityWeakReference;

        public schoolNewsHandler(SchoolNewsActivity schoolNewsActivity){
            schoolNewsActivityWeakReference = new WeakReference<>(schoolNewsActivity);
        }

        //处理Message
        @Override
        public void handleMessage(Message message){
            SchoolNewsActivity schoolNewsActivity =  schoolNewsActivityWeakReference.get();
            schoolNewsActivity.initView();
        }
    }

    public class schoolThread implements Runnable{
        private String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/schoolNews";

        @Override
        public void run() {
            refresh.setRefreshing(true);
            isRefresh = true;
            Request request = new Request.Builder().url(url).build();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Call call = SmallSeedlingApplication.client.newCall(request);
            call.enqueue( new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("schoolnews True Fail");
                    Message message = Message.obtain();
                    message.obj = exampleList;
                    isRefresh = false;
                    refresh.setRefreshing(false);
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    // Json --> Gson
                    Gson gson = new Gson();
                    exampleList = gson.fromJson(response.body().string(),new TypeToken<List<EXAMPLE>>(){}.getType());
                    System.out.println("看看这里有没有WEB连接"+exampleList.get(1).getWeburl());
                    Message message = Message.obtain();
                    message.obj = exampleList;
                    isRefresh = false;
                    refresh.setRefreshing(false);
                    handler.sendMessage(message);
                }
            });
        }
    }


}

