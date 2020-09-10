package com.infomatics.oxfam.twat.model.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("datalist")
    @Expose
    private List<Datalist> datalist = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datalist> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<Datalist> datalist) {
        this.datalist = datalist;
    }
}
