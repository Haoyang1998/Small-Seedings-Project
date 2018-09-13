package com.seuchild.smallseedling.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("messageText")
    @Expose
    private String messageText;
    @SerializedName("person")
    @Expose
    private String person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }


}
