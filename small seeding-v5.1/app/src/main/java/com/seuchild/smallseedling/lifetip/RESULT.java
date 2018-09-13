package com.seuchild.smallseedling.lifetip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: created by Ginger on 2018/9/13 00 38
 * E-Mail: 1020072294@qq.com
 */
public class RESULT {
    @SerializedName("imgUrl")
    @Expose
    private List<String> imgUrl = null;
    @SerializedName("editor")
    @Expose
    private String editor;
    @SerializedName("publishTime")
    @Expose
    private String publishTime;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;

    /**
     * No args constructor for use in serialization
     *
     */
    public RESULT() {
    }

    /**
     *
     * @param content
     * @param imgUrl
     * @param title
     * @param category
     * @param editor
     * @param source
     * @param publishTime
     */
    public RESULT(List<String> imgUrl, String editor, String publishTime, String source, String category, String title, String content) {
        super();
        this.imgUrl = imgUrl;
        this.editor = editor;
        this.publishTime = publishTime;
        this.source = source;
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
