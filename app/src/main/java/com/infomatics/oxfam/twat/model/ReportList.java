package com.infomatics.oxfam.twat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportList {
    @SerializedName("Team Title")
    @Expose
    private String teamTitle;
    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("TIME ELAPSED")
    @Expose
    private String tIMEELAPSED;
    @SerializedName("time_in")
    @Expose
    private String timeIn;
    @SerializedName("retirements")
    @Expose
    private String retirements;
    @SerializedName("Checkpoint")
    @Expose
    private String checkpoint;

    public String getTeamTitle() {
        return teamTitle;
    }

    public void setTeamTitle(String teamTitle) {
        this.teamTitle = teamTitle;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTIMEELAPSED() {
        return tIMEELAPSED;
    }

    public void setTIMEELAPSED(String tIMEELAPSED) {
        this.tIMEELAPSED = tIMEELAPSED;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getRetirements() {
        return retirements;
    }

    public void setRetirements(String retirements) {
        this.retirements = retirements;
    }

    public String getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }
}
