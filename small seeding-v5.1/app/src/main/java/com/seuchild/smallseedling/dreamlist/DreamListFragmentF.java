package com.seuchild.smallseedling.dreamlist;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;

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

public class DreamListFragmentF extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public View mview;
    private RecyclerView recyclerView_false;
    private Context mcontext;
    private static List<String> Fdata = new ArrayList<>();

    private List<EXAMPLE> exampleList = new ArrayList<>();

    private SwipeRefreshLayout refresh;
    private boolean isRefresh = false;//是否刷新中
    FalseHandler handler = new FalseHandler(this);

    FalseThread falseThread = new FalseThread();

    private FalseHandler falseHandler =new FalseHandler(this);

    public static DreamListFragmentF newInstance(List<String> fdata) {
        //Fdata = fdata;
        return new DreamListFragmentF();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mcontext = getContext();
        mview = inflater.inflate(R.layout.fragment_dreamlist, null);
        recyclerView_false = mview.findViewById(R.id.fragmentdreamlist_recyclerview);

        // 下拉组件 设置下拉刷新的监听
        refresh = mview.findViewById(R.id.fragmentdreamlist_swiperefreshlo);
        refresh.setOnRefreshListener(this);

        refresh.setRefreshing(true);
        isRefresh = true;


        initFalseView();
        new Thread(falseThread).start();
        return mview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    // 初始化 recyclerview_focus布局
    private void initFalseView() {

        for (int i = 0; i < exampleList.size(); i++) {
            if (exampleList.get(i).getState().equals("false")) {
                Fdata.add(exampleList.get(i).getDream());
            }
        }

        // 设置布局管理器
        recyclerView_false.setLayoutManager(new LinearLayoutManager(mcontext));
        // 设置适配器
        recyclerView_false.setAdapter(new DreamListAdapter(Fdata, R.drawable.weishixian));
        isRefresh = false;
        refresh.setRefreshing(false);
        Fdata = null;
        Fdata = new ArrayList<>();

    }


    @Override
    public void onRefresh() {
        // 检查是否处于刷新状态
        if (!isRefresh) {
            isRefresh = true;
            new Thread(falseThread).start();
        }
    }
    private static class FalseHandler extends Handler {
        private final WeakReference<DreamListFragmentF> falseFragment;

        public FalseHandler(DreamListFragmentF fragment){
            falseFragment = new WeakReference<>(fragment);
        }

        //处理Message
        @Override
        public void handleMessage(Message message){
            DreamListFragmentF fragment = falseFragment.get();
            fragment.initFalseView();
        }
    }

    public class FalseThread implements Runnable{
        private String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/dreamlist";

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
                    System.out.println("DreamList False Fail");
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

}