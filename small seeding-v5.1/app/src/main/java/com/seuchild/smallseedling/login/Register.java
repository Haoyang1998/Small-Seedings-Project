package com.seuchild.smallseedling.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.SmallSeedlingApplication;
import com.seuchild.smallseedling.StartActivity;

public class Register extends AppCompatActivity {

    private Button button;
    private TextInputLayout tilName, tilPassword;
    private TextInputEditText birth;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextInputEditText zhanghao;
    private TextInputEditText mima;
    private Calendar calendar;
    private String zhstring, mmstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button=findViewById(R.id.log_out);
        birth=findViewById(R.id.tiet_birth);
        birth.setInputType(InputType.TYPE_NULL);
        calendar= Calendar.getInstance();
//        tilName = findViewById(R.id.til_name);
//        zhanghao = tilName.getEditText().toString();
//        tilPassword=findViewById(R.id.til_key);
//        mima=tilPassword.getEditText().toString();

        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Register.this,R.style.MyDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                birth.setText(new StringBuilder()
                                        .append(mYear)
                                        .append("-")
                                        .append((mMonth + 1) < 10 ? "0"
                                                + (mMonth + 1) : (mMonth + 1))
                                        .append("-")
                                        .append((mDay < 10) ? "0" + mDay : mDay));
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    public void onClick_register(View view){

        zhanghao=view.getRootView().findViewById(R.id.tie_name);;
        mima = view.getRootView().findViewById(R.id.key_a);
        zhstring=zhanghao.getText().toString();
        mmstring=mima.getText().toString();
      //  String url = "http://120.79.207.84:8011/miao";
        String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/miao";
        HttpThread t1 = new HttpThread(url, zhstring, mmstring);
        t1.start();
        finish();
        startActivity(new Intent(Register.this,LogIn.class));
    }
    public void onClick_register_login(View view){
        finish();
        startActivity(new Intent(Register.this,LogIn.class));
    }
}
