package com.infomatics.oxfam.twat.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CPDashboardResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("is_logged_in")
    @Expose
    private String isLoggedIn;
    @SerializedName("hundred")
    @Expose
    private Hundred hundred;
    @SerializedName("fifty")
    @Expose
    private Hundred fifty;
    @SerializedName("teamCount")
    @Expose
    private int teamCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public String getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(String isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public Hundred getHundred() {
        return hundred;
    }

    public void setHundred(Hundred hundred) {
        this.hundred = hundred;
    }

    public Hundred getFifty() {
        return fifty;
    }

    public void setFifty(Hundred fifty) {
        this.fifty = fifty;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }
}
