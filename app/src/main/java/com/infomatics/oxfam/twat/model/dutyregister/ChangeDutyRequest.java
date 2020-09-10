package com.infomatics.oxfam.twat.model.dutyregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeDutyRequest {
    @SerializedName("userid")
    @Expose
    private String userid;

    @SerializedName("cpid")
    @Expose
    private String cpid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }
}
