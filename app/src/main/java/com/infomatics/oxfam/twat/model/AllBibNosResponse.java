package com.infomatics.oxfam.twat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllBibNosResponse extends BaseResponse {

    @SerializedName("datalist")
    @Expose
    private ArrayList<Integer> datalist;

    public ArrayList<Integer> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<Integer> datalist) {
        this.datalist = datalist;
    }
}
