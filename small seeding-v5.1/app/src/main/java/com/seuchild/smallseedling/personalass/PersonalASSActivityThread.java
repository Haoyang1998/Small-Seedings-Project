package com.seuchild.smallseedling.personalass;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import com.seuchild.smallseedling.SmallSeedlingApplication;

import static com.seuchild.smallseedling.SmallSeedlingApplication.server_ip;
import static com.seuchild.smallseedling.SmallSeedlingApplication.server_port;

public class PersonalASSActivityThread extends Thread {
    private String url = "http://"+server_ip+":"+server_port+"/help/post";
    //返回JSON列表单个结果
    private String title,type,identity,content;
    private Integer taskid;

    private int count;
    @Override
    public void run() {

        //构造REQUESTBODY
        FormBody requestBodyBuilder = new FormBody.Builder()
                .add("title",title)
                .add("type",type)
                .add("identity",identity)
                .add("content",content)
                .add("taskid", String.valueOf(taskid))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBodyBuilder)
                .build();

        // 异步加载 回调方法
        Call call = SmallSeedlingApplication.client.newCall(request);
        call.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("未完成求助 Fail");
                setCount(0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                System.out.println(url+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                setCount(1);
            }
        });

    }

    public void sendDate(String title,String type,String identity,String content,Integer taskid){
        this.content=content;
        this.identity=identity;
        this.taskid=taskid;
        this.title=title;
        this.type=type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
