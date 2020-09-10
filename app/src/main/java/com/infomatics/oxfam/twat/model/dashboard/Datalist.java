package com.infomatics.oxfam.twat.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datalist {
    @SerializedName("totalTeam")
    @Expose
    private Integer totalTeam;
    @SerializedName("incidents")
    @Expose
    private Integer incidents;
    @SerializedName("lost_and_found")
    @Expose
    private Integer lostAndFound;
    @SerializedName("corporates")
    @Expose
    private Integer corporates;
    @SerializedName("males")
    @Expose
    private Integer males;
    @SerializedName("females")
    @Expose
    private Integer females;
    @SerializedName("veteran")
    @Expose
    private Integer veteran;
    @SerializedName("walkersStarted")
    @Expose
    private Integer walkersStarted;
    @SerializedName("checkPointData")
    @Expose
    private List<CheckPointData> checkPointData = null;
    @SerializedName("teamStarted")
    @Expose
    private Integer teamStarted;
    @SerializedName("fullTeam")
    @Expose
    private Integer fullTeam;
    @SerializedName("incompleteTeam")
    @Expose
    private Integer incompleteTeam;

    public Integer getTotalTeam() {
        return totalTeam;
    }

    public void setTotalTeam(Integer totalTeam) {
        this.totalTeam = totalTeam;
    }

    public Integer getIncidents() {
        return incidents;
    }

    public void setIncidents(Integer incidents) {
        this.incidents = incidents;
    }

    public Integer getLostAndFound() {
        return lostAndFound;
    }

    public void setLostAndFound(Integer lostAndFound) {
        this.lostAndFound = lostAndFound;
    }

    public Integer getCorporates() {
        return corporates;
    }

    public void setCorporates(Integer corporates) {
        this.corporates = corporates;
    }

    public Integer getMales() {
        return males;
    }

    public void setMales(Integer males) {
        this.males = males;
    }

    public Integer getFemales() {
        return females;
    }

    public void setFemales(Integer females) {
        this.females = females;
    }

    public Integer getVeteran() {
        return veteran;
    }

    public void setVeteran(Integer veteran) {
        this.veteran = veteran;
    }

    public Integer getWalkersStarted() {
        return walkersStarted;
    }

    public void setWalkersStarted(Integer walkersStarted) {
        this.walkersStarted = walkersStarted;
    }

    public List<CheckPointData> getCheckPointData() {
        return checkPointData;
    }

    public void setCheckPointData(List<CheckPointData> checkPointData) {
        this.checkPointData = checkPointData;
    }

    public Integer getTeamStarted() {
        return teamStarted;
    }

    public void setTeamStarted(Integer teamStarted) {
        this.teamStarted = teamStarted;
    }

    public Integer getFullTeam() {
        return fullTeam;
    }

    public void setFullTeam(Integer fullTeam) {
        this.fullTeam = fullTeam;
    }

    public Integer getIncompleteTeam() {
        return incompleteTeam;
    }

    public void setIncompleteTeam(Integer incompleteTeam) {
        this.incompleteTeam = incompleteTeam;
    }
}
