package com.infomatics.oxfam.twat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CPEntryResponse extends BaseResponse {
    @SerializedName("datalist")
    @Expose
    private ArrayList<CpEntryData> datalist;

    public ArrayList<CpEntryData> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<CpEntryData> datalist) {
        this.datalist = datalist;
    }
}
