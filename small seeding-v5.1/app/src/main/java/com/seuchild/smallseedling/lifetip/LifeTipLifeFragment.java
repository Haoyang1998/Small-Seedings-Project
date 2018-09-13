package com.seuchild.smallseedling.lifetip;

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
 * 生活贴士 生活
 * Author: created by Ginger on 2018/9/4 21 22
 * E-Mail: 1020072294@qq.com
 */
public class LifeTipLifeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // 数据智慧新闻API信息
    private String appKey = "896b8aa91b68436696621591b83f69b2";
    private String category = "文化";
    private String urlHeader = "http://api.shujuzhihui.cn/api/news/list?";
    private String url = urlHeader+"appKey="+appKey+"&category="+category;

    public View view;
    public RecyclerView recyclerview_life;
    public Context mcontext;
    // json返回结果
    private static LifeTipItemResult lifetipitemresult;
    private static List<LifeTipItem> news = new ArrayList<>();
    Gson gson = new Gson();
    // 子线程更新父线程控件
    private LifeHandler handler = new LifeHandler(this);
    private LifeThread lifethread = new LifeThread();

    // swiperefresh 组件
    private SwipeRefreshLayout refreshlo;
    private boolean isRefresh = false;//是否刷新中

    // 实例化 生活贴士 生活 Fragment
    public static LifeTipLifeFragment newInstance(){
        return new LifeTipLifeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        mcontext = getContext();
        //  实例化 生活贴士 生活 布局文件
        view = inflater.inflate(R.layout.fragment_lifetip,null);
        // 下拉组件 设置下拉刷新的监听
        refreshlo = view.findViewById(R.id.fragmentlife_swiperefreshlo);
        refreshlo.setOnRefreshListener(this);
        // recyclerview
        recyclerview_life = view.findViewById(R.id.fragmentlife_recyclerview);
        recyclerview_life.setLayoutManager(new LinearLayoutManager(mcontext));
        initRecyclerLifeView();
        // 开启请求数据线程
        // 开始加载
        refreshlo.setRefreshing(true);
        isRefresh = true;
        new Thread(lifethread).start();
        return view;
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    //  初始化 Fragment中的recyclerView
    public void initRecyclerLifeView(){
        //  设置适配器
        recyclerview_life.setAdapter(new LifeTipFragmentRecyclerViewAdapter(news,mcontext));
    }

    // 线程 得到数据后提醒handler去处理
    private class LifeThread implements Runnable{
        @Override
        public void run() {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            // 异步加载 回调方法
            Call call = SmallSeedlingApplication.client.newCall(request);
            call.enqueue(new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("生活 Fail");
                    Message message = Message.obtain();
                    message.obj = news;
                    isRefresh = false;
                    refreshlo.setRefreshing(false);
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    // Json --> Gson
                    lifetipitemresult = gson.fromJson(response.body().string(),LifeTipItemResult.class);
                    news = lifetipitemresult.getRESULT().getNewsList();
                    Message message = Message.obtain();
                    message.obj = news;
                    isRefresh = false;
                    refreshlo.setRefreshing(false);
                    handler.sendMessage(message);
                }
            });
        }
    }

    private static class LifeHandler extends Handler {
        // 相当于语境
        private final WeakReference<LifeTipLifeFragment> lifefragment;

        // 构造函数
        public LifeHandler(LifeTipLifeFragment fragment) {
            lifefragment = new WeakReference<>(fragment);
        }

        // 处理message的函数
        @Override
        public void handleMessage(Message msg) {
            LifeTipLifeFragment fragment = lifefragment.get();
            news = (List<LifeTipItem>) msg.obj;
            fragment.initRecyclerLifeView();
        }

    }

    // 下拉刷新
    public void onRefresh(){
        // 检查是否处于刷新状态
        if(!isRefresh){
            isRefresh = true;
            new Thread(lifethread).start();
        }
    }

}
