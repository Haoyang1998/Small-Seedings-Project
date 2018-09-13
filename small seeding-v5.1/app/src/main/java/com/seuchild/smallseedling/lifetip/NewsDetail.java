package com.seuchild.smallseedling.lifetip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author: created by Ginger on 2018/9/13 00 37
 * E-Mail: 1020072294@qq.com
 */
public class NewsDetail {
    @SerializedName("ERRORCODE")
    @Expose
    private String eRRORCODE;
    @SerializedName("RESULT")
    @Expose
    private RESULT rESULT;

    /**
     * No args constructor for use in serialization
     *
     */
    public NewsDetail() {
    }

    /**
     *
     * @param eRRORCODE
     * @param rESULT
     */
    public NewsDetail(String eRRORCODE, RESULT rESULT) {
        super();
        this.eRRORCODE = eRRORCODE;
        this.rESULT = rESULT;
    }

    public String getERRORCODE() {
        return eRRORCODE;
    }

    public void setERRORCODE(String eRRORCODE) {
        this.eRRORCODE = eRRORCODE;
    }

    public RESULT getRESULT() {
        return rESULT;
    }

    public void setRESULT(RESULT rESULT) {
        this.rESULT = rESULT;
    }
}
