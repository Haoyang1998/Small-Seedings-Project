package com.seuchild.smallseedling.schoolnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EXAMPLE {

    @SerializedName("newsTitle")
    @Expose
    private String newsTitle;
    @SerializedName("newsType")
    @Expose
    private String newsType;
    @SerializedName("newsTime")
    @Expose
    private String newsTime;
    @SerializedName("newsPalce")
    @Expose
    private String newsPalce;
    @SerializedName("introduce")
    @Expose
    private String introduce;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("weburl")
    @Expose
    private String weburl;

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsPalce() {
        return newsPalce;
    }

    public void setNewsPalce(String newsPalce) {
        this.newsPalce = newsPalce;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}

