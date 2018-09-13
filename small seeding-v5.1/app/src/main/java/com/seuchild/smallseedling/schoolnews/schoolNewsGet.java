package com.seuchild.smallseedling.schoolnews;


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

public class schoolNewsGet extends Thread {

    private String url = "http://"+ SmallSeedlingApplication.server_ip+":"+SmallSeedlingApplication.server_port+"/schoolNews";
    private EXAMPLE example;
    private List<EXAMPLE> examples = new ArrayList<>();

    public List<EXAMPLE> getExamples() {
        return examples;
    }

    @Override
    public void run() {
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = SmallSeedlingApplication.client.newCall(request).execute();
            // System.out.println(" HERE  !!!!!!!!!!!  ");
            Gson gson = new Gson();
            examples = gson.fromJson(response.body().string(),new TypeToken<List<EXAMPLE>>(){}.getType());
            // System.out.println(examples.get(1).getNewsTitle());
            //System.out.println(examples.size()+" !!!!!!!!!!!!!!! ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


