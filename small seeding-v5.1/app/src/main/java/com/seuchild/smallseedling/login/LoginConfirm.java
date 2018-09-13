package com.seuchild.smallseedling.login;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginConfirm extends  Thread{
    String url;
    String zhstring;
    Integer size;
    private Example example;
    List<Example> examples;
    Boolean flag=false;
    public LoginConfirm(String url,String zhstring) {
        this.url=url;
        this.zhstring=zhstring;
    }
    public Boolean getFlag() {
        return flag;
    }

    @Override
    public void run(){
        try {
            URL httpUrl=new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        Response response=null;
        try {
            response=client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Gson gson=new Gson();
        try {
            examples=gson.fromJson(response.body().string(),new TypeToken<List<Example>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        size=examples.size();
        System.out.println(size);

        for(int m=0;m<size;m++)
        {
            String yy=examples.get(m).getZhanghao();
            System.out.println(yy);
            if(zhstring.equals(yy)) {
                flag = true;
                break;
            }
        }
        System.out.println(flag);
    }
}
