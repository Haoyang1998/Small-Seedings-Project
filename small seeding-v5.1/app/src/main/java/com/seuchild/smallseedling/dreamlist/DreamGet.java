package com.seuchild.smallseedling.dreamlist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seuchild.smallseedling.SmallSeedlingApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DreamGet extends Thread {
//    private final OkHttpClient client = new OkHttpClient.Builder().
//            connectTimeout(30, TimeUnit.SECONDS).
//            readTimeout(30, TimeUnit.SECONDS).
//            build();
    private EXAMPLE example;
    private List<EXAMPLE> examples = new ArrayList<>();
    private String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/dreamlist";

    public List<EXAMPLE> getExamples() {
        return examples;
    }

    @Override
    public void run(){
        System.out.println(url+"      !!!!   ");
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = SmallSeedlingApplication.client.newCall(request).execute();
            Gson gson = new Gson();
            examples = gson.fromJson(response.body().string(),new TypeToken<List<EXAMPLE>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

