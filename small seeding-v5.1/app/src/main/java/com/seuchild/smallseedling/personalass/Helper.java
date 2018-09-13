package com.seuchild.smallseedling.personalass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Helper {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("identity")
    @Expose
    private String identity;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("taskid")
    @Expose
    private Integer taskid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
