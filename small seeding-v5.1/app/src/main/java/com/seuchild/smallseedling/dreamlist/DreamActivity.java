package com.seuchild.smallseedling.dreamlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.SmallSeedlingApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DreamActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView back;
    private Button wish_btn;
    private String dream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);

        //关联TabLayout与ViewPager并为ViewPager设置自定义适配器

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText = findViewById(R.id.editText);
        wish_btn=findViewById(R.id.button_dream);


        wish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dream=editText.getText().toString();
                System.out.println(dream+"hahahha!!@@!@!@");

                if(dream.equals("")){
                    Toast toast = Toast.makeText(DreamActivity.this,"许愿不能为空",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(DreamActivity.this,"许愿成功",Toast.LENGTH_LONG);
                    toast.show();
                    post.start();
                    editText.setText("");
                }
            }

            Thread post = new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/dreamlist";
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("dream",dream)
                            .add("state","false").build();
                    Request request = new Request.Builder().url(url).post(formBody).build();
                    try {
                        Response response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });

        });

    }


}
