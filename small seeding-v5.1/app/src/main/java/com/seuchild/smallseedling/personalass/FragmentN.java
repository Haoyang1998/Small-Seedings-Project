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

import com.seuchild.smallseedling.item.ItemRemoveRecyclerViewN;
import com.seuchild.smallseedling.item.OnItemClickListener;
import com.seuchild.smallseedling.item.SpacesItemDecoration;
import com.seuchild.smallseedling.R;

import static com.seuchild.smallseedling.SmallSeedlingApplication.server_ip;
import static com.seuchild.smallseedling.SmallSeedlingApplication.server_port;


public class FragmentN extends Fragment implements SwipeRefreshLayout.OnRefreshListener {



    private boolean isRefresh = false;//是否刷新中
        //返回JSON列表单个结果
        private Gson gson = new Gson();
        private String url = "http://"+server_ip+":"+server_port+"/help/get/0";
        private SwipeRefreshLayout sr_not;
        private ItemRemoveRecyclerViewN rv_not;
        private AssFragmentPageAdapterN adapter_not;
        private static List<Helper> list_not= new ArrayList<>();
        private MyHandler handler = new MyHandler(this);
        private FragmentNThread fn_thread = new FragmentNThread();
        private Context mcontext;

        public static Fragment newInstance(){
            return new FragmentN();
        }

        @Override
        public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
            mcontext = getContext();
            View view = inflater.inflate(R.layout.fragment_not,null);
            rv_not = view.findViewById(R.id.fragment_not_rv);
            list_not = new ArrayList<>();

            sr_not = view.findViewById(R.id.fragment_not_sr);
            sr_not.setOnRefreshListener(this);

            rv_not.setLayoutManager(new LinearLayoutManager(mcontext));

            int space = 10;
            rv_not.addItemDecoration(new SpacesItemDecoration(space));

            initMyView();
            // 开始加载
            sr_not.setRefreshing(true);
            isRefresh = true;
            new Thread(fn_thread).start();// 开启一个线程 请求完成后再设置一次适配器

            rv_not.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void ItemClick(View v,int pos) {
                    Toast.makeText(v.getContext(), "** " + list_not.get(pos) + " **", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDeleteClick(View v,int pos) {
                    Integer i = list_not.get(pos).getTaskid();
                    updateDate(i);
                    adapter_not.removeItem(pos);

                    pos+=1;
                    Toast.makeText(v.getContext(), "notice " + pos  + " has been removed", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

    private void initMyView(){
        rv_not.setLayoutManager(new LinearLayoutManager(mcontext));
        adapter_not = new AssFragmentPageAdapterN(list_not,mcontext,R.layout.fragment_not_view);
        rv_not.setAdapter(adapter_not);
    }

    public void setMenuVisibility(boolean menuVisible){
        super.setMenuVisibility(menuVisible);
        if(this.getView()!=null)
            this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
    }

        @Override
        public void onViewCreated(View view,@Nullable Bundle savedInstanceState){

            super.onViewCreated(view,savedInstanceState);
        }

        @Override
        public void onDestroyView(){
            super.onDestroyView();
        }


    private void updateDate(Integer taskid){
            DoneThread d_thread = new DoneThread();
            d_thread.updateDate(taskid);
            d_thread.start();
    }

    @Override
    public void onRefresh() {
        // 检查是否处于刷新状态
        if(!isRefresh){
            isRefresh = true;
            new Thread(fn_thread).start();
        }
    }

    class FragmentNThread extends Thread{

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
                    System.out.println("未完成求助 Fail");
                    Message message = Message.obtain();
                    message.obj = list_not;
                    isRefresh = false;
                    sr_not.setRefreshing(false);
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    // Json --> Gson
                    list_not = gson.fromJson(response.body().string(),new TypeToken<List<Helper>>(){}.getType());
                    Message message = Message.obtain();
                    message.obj = list_not;
                    isRefresh = false;
                    sr_not.setRefreshing(false);
                    handler.sendMessage(message);
                }
            });

        }
    }

    private static class MyHandler extends Handler{
        // 相当于语境
        private final WeakReference<FragmentN> myfragment;

        // 构造函数
        public MyHandler(FragmentN fragment) {
            myfragment = new WeakReference<>(fragment);
        }

        // 处理message的函数
        @Override
        public void handleMessage(Message msg) {
            FragmentN fragment = myfragment.get();
            list_not  = (List<Helper>) msg.obj;
            fragment.initMyView();
        }
    }
}