package com.seuchild.smallseedling;

import android.app.Application;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Author: created by Ginger on 2018/9/8 20 31
 * E-Mail: 1020072294@qq.com
 */
public class SmallSeedlingApplication extends Application {
    // 不带cache和authenticator
    public final static OkHttpClient client = new OkHttpClient.Builder().
            readTimeout(30, TimeUnit.SECONDS).
            build();

    public final static OkHttpClient clientWith60sTimeout = client.newBuilder().readTimeout(60, TimeUnit.SECONDS).build();

    // 应用服务器IP
    public final static String server_ip = "120.79.207.84";
    //public final static String server_database = "hemiao";
    public final static String server_port = "8022";

}
