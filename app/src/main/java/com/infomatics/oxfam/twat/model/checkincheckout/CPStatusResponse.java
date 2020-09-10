package com.infomatics.oxfam.twat.model.checkincheckout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;

public class CPStatusResponse extends BaseResponse {

    @SerializedName("datalist")
    @Expose
    private Datalist datalist;

    public Datalist getDatalist() {
        return datalist;
    }

    public void setDatalist(Datalist datalist) {
        this.datalist = datalist;
    }
}
