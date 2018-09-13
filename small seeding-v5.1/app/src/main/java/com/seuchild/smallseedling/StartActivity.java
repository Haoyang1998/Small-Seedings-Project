package com.seuchild.smallseedling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;


import com.seuchild.smallseedling.dreamlist.DreamListActivity;
import com.seuchild.smallseedling.education.EducationActivity;
import com.seuchild.smallseedling.health.HealthActivity;
import com.seuchild.smallseedling.lifetip.LifeTipActivity;
import com.seuchild.smallseedling.message.MessageActivity;
import com.seuchild.smallseedling.myInfo.MyInfoActivity;
import com.seuchild.smallseedling.map.MapActivity;
import com.seuchild.smallseedling.parent.ParentActivity;
import com.seuchild.smallseedling.personalass.PersonalASSActivity;
import com.seuchild.smallseedling.schoolnews.SchoolNewsActivity;
import com.seuchild.smallseedling.square.SquareActivity;
import com.seuchild.smallseedling.startDirect.MyPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class StartActivity extends AppCompatActivity {
    private Context mContext;

    //首页图标排列
    private GridView grid_view;
    private List<Map<String,Object>> list;
    private String[] from = {"image","title"};
    private int[] to = {R.id.grid_icon,R.id.grid_title};
    private SimpleAdapter mAdapter = null;

    //  ViewPager 变量
    private ViewPager vpager_two;
    private ArrayList<View> aList2;
    private MyPagerAdapter mAdapter2;
    private RelativeLayout diy_1,diy_2,diy_3,diy_4,diy_5,diy_6,diy_7,diy_8;

    //  三个点
    private ImageView rmFirst;
    private ImageView rmSecond;
    private ImageView rmThird;
    private ImageView rmFour;

    //  首页底部导航栏
    private BottomNavigationView start_bnv;

    // 点击底部导航栏 刷新本界面
    public void refresh(){
        //onCreate(null);
        System.out.println("");
    }

    // 异常退出保存当前Activity的信息
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mContext = StartActivity.this;
        //  ViewPager设置
        rmFirst=findViewById(R.id.rmFirst);
        rmSecond=findViewById(R.id.rmSecond);
        rmThird=findViewById(R.id.rmThird);
        rmFour=findViewById(R.id.rmFour);

        vpager_two=findViewById(R.id.mainpage_viewpager);
        aList2 = new ArrayList<View>();
        LayoutInflater li2 = getLayoutInflater();
        aList2.add(li2.inflate(R.layout.mainpage_vp_1,null,false));
        aList2.add(li2.inflate(R.layout.mainpage_vp_2,null,false));
        aList2.add(li2.inflate(R.layout.mainpage_vp_3,null,false));
        aList2.add(li2.inflate(R.layout.mainpage_vp_4,null,false));
        mAdapter2 = new MyPagerAdapter(aList2);
        vpager_two.setAdapter(mAdapter2);

        vpager_two.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rmFirst.setImageResource(R.drawable.dot1);
                        rmSecond.setImageResource(R.drawable.dotd1);
                        rmThird.setImageResource(R.drawable.dotd1);
                        rmFour.setImageResource(R.drawable.dotd1);
                        break;
                    case 1:
                        rmFirst.setImageResource(R.drawable.dotd1);
                        rmSecond.setImageResource(R.drawable.dot1);
                        rmThird.setImageResource(R.drawable.dotd1);
                        rmFour.setImageResource(R.drawable.dotd1);
                        break;
                    case 2:
                        rmFirst.setImageResource(R.drawable.dotd1);
                        rmSecond.setImageResource(R.drawable.dotd1);
                        rmThird.setImageResource(R.drawable.dot1);
                        rmFour.setImageResource(R.drawable.dotd1);
                        break;
                    case 3:
                        rmFirst.setImageResource(R.drawable.dotd1);
                        rmSecond.setImageResource(R.drawable.dotd1);
                        rmThird.setImageResource(R.drawable.dotd1);
                        rmFour.setImageResource(R.drawable.dot1);
                        break;

                    default:
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //定时器 3s轮播 3s间隔
        Timer tm = new Timer();
        tm.scheduleAtFixedRate(new MyTimetask(),2000,2000);



        //  获取首页两行图标排列
        grid_view = findViewById(R.id.mainpage_gridview);
        list = getList();
        mAdapter =  new SimpleAdapter(this,list,R.layout.item_grid,from,to);
        grid_view.setAdapter(mAdapter);
        //  设置八个小图标的点击事件
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> args0,View args1,int idx,long arg3) {
                // "寄语板","安全","健康","愿望清单","教育平台","生活贴士","校园时讯","个人求助"
                if(list.get(idx).containsValue("校园时讯"))
                    startActivity(new Intent(StartActivity.this,SchoolNewsActivity.class));
                if(list.get(idx).containsValue("安全界面"))
                    startActivity(new Intent(StartActivity.this,MapActivity.class));
                if(list.get(idx).containsValue("生活贴士"))
                    startActivity(new Intent(StartActivity.this,LifeTipActivity.class));
                if(list.get(idx).containsValue("我的健康"))
                    startActivity(new Intent(StartActivity.this,HealthActivity.class));
                if(list.get(idx).containsValue("愿望清单"))
                    startActivity(new Intent(StartActivity.this,DreamListActivity.class));
                if(list.get(idx).containsValue("个人求助"))
                    startActivity(new Intent(StartActivity.this,PersonalASSActivity.class));
                if(list.get(idx).containsValue("寄语板"))
                    startActivity(new Intent(StartActivity.this,MessageActivity.class));
                if(list.get(idx).containsValue("教育平台"))
                    startActivity(new Intent(StartActivity.this,EducationActivity.class));
            }
        });

        //  获取首页底部导航栏
        start_bnv = findViewById(R.id.start_bnv);
        //  设置监听器 底部导航栏第一次选择
        // start_bnv.performClick();
        start_bnv.getMenu().getItem(0).setChecked(true);
        start_bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    //  界面跳转方法 本界面刷新 新界面就新建一个
                    @Override
                    public boolean onNavigationItemSelected(@Nullable MenuItem item) {
                        int num = item.getOrder();
                        if( num ==2)
                        {
                            startActivity(new Intent(StartActivity.this,SquareActivity.class));
                        }
                        else if(num ==3){
                            startActivity(new Intent(StartActivity.this,ParentActivity.class));
                        }
                        else if(num == 4){
                            startActivity(new Intent(StartActivity.this,MyInfoActivity.class));
                        }
                        else
                            return false;

                        //  保存当前Activity状态信息
                        //  onSaveInstanceState(savedInstanceState);
                        //  销毁当前页
                        finish();
                        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
        );
        //start_bnv.setSelectedItemId(R.id.bnv_start);

        //校园时讯监听事件
        diy_1=findViewById(R.id.mainpage_diy1);
        diy_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,SchoolNewsActivity.class);
                startActivity(intent);
            }
        });

        //安全界面监听事件
        diy_2=findViewById(R.id.mainpage_diy2);
        diy_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

        //生活贴士监听事件
        diy_3=findViewById(R.id.mainpage_diy3);
        diy_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,LifeTipActivity.class);
                startActivity(intent);
            }
        });

        //我的健康监听事件
        diy_4=findViewById(R.id.mainpage_diy4);
        diy_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,HealthActivity.class);
                startActivity(intent);
            }
        });

        //愿望清单监听事件
        diy_5=findViewById(R.id.mainpage_diy5);
        diy_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,DreamListActivity.class);
                startActivity(intent);
            }
        });

        //个人求助监听事件
        diy_6=findViewById(R.id.mainpage_diy6);
        diy_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,PersonalASSActivity.class);
                startActivity(intent);
            }
        });

        //寄语板监听事件
        diy_7=findViewById(R.id.mainpage_diy7);
        diy_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,MessageActivity.class);
                startActivity(intent);
            }
        });

        //树洞监听事件
        diy_8=findViewById(R.id.mainpage_diy8);
        diy_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,EducationActivity.class);
                startActivity(intent);
            }
        });


     }

     //  构造图标列表
     public List<Map<String,Object>> getList(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;

        String[] titles = new String[]{"校园时讯","安全界面","生活贴士","我的健康","愿望清单","个人求助","寄语板","教育平台"};
        Integer[] images = {R.drawable.ic_gridschool,R.drawable.ic_gridsecurity,R.drawable.ic_gridlife,R.drawable.ic_gridhealth,R.drawable.ic_gridwish,R.drawable.ic_gridhelp,R.drawable.ic_gridboard,R.drawable.ic_grideducation};

      for (int i = 0;i<images.length;i++)
      {
            map = new HashMap<String,Object>();
            map.put("image",images[i]);
            map.put("title",titles[i]);
            list.add(map);
      }
      return list;
     }

     //  定时器
     public class MyTimetask extends TimerTask {

         @Override
         public void run() {
             StartActivity.this.runOnUiThread(new Runnable() {
                 @Override
                 public void run() {

                     if (vpager_two.getCurrentItem()==0)
                         vpager_two.setCurrentItem(1);
                     else if(vpager_two.getCurrentItem()==1)
                         vpager_two.setCurrentItem(2);
                     else if(vpager_two.getCurrentItem()==2)
                         vpager_two.setCurrentItem(3);
                     else if(vpager_two.getCurrentItem()==3)
                         vpager_two.setCurrentItem(0);
                 }
             });
         }
     }

}
