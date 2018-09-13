package com.seuchild.smallseedling.personalass;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.seuchild.smallseedling.R;


public class PersonalASSActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String TAG=".PersonalASSActivity";

    private String title,type,identity,content;
    private List<Integer> taskid = new ArrayList<>();

    //初始化输入框
    private TextInputLayout mTextInputLayoutT,mTextInputLayoutC;
    //初始化toolbar
    private Toolbar mToolbar;

    //初始化button
    private Button btn;

    //初始化spinner
    private Spinner sp,spk;
    private static int pos = 0;

    private String []help_type_arr = {"食品","衣服","文具","寻找","教育","出行","日用品","娱乐用品"};
    private String []person_type_arr ={"儿童","家长","教师","志愿者","政府","企业"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_ass);
        initArr();
        initToolbar();
        initSpinner();
        btn=findViewById(R.id.personal_ass_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initmTextInputEditText();
                if(sendDate(title,type,identity,content,taskid.get(pos)))
                    Toast.makeText(v.getContext(), "任务"+taskid.get(pos)+"添加成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(), "任务"+taskid.get(pos)+"添加失败", Toast.LENGTH_SHORT).show();
                pos++;
            }
        });
    }

    private void initArr() {
        for(int i = 0;i<100;i++){
            taskid.add((180910001+i));
        }
    }

    private void initmTextInputEditText() {
        mTextInputLayoutC = findViewById(R.id.personal_ass_til_con);
        content = mTextInputLayoutC.getEditText().getText().toString();
        mTextInputLayoutT = findViewById(R.id.personal_ass_til_title);
        title = mTextInputLayoutT.getEditText().getText().toString();
    }

    private void initSpinner() {
        sp = findViewById(R.id.personal_ass_sp);
        ArrayAdapter<String> ps_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,help_type_arr);
        sp.setAdapter(ps_adapter);
        sp.setOnItemSelectedListener(this);
        type = help_type_arr[sp.getSelectedItemPosition()];

        spk = findViewById(R.id.personal_ass_sp_kid);
        ArrayAdapter<String> psk_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,person_type_arr);
        spk.setAdapter(psk_adapter);
        spk.setOnItemSelectedListener(this);
        identity = person_type_arr[spk.getSelectedItemPosition()];

    }

    //初始化Toolbar
    private void initToolbar() {
        mToolbar = findViewById(R.id.personal_ass_tb);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personal_ass_toolbar,menu);
        return true;
    }

    //菜单选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.personal_ass_mine:
                Intent i = new Intent();
                i.setClass(PersonalASSActivity.this,MyASSActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean sendDate(String title,String type,String identity,String content,Integer taskid){
        PersonalASSActivityThread p_thread = new PersonalASSActivityThread();
        p_thread.sendDate(title,type,identity,content,taskid);
        p_thread.start();
        if(p_thread.getCount() == 1)
            return false;
        else
            return true;
    }

}
