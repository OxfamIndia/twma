package com.infomatics.oxfam.twat.model.contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactModel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("posting")
    @Expose
    private String posting;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("dutyTimings")
    @Expose
    private String dutyTimings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDutyTimings() {
        return dutyTimings;
    }

    public void setDutyTimings(String dutyTimings) {
        this.dutyTimings = dutyTimings;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }
}
