package com.seuchild.smallseedling.message;

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

public class MessageGet extends Thread{
//    private final OkHttpClient client = new OkHttpClient.Builder().
//            connectTimeout(30, TimeUnit.SECONDS).
//            readTimeout(30, TimeUnit.SECONDS).
//            build();
    private String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/message";
    private Example example;
    private List<Example> examples = new ArrayList<>();

    @Override
    public void run() {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = SmallSeedlingApplication.client.newCall(request).execute();
            Gson gson = new Gson();
            examples = gson.fromJson(response.body().string(),new TypeToken<List<Example>>(){}.getType());
            //System.out.println(examples.get(1).getMessageText());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Example> getExamples() {
        return examples;
    }
}