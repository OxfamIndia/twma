package com.infomatics.oxfam.twat.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hundred {
    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("walker")
    @Expose
    private Walker walker;

    @SerializedName("totalWalkers")
    @Expose
    private Integer totalWalkers;

    @SerializedName("totalTeams")
    @Expose
    private Integer totalTeams;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Walker getWalker() {
        return walker;
    }

    public void setWalker(Walker walker) {
        this.walker = walker;
    }

    public Integer getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(Integer totalTeams) {
        this.totalTeams = totalTeams;
    }

    public Integer getTotalWalkers() {
        return totalWalkers;
    }

    public void setTotalWalkers(Integer totalWalkers) {
        this.totalWalkers = totalWalkers;
    }
}
