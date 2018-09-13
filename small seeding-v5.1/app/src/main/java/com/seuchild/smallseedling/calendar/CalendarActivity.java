package com.seuchild.smallseedling.calendar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.SmallSeedlingApplication;
import com.seuchild.smallseedling.item.ItemRemoveRecyclerView;
import com.seuchild.smallseedling.item.OnItemClickListener;
import com.seuchild.smallseedling.item.SpacesItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import static com.seuchild.smallseedling.SmallSeedlingApplication.server_ip;
import static com.seuchild.smallseedling.SmallSeedlingApplication.server_port;


public class CalendarActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String url = "http://"+server_ip+":"+server_port+"/notice/";
    private boolean isRefresh = false;//是否刷新中
    private MyHandler handler = new MyHandler();
    private Gson gson = new Gson();
    private Integer date;
    private String content;

    private DeleteNoticeThread mDeleteNoticeThread = new DeleteNoticeThread();
    private GetThread get_thread = new GetThread();
    private AddThread mAddThread = new AddThread();

    private List<Integer> noticeid = new ArrayList<>();

    private int positionAll = 0;

    private Context mContext;
    private Toolbar mToolbar;
    private CalendarDialog cd;
    private ItemRemoveRecyclerView rv1;
    private static List<Notice> list1 = new ArrayList<>();
    private SwipeRefreshLayout srl;
    private CalendarView mcv;
    private int year,month,day;
    private int data_count = 0;
    private String notice = null;
    private Calendar ca = Calendar.getInstance();
    public void setY(int year) {
        if(data_count == 0)
            this.year = ca.get(Calendar.YEAR);
        else
            this.year = year;
    }

    private MyAdapter adapter;
    public void setM(int month){
        if(data_count == 0)
            this.month = ca.get(Calendar.MONTH);
        else
            this.month = month;
    }
    public void setD(int day){
        if(data_count == 0)
            this.day = ca.get(Calendar.DAY_OF_MONTH);
        else
            this.day = day;
    }
    public int getY() {
        return year;
    }

    public int getM(){
        return month;
    }
    public int getD(){
        return day;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = CalendarActivity.this;
        //全屏显示
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_calendar_cardview);
        initArr();
        initCalendarView();
        srl = findViewById(R.id.srl);
        //        刷新
        srl.setOnRefreshListener(this);
        rv1 = findViewById(R.id.rv111);
        adapter = new MyAdapter(list1,CalendarActivity.this);
        rv1.setLayoutManager(new LinearLayoutManager(CalendarActivity.this));

        //RecyclerView 间距
        int space = 10;
        rv1.addItemDecoration(new SpacesItemDecoration(space));


        initRView();
        // 开始加载
        srl.setRefreshing(true);
        isRefresh = true;
        new Thread(get_thread).start();// 开启一个线程 请求完成后再设置一次适配器

        rv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void ItemClick(View v,int pos) {
                Toast.makeText(CalendarActivity.this, "** " + list1.get(pos).getNoticeid().toString() + " **", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(View v,int pos) {
                mDeleteNoticeThread.setdNid(noticeid.get(pos));
                System.out.println(noticeid.get(pos));
                new Thread(mDeleteNoticeThread).start();
                adapter.removeItem(pos);
                Toast.makeText(CalendarActivity.this, "notice " + pos  + " has been removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCalendarView() {
        mcv = findViewById(R.id.cv_calendar);
        initToolbar();
        mcv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                data_count = 1;
                setY(year);
                setD(dayOfMonth);
                setM(month);

            }
        });
    }

    private void initArr() {
        for(int i = 0;i<100;i++){
            noticeid.add((180910001+i));
        }
    }

    public void initRView() {
        rv1.setLayoutManager(new LinearLayoutManager(CalendarActivity.this));
        adapter = new MyAdapter(list1,CalendarActivity.this);
        rv1.setAdapter(adapter);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.front_page_tb);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //dialog弹窗
    public void showEditDialog() {

        if(data_count != 0)
            cd = new CalendarDialog(this, R.style.Theme_AppCompat_Dialog, onClickL,getY(),getM(),getD());
        else
            cd = new CalendarDialog(this, R.style.Theme_AppCompat_Dialog, onClickL
                    ,ca.get(Calendar.YEAR),ca.get(Calendar.MONTH)+1,ca.get(Calendar.DAY_OF_MONTH));
        cd.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_toolbar,menu);
        return true;
    }

    private View.OnClickListener onClickL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.dialog_button:
                    notice = cd.text_content.getText().toString();
                    if (notice != null){
                        date = cd.getyear()*10000+cd.getmon()*100+cd.getday();
                        content = notice;
                        mAddThread.setNid(noticeid.get(positionAll));
                        new Thread(mAddThread).start();
                        positionAll++;
                        notice = null;
                    }
                    cd.dismiss();
                    break;
                case R.id.dialog_button_cancel:
                    cd.cancel();
                    break;
            }
        }
    };




    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.calendar_add_button:
                showEditDialog();
                adapter.notifyItemInserted(list1.size());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        if(!isRefresh){
            isRefresh = true;
            new Thread(get_thread).start();
        }
    }


    private class MyHandler extends Handler {

        // 处理message的函数
        @Override
        public void handleMessage(Message msg) {
            list1  = (List<Notice>) msg.obj;
            isRefresh = false;
            srl.setRefreshing(false);
            initRView();
        }

    }

    class GetThread extends Thread{
        public void run() {
            System.out.println(url+"get");
            Request request = new Request.Builder()
                    .url(url+"get")
                    .build();

            // 异步加载 回调方法
            Call call = SmallSeedlingApplication.client.newCall(request);
            call.enqueue(new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("获取提醒 Fail");
                    Message message = Message.obtain();
                    message.obj = list1;
//                    isRefresh = false;
//                    srl.setRefreshing(false);
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    // Json --> Gson
                    list1 = gson.fromJson(response.body().string(),new TypeToken<List<Notice>>(){}.getType());
                    Message message = Message.obtain();
                    message.obj = list1;
                    handler.sendMessage(message);
                }
            });
        }
    }

    class DeleteNoticeThread extends Thread{
        int count;

        public Integer getdNid() {
            return dnid;
        }

        public void setdNid(Integer dnid) {
            this.dnid = dnid;
        }

        private Integer dnid;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void run() {    //构造REQUESTBODY
            FormBody requestBodyBuilder = new FormBody.Builder()
                    .add("noticeid", String.valueOf(dnid))
                    .build();

            System.out.println(url + "delete/" + dnid);
            Request request = new Request.Builder()
                    .url(url + "delete/" + dnid)
                    .delete(requestBodyBuilder)
                    .build();

            // 异步加载 回调方法
            Call call = SmallSeedlingApplication.client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("删除 Fail");
                    setCount(0);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("删除成功");
                    setCount(1);
                }
            });
        }
    }
    class AddThread extends Thread{
        private Integer nid;
        public void setNid(Integer nid) {
            this.nid = nid;
        }

        public void setAddcount(int addcount) {
            this.addcount = addcount;
        }

        private int addcount;
        @Override
        public void run() {    //构造REQUESTBODY
            FormBody requestBodyBuilder = new FormBody.Builder()
                    .add("date", String.valueOf(date))
                    .add("content",content)
                    .add("noticeid", String.valueOf(nid))
                    .build();

            Request request = new Request.Builder()
                    .url(url + "add")
                    .put(requestBodyBuilder)
                    .build();

            // 异步加载 回调方法
            Call call = SmallSeedlingApplication.client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("添加 Fail");
                    setAddcount(0);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("添加成功");
                    setAddcount(1);
                }
            });
        }
    }

}
