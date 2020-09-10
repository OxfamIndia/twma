package com.infomatics.oxfam.twat.model.dutyregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;

import java.util.List;

public class DutyResponse extends BaseResponse {

    @SerializedName("datalist")
    @Expose
    private List<DutyList> datalist;

    public List<DutyList> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DutyList> datalist) {
        this.datalist = datalist;
    }
}
