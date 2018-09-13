package com.seuchild.smallseedling.startDirect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.StartActivity;
import com.seuchild.smallseedling.login.LogIn;

import java.util.Timer;
import java.util.TimerTask;

public class InitialActivity extends Activity {

    private Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        final Intent intent = new Intent(InitialActivity.this,LogIn.class);
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        timer.schedule(task,2000);

        skip =findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();//终止定时器工作。防止跳转两次
                finish();
                startActivity(intent);
            }
        });
    }
}
