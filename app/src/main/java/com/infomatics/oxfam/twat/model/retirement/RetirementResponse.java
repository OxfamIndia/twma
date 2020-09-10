package com.infomatics.oxfam.twat.model.retirement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;

import java.util.List;

public class RetirementResponse extends BaseResponse {

    @SerializedName("datalist")
    @Expose
    private List<Datalist> dataList;

    public List<Datalist> getDataList() {
        return dataList;
    }

    public void setDataList(List<Datalist> dataList) {
        this.dataList = dataList;
    }
}
