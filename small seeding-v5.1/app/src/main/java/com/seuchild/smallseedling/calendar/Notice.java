package com.seuchild.smallseedling.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("content")
    @Expose
    private Object content;
    @SerializedName("noticeid")
    @Expose
    private Object noticeid;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(Object noticeid) {
        this.noticeid = noticeid;
    }
}
