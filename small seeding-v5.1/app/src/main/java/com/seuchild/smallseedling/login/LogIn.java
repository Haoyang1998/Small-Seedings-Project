package com.seuchild.smallseedling.login;

import android.content.Intent;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.SmallSeedlingApplication;
import com.seuchild.smallseedling.StartActivity;

public class LogIn extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private Button mBtnLogin;
    private TextInputEditText zhanghao;
    private TextInputEditText mima;
    public  LoginConfirm t3;
    public LoginThread t2;
    private String zhstring, mmstring;
    public boolean flagconfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        button = findViewById(R.id.but_1);
        textView = findViewById(R.id.tv_su);
        mBtnLogin = findViewById(R.id. but_1);
    }
    public void onClick_login(View view){
        // String url = "http://120.79.207.84:8022/miao/zhanghao/";
        String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/miao/zhanghao/";
        //String urlconfirm="http://120.79.207.84:8022/miao";
        String urlconfirm = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/miao";
        System.out.println("zhuxiancheng!!!!!!!!1111");
        zhanghao = view.getRootView().findViewById(R.id.tel_user);
        mima = view.getRootView().findViewById(R.id.tel_key);
        zhstring=zhanghao.getText().toString();
        mmstring=mima.getText().toString();
        System.out.println(mmstring);
        if((zhstring.equals(""))||(mmstring.equals("")))
        {
            Toast.makeText(LogIn.this, "账号密码不能为空", Toast.LENGTH_LONG).show();
        }
        else {
               t3 = new LoginConfirm(urlconfirm, zhstring);
              t3.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
              flagconfirm=t3.getFlag();
              System.out.println(flagconfirm);
            if(flagconfirm) {
            System.out.println(zhstring + "8888888888888888888888");
            System.out.println(zhstring);
            t2 = new LoginThread(url, zhstring);
            t2.start();
            String flag = "null";
            System.out.println(flag + "FFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = t2.getMm();
            System.out.println(flag + "FFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
            if (flag.equals(mmstring)) {
                Toast.makeText(LogIn.this, "登陆成功", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(LogIn.this, StartActivity.class));
            } else {
                Toast.makeText(LogIn.this, "密码错误", Toast.LENGTH_LONG).show();
            }
            }
            else{
                Toast.makeText(LogIn.this, "账号不存在", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void onClick_register(View view){
        finish();
        startActivity(new Intent(LogIn.this, Register.class));
    }
}