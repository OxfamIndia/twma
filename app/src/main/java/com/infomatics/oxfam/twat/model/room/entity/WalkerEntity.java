package com.infomatics.oxfam.twat.model.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "walker_table")
public class WalkerEntity {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "walker_name")
    private String walkerName;

    @ColumnInfo(name = "bibNo")
    private String bibNo;

    @ColumnInfo(name="walkerId")
    private int walkerId;

    @ColumnInfo(name = "mobile")
    private String mobile;

    @ColumnInfo(name="running")
    private String running;

    @ColumnInfo(name="team_name")
    private String teamName;

    @ColumnInfo(name ="type")
    private int type;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getWalkerName() {
        return walkerName;
    }

    public void setWalkerName(String walkerName) {
        this.walkerName = walkerName;
    }

    public String getBibNo() {
        return bibNo;
    }

    public void setBibNo(String bibNo) {
        this.bibNo = bibNo;
    }

    public int getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(int walkerId) {
        this.walkerId = walkerId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return walkerName;
    }
}
