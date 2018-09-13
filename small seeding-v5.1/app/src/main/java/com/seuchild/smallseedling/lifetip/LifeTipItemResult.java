package com.seuchild.smallseedling.lifetip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: created by Ginger on 2018/9/8 21 30
 * E-Mail: 1020072294@qq.com
 */
public class LifeTipItemResult {

    @SerializedName("ERRORCODE")
    @Expose
    private String eRRORCODE;

    @SerializedName("RESULT")
    @Expose
    private RESULT rESULT;

    public RESULT getRESULT() {
        return rESULT;
    }

    public class RESULT {

        @SerializedName("newsList")
        @Expose
        private List<LifeTipItem> newsList = null;

        @SerializedName("page")
        @Expose
        private Integer page;

        public List<LifeTipItem> getNewsList() {
            return newsList;
        }
    }

}
