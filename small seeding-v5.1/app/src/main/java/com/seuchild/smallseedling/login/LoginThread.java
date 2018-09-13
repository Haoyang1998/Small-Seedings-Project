package com.seuchild.smallseedling.login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginThread extends Thread {
    String url;
    String fuck;
    private Example example;
    List<Example> examples;
    public  String mm;

    public LoginThread(String url, String fuck) {
        this.url=url;
        this.fuck=fuck;
    }
    public String getMm() {
        return mm;
    }
    @Override
    public void run(){
        url=url+fuck;
        try {
            URL httpUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(url+"!!!!!!!!!!!!@@@@@@@@@");
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        Response response= null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson=new Gson();
        try {
            examples =gson.fromJson(response.body().string(),new TypeToken<List<Example>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mm=examples.get(0).getMima();
        System.out.println(mm+"MMMMMMMMMMM");
    }
}
