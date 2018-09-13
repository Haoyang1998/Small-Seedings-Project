package com.seuchild.smallseedling.square;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * create by cz
 */
public class activitiesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView rv_activities;
    private List<String> mname=new ArrayList<>();
    private List<String> mcontent=new ArrayList<>();
    private List<String> miamge =new ArrayList<>();
    private Context mcontext;
    public View view;
    private List<EXAMPLE> exampleList = new ArrayList<>();
    ActiThread actiThread=new ActiThread();
    //添加下拉刷新效果
    private SwipeRefreshLayout refresh;
    private boolean isRefresh =false;
    actiHandler handler=new actiHandler(this);
    public static activitiesFragment newInstance(){return new activitiesFragment();}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        //初始化布局和部件
        mcontext = getContext();
        view = inflater.inflate(R.layout.fragment_activities,null);
        rv_activities=view.findViewById(R.id.activities_recyclerview);
        refresh=view.findViewById(R.id.activities_swiperefreshlo);
        //获取数据到example对象中


        refresh.setOnRefreshListener(this);

        initData();
        new Thread(actiThread).start();
        return view;
    }
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    public void initRecyclerActiView(){
        rv_activities.setLayoutManager(new LinearLayoutManager(mcontext));
        rv_activities.setAdapter(new SquareRecyclerViewAdpter(mcontext,mcontent,mname,miamge));
    }
    public void initData(){
        mname.clear();
        mcontent.clear();
        miamge.clear();

        for (int i=0;i<exampleList.size();i++){
            mname.add(exampleList.get(i).getMName());
            mcontent.add(exampleList.get(i).getMContent());
            miamge.add(exampleList.get(i).getMUrl());
        }

        isRefresh=false;
        refresh.setRefreshing(false);
        initRecyclerActiView();

    }

    private static class actiHandler extends Handler {
        private final WeakReference<activitiesFragment> actiFragment;
        public actiHandler(activitiesFragment fragment){
            actiFragment = new WeakReference<>(fragment);
        }
        //处理Message
        @Override
        public void handleMessage(Message message){
            activitiesFragment fragment =  actiFragment.get();
            fragment.initData();
        }
    }



    public class ActiThread implements Runnable{
        private String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/acti";

        @Override
        public void run(){
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
                    System.out.println("DreamList True Fail");
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
                    Message message = Message.obtain();
                    message.obj = exampleList;
                    isRefresh = false;
                    refresh.setRefreshing(false);
                    handler.sendMessage(message);
                }
            });
        }

    }

    @Override
    public void onRefresh() {
        // 检查是否处于刷新状态
        if(!isRefresh){
            isRefresh = true;
            new Thread(actiThread).start();
        }
    }
}
