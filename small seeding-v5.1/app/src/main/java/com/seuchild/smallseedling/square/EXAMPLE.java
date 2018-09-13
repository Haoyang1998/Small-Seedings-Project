package com.seuchild.smallseedling.square;

/**
 * create by cz
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EXAMPLE {

    @SerializedName("m_name")
    @Expose
    private String mName;
    @SerializedName("m_content")
    @Expose
    private String mContent;
    @SerializedName("m_url")
    @Expose
    private String mUrl;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public String getMContent() {
        return mContent;
    }

    public void setMContent(String mContent) {
        this.mContent = mContent;
    }

    public String getMUrl() {
        return mUrl;
    }

    public void setMUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
