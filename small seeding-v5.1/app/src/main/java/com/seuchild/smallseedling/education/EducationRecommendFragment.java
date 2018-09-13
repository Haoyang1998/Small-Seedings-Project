package com.seuchild.smallseedling.education;

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
 * 教育平台 推荐 Fragment
 * Author: created by Ginger on 2018/9/4 18 07
 * E-Mail: 1020072294@qq.com
 */
public class EducationRecommendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // 线程请求的url
    String url = "http://" + SmallSeedlingApplication.server_ip +":"+ SmallSeedlingApplication.server_port+"/videos";
    public View mview;
    private RecyclerView recyclerview_recommend;
    private Context mcontext;
    private static List<Video> videos = new ArrayList<>();
    private Gson gson = new Gson();
    // 子线程更新父线程控件
    private RecommendHandler recommendhandler = new RecommendHandler(this);
    private RecommendThread recommendthread = new RecommendThread();

    // swiperefresh 组件
    private SwipeRefreshLayout refreshlo;
    private boolean isRefresh = false;//是否刷新中

    //实例化Fragment对象
    public static Fragment newInstance(){
        return new EducationRecommendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mcontext = this.getContext();
        mview = inflater.inflate(R.layout.fragment_education,null);
        // 下拉组件 设置下拉刷新的监听
        refreshlo = mview.findViewById(R.id.fragmenteducation_swiperefreshlo);
        refreshlo.setOnRefreshListener(this);
        // RecyclerView
        recyclerview_recommend = mview.findViewById(R.id.fragmenteducation_recyclerview);
        recyclerview_recommend.setLayoutManager(new LinearLayoutManager(mcontext));
        initRecommendView();
        // 开启一个线程 请求完成后再设置一次适配器
        // 开始加载
        refreshlo.setRefreshing(true);
        isRefresh = true;
        new Thread(recommendthread).start();
        return mview;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    // 初始化 recyclerview_recommend布局
    private void initRecommendView(){
        recyclerview_recommend.setAdapter(new EducationFragmentRecyclerViewAdapter(videos,mcontext));
    }

    private static class RecommendHandler extends Handler {
        private final WeakReference<EducationRecommendFragment> recommendFragment;

        public RecommendHandler(EducationRecommendFragment fragment){
            recommendFragment = new WeakReference<>(fragment);
        }

        //处理Message
        @Override
        public void handleMessage(Message message){
            EducationRecommendFragment fragment =  recommendFragment.get();
            videos = (List<Video>)message.obj;
            fragment.initRecommendView();
        }
    }

    // 线程 得到数据后提醒handler去处理
    private class RecommendThread implements Runnable{
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
                    System.out.println("推荐 Fail");
                    Message message = Message.obtain();
                    message.obj = videos;
                    isRefresh = false;
                    refreshlo.setRefreshing(false);
                    recommendhandler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    // Json --> Gson
                    videos = gson.fromJson(response.body().string(),new TypeToken<List<Video>>(){}.getType());
                    Message message = Message.obtain();
                    message.obj = videos;
                    isRefresh = false;
                    refreshlo.setRefreshing(false);
                    recommendhandler.sendMessage(message);
                }
            });
        }
    }

    // 下拉刷新
    public void onRefresh(){
        // 检查是否处于刷新状态
        if(!isRefresh){
            isRefresh = true;
            new Thread(recommendthread).start();
        }
    }

}
