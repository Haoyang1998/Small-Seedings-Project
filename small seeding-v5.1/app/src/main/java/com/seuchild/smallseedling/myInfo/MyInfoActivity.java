package com.seuchild.smallseedling.myInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.StartActivity;
import com.seuchild.smallseedling.parent.ParentActivity;
import com.seuchild.smallseedling.square.SquareActivity;


/**
 * Author: created by Ginger on 2018/8/28 13 30
 * E-Mail: 1020072294@qq.com
 */
public class MyInfoActivity extends AppCompatActivity {
    private Context myinfo_Context;
    private BottomNavigationView myinfo_bnv;


    //  刷新界面方法 未实现
    public void refresh(){
        //super.onCreate(null);
    }

    // 保存关于布局的一些数据
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        myinfo_Context = MyInfoActivity.this;

        myinfo_bnv = findViewById(R.id.myinfopage_bnv);
        //  设置监听器 底部导航栏
        //myinfo_bnv.performClick();
        myinfo_bnv.getMenu().getItem(3).setChecked(true);
        myinfo_bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    //  界面跳转方法 本界面刷新 新界面就新建一个
                    @Override
                    public boolean onNavigationItemSelected(@Nullable MenuItem item) {
                        int num = item.getOrder();
                        if(num ==1)
                            startActivity(new Intent(MyInfoActivity.this,StartActivity.class));
                        else if(num == 2)
                            startActivity(new Intent(MyInfoActivity.this,SquareActivity.class));
                        else if(num == 3)
                            startActivity(new Intent(MyInfoActivity.this,ParentActivity.class));
                        else
                            return false;

                        //  异常退出情况下保存当前Activity状态信息
                        //  onSaveInstanceState(savedInstanceState);
                        //  销毁当前页
                        finish();
                        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
        );
        //myinfo_bnv.setSelectedItemId(R.id.bnv_myinfo);
    }


}
