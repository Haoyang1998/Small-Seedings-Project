package com.seuchild.smallseedling.education;

/**
 * Author: created by Ginger on 2018/9/10 01 03
 * E-Mail: 1020072294@qq.com
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("video_name")
    @Expose
    private String videoName;

    @SerializedName("video_category")
    @Expose
    private String videoCategory;

    @SerializedName("video_source")
    @Expose
    private String videoSource;

    @SerializedName("video_time")
    @Expose
    private String videoTime;

    @SerializedName("video_image_url")
    @Expose
    private String videoImageUrl;

    @SerializedName("video_url")
    @Expose
    private String videoUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public Video() {
    }

    /**
     *
     * @param videoTime
     * @param videoSource
     * @param videoCategory
     * @param videoName
     * @param videoImageUrl
     */
    public Video(String videoName, String videoCategory, String videoSource, String videoTime, String videoImageUrl) {
        super();
        this.videoName = videoName;
        this.videoCategory = videoCategory;
        this.videoSource = videoSource;
        this.videoTime = videoTime;
        this.videoImageUrl = videoImageUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(String videoCategory) {
        this.videoCategory = videoCategory;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getVideoImageUrl() {
        return videoImageUrl;
    }

    public void setVideoImageUrl(String videoImageUrl) {
        this.videoImageUrl = videoImageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}