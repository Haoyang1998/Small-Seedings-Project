package com.seuchild.smallseedling.personalass;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import com.seuchild.smallseedling.SmallSeedlingApplication;
import com.seuchild.smallseedling.item.ItemRemoveRecyclerViewD;
import com.seuchild.smallseedling.item.OnItemClickListener;
import com.seuchild.smallseedling.item.SpacesItemDecoration;
import com.seuchild.smallseedling.R;

import static com.seuchild.smallseedling.SmallSeedlingApplication.server_ip;
import static com.seuchild.smallseedling.SmallSeedlingApplication.server_port;

public class FragmentD extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //返回JSON列表单个结果
    private Gson gson = new Gson();

    private Context mcontext;
    private String url = "http://"+server_ip+":"+server_port+"/help/get/1";
    private ItemRemoveRecyclerViewD rv_done;
    private AssFragmentPageAdapterD adapter_done;
    private SwipeRefreshLayout sr_done;
    private boolean isRefresh = false;//是否刷新中
    private static List<Helper> list_done = new ArrayList<>();
    private MyHandler handler = new MyHandler(this);
    private FragmentDThread fd_thread = new FragmentDThread();

    public static Fragment newInstance(){
        return new FragmentD();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){

        mcontext = getContext();
        View view = inflater.inflate(R.layout.fragment_done,null);
        //刷新
        sr_done = view.findViewById(R.id.fragment_done_sr);
        sr_done.setOnRefreshListener(this);

        //初始化recyclerview
        rv_done = view.findViewById(R.id.fragment_done_rv);
        rv_done.setLayoutManager(new LinearLayoutManager(mcontext));

        //间隔
        int space = 10;
        rv_done.addItemDecoration(new SpacesItemDecoration(space));

        //适配器
        initMyView();
        // 开始加载
        sr_done.setRefreshing(true);
        isRefresh = true;
        new Thread(fd_thread).start();// 开启一个线程 请求完成后再设置一次适配器

        rv_done.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void ItemClick(View v,int pos) {
                Toast.makeText(mcontext, "** " + list_done.get(pos) + " **", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(View v,int pos) {
                Integer i = list_done.get(pos).getTaskid();
                deleteDate(i);
                adapter_done.removeItem(pos);
                pos+=1;
                Toast.makeText(mcontext, "notice " + pos  + " has been removed", Toast.LENGTH_SHORT).show();
                pos-=1;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){

        super.onViewCreated(view,savedInstanceState);
    }


    // 初始化 recyclerview_focus布局
    private void initMyView(){
        rv_done.setLayoutManager(new LinearLayoutManager(mcontext));
        adapter_done = new AssFragmentPageAdapterD(list_done,mcontext,R.layout.fragment_done_view);
        rv_done.setAdapter(adapter_done);
    }

    public void setMenuVisibility(boolean menuVisible){
        super.setMenuVisibility(menuVisible);
        if(this.getView()!=null)
            this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    private void deleteDate(Integer taskid){
        DeleteThread d_thread = new DeleteThread();
        d_thread.deleteDate(taskid);
        d_thread.start();
        while(d_thread.getCount()==0);
    }

    @Override
    public void onRefresh() {
        //检查是否处于刷新状态
        if(!isRefresh){
            isRefresh = true;
            new Thread(fd_thread).start();
        }
    }

    //内联线程
    class FragmentDThread extends Thread{


        public void run() {

            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            // 异步加载 回调方法
            Call call = SmallSeedlingApplication.client.newCall(request);
            call.enqueue(new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("已完成求助 Fail");
                    Message message = Message.obtain();
                    message.obj = list_done;
                    isRefresh = false;
                    sr_done.setRefreshing(false);
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    // Json --> Gson
                    list_done = gson.fromJson(response.body().string(),new TypeToken<List<Helper>>(){}.getType());
                    Message message = Message.obtain();
                    message.obj = list_done;
                    isRefresh = false;
                    sr_done.setRefreshing(false);
                    handler.sendMessage(message);
                }
            });

        }
    }

    private static class MyHandler extends Handler {
        // 相当于语境
        private final WeakReference<FragmentD> myfragment;

        // 构造函数
        public MyHandler(FragmentD fragment) {
            myfragment = new WeakReference<>(fragment);
        }

        // 处理message的函数
        @Override
        public void handleMessage(Message msg) {
            FragmentD fragment = myfragment.get();
            list_done  = (List<Helper>) msg.obj;
            fragment.initMyView();
        }

    }
}
