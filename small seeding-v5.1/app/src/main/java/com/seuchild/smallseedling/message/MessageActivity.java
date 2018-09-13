package com.seuchild.smallseedling.message;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.SmallSeedlingApplication;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: created by Ginger on 2018/9/3 14 27
 * E-Mail: 1020072294@qq.com
 */
public class MessageActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mrecycler;
    private ImageView back;
    private List<String> messageText = new ArrayList<>();
    private List<String> persons = new ArrayList<>();
    private List<Example> exampleList = new ArrayList<>();
    MessageThread mThread = new  MessageThread();

    MessageHandler handler = new MessageHandler(this);

    private SwipeRefreshLayout refresh;
    private boolean isRefresh = false;//是否刷新中

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mrecycler=findViewById(R.id.message_recview);

        // 下拉组件 设置下拉刷新的监听
        refresh = findViewById(R.id.message_swiperefreshlo);
        refresh.setOnRefreshListener(this);

        refresh.setRefreshing(true);
        isRefresh = true;

        initView();
        new Thread(mThread).start();

    }

    private void initView(){
//        MessageGet messageGet = new MessageGet();
//        messageGet.start();
//
//        while (messageGet.getExamples().size() == 0);
//
//        exampleList = messageGet.getExamples();
        initialData();

        mrecycler.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        mrecycler.setAdapter(new MessageAdapter(MessageActivity.this,messageText,persons));

        refresh.setRefreshing(false);
        isRefresh = false;

        messageText=null;
        messageText=new ArrayList<>();
        persons=null;
        persons=new ArrayList<>();
    }

    private void initialData() {
        for(int i = 0;i<exampleList.size();i++){
            messageText.add(exampleList.get(i).getMessageText());
            persons.add(exampleList.get(i).getPerson());
        }
    }

    @Override
    public void onRefresh() {
        if(!isRefresh){
            isRefresh = true;
            initView();
        }
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<MessageActivity> messageActivityWeakReference;

        public MessageHandler(MessageActivity messageActivity){
            messageActivityWeakReference = new WeakReference<>(messageActivity);
        }

        //处理Message
        @Override
        public void handleMessage(Message message){
            MessageActivity meessageActivity =  messageActivityWeakReference.get();
            meessageActivity.initView();
        }
    }

    public class MessageThread implements Runnable{
        private String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/message";

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
                    System.out.println("message True Fail");
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
                    exampleList = gson.fromJson(response.body().string(),new TypeToken<List<Example>>(){}.getType());
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
