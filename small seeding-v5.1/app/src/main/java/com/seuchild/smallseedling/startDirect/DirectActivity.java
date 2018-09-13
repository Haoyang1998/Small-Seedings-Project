package com.seuchild.smallseedling.startDirect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class DirectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_direct);
        date();
    }
    private void date() {
        SharedPreferences shared=getSharedPreferences("is", MODE_PRIVATE);
        boolean isfer=shared.getBoolean("isfer", true);
        SharedPreferences.Editor editor=shared.edit();
        if(isfer){
            //第一次进入跳转
            // PreviewActivity
            Intent in=new Intent(DirectActivity.this,PreviewActivity.class);
            startActivity(in);
            finish();
            editor.putBoolean("isfer", false);
            editor.commit();
        }else{
            //第二次进入跳转
            Intent in=new Intent(DirectActivity.this,InitialActivity.class);
            startActivity(in);
            finish();

        }
    }
}
