package com.seuchild.smallseedling.dreamlist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EXAMPLE {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dream")
    @Expose
    private String dream;
    @SerializedName("state")
    @Expose
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDream() {
        return dream;
    }

    public void setDream(String dream) {
        this.dream = dream;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}