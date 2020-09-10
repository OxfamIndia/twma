package com.infomatics.oxfam.twat.model.dutyregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;

import java.util.List;

public class DutyRegisterResponse extends BaseResponse {
    @SerializedName("datalist")
    @Expose
    private List<Datalist> datalist = null;

    public List<Datalist> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<Datalist> datalist) {
        this.datalist = datalist;
    }
}
