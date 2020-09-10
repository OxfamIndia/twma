package com.infomatics.oxfam.twat.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datalist {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("teamId")
    @Expose
    private Object teamId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("teamTitle")
    @Expose
    private String teamTitle;
    @SerializedName("leaderId")
    @Expose
    private Integer leaderId;
    @SerializedName("walker")
    @Expose
    private List<Walker> walker = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getTeamId() {
        return teamId;
    }

    public void setTeamId(Object teamId) {
        this.teamId = teamId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTeamTitle() {
        return teamTitle;
    }

    public void setTeamTitle(String teamTitle) {
        this.teamTitle = teamTitle;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public List<Walker> getWalker() {
        return walker;
    }

    public void setWalker(List<Walker> walker) {
        this.walker = walker;
    }
}
