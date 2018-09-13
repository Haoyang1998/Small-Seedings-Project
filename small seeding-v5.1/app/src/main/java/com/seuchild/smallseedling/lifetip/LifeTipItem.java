package com.seuchild.smallseedling.lifetip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 数据类
 * Author: created by Ginger on 2018/9/4 22 30
 * E-Mail: 1020072294@qq.com
 */

public class LifeTipItem {

    @SerializedName("publishTime")
    @Expose
    private String publishTime;

    @SerializedName("newsId")
    @Expose
    private String newsId;

    @SerializedName("newsImg")
    @Expose
    private String newsImg;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("title")
    @Expose
    private String title;


    public String getPublishTime() {
        return publishTime;
    }

    public String getNewsId() {
        return newsId;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public String getSource() {
        return source;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

}
