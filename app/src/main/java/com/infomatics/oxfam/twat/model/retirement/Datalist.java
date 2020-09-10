package com.infomatics.oxfam.twat.model.retirement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datalist {

    @SerializedName("bib_no")
    @Expose
    private String bibNo;

    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    @SerializedName("cp_name")
    @Expose
    private String cpName;

    @SerializedName("in_time")
    @Expose
    private String inTime;

    public String getBibNo() {
        return bibNo;
    }

    public void setBibNo(String bibNo) {
        this.bibNo = bibNo;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }
}
