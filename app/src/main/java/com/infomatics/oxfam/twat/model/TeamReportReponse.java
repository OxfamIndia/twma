package com.infomatics.oxfam.twat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamReportReponse extends BaseResponse {
    @SerializedName("datalist")
    @Expose
    private List<ReportList> datalist;

    public List<ReportList> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<ReportList> datalist) {
        this.datalist = datalist;
    }
}
