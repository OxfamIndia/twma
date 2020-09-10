package com.infomatics.oxfam.twat.model.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.team.Walker;
import com.infomatics.oxfam.twat.util.DataConverter;

import java.util.List;

@Entity(tableName = "team_table")
public class TeamEntity {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("teamId")
    @Expose
    @ColumnInfo(name = "teamId")
    private int teamId;

    @ColumnInfo(name ="image")
    @SerializedName("image")
    @Expose
    private String image;

    @ColumnInfo(name = "teamTitle")
    @SerializedName("teamTitle")
    @Expose
    private String teamTitle;

    @ColumnInfo(name="type")
    @SerializedName("type")
    @Expose
    private int type;

    @ColumnInfo(name="leaderId")
    @SerializedName("leaderId")
    @Expose
    private int leaderId;

    @ColumnInfo(name = "walker")
    @TypeConverters(DataConverter.class)
    @SerializedName("walker")
    @Expose
    private List<Walker> walker;

    @SerializedName("isActivated")
    @Expose
    private int isActivated;

    @SerializedName("lastCP")
    @Expose
    private int lastCP;

    @SerializedName("lastCPTime")
    @Expose
    private String lastCPTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
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

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }
    public List<Walker> getWalker() {
        return walker;
    }

    public void setWalker(List<Walker> walker) {
        this.walker = walker;
    }

    @Override
    public String toString() {
        return this.teamTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(int isActivated) {
        this.isActivated = isActivated;
    }

    public int getLastCP() {
        return lastCP;
    }

    public void setLastCP(int lastCP) {
        this.lastCP = lastCP;
    }

    public String getLastCPTime() {
        return lastCPTime;
    }

    public void setLastCPTime(String lastCPTime) {
        this.lastCPTime = lastCPTime;
    }
}
