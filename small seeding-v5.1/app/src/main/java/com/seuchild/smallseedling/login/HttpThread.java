package com.seuchild.smallseedling.login;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpThread extends Thread {
    String url;
    String zhanghao;
    String mima;
    public HttpThread(String url, String zhanghao, String mima) {
        this.url=url;
        this.zhanghao=zhanghao;
        this.mima=mima;
    }

    @Override
    public void run(){
        try {
            url = url+"?zhanghao="+zhanghao+"&mima="+mima;
            URL  httpUrl = new URL(url);
            System.out.println(url);
            HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.connect();
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb=new StringBuffer();
            String str;
            while((str=reader.readLine())!=null){
                sb.append(str);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
