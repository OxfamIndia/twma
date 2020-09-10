package com.infomatics.oxfam.twat.model.checkincheckout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;

import java.util.ArrayList;

public class CheckInCheckOutResponse extends BaseResponse {

    @SerializedName("datalist")
    @Expose
    private ArrayList<CheckInCheckOutBean> datalist;

    public ArrayList<CheckInCheckOutBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<CheckInCheckOutBean> datalist) {
        this.datalist = datalist;
    }
}
