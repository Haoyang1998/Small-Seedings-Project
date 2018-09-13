package com.seuchild.smallseedling.login;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("zhanghao")
    @Expose
    private String zhanghao;
    @SerializedName("mima")
    @Expose
    private String mima;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }
}
