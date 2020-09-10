package com.infomatics.oxfam.twat.model.checkincheckout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeCPStatusRequest {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("remark")
    @Expose
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
