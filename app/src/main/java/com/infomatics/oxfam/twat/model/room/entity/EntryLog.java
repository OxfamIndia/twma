package com.infomatics.oxfam.twat.model.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "entry_log")
public class EntryLog {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "tag_id")
    private String tagId;

    @ColumnInfo(name = "bibNo")
    private String bibNo;

    @ColumnInfo(name = "check_in_time")
    private String checkInTime;

    @ColumnInfo(name = "check_in_millis")
    private String checkInMillis;

    @ColumnInfo(name = "check_out_time")
    private String checkOutTime;

    @ColumnInfo(name="check_out_millis")
    private String checkOutMillis;

    @ColumnInfo(name = "hasRetired")
    private int hasRetired;

    @ColumnInfo(name = "hasCheckedOut")
    private int hasCheckedOut;

    @ColumnInfo(name="activity_time")
    private String activityTime;

    @ColumnInfo(name="is_synced")
    private int isSynced;

    @ColumnInfo(name="checkpoint_id")
    private int checkpoint_id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public int getHasRetired() {
        return hasRetired;
    }

    public void setHasRetired(int hasRetired) {
        this.hasRetired = hasRetired;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public int getHasCheckedOut() {
        return hasCheckedOut;
    }

    public void setHasCheckedOut(int hasCheckedOut) {
        this.hasCheckedOut = hasCheckedOut;
    }

    public String getCheckInMillis() {
        return checkInMillis;
    }

    public void setCheckInMillis(String checkInMillis) {
        this.checkInMillis = checkInMillis;
    }

    public String getCheckOutMillis() {
        return checkOutMillis;
    }

    public void setCheckOutMillis(String checkOutMillis) {
        this.checkOutMillis = checkOutMillis;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }

    public String getBibNo() {
        return bibNo;
    }

    public void setBibNo(String bibNo) {
        this.bibNo = bibNo;
    }

    public int getCheckpoint_id() {
        return checkpoint_id;
    }

    public void setCheckpoint_id(int checkpoint_id) {
        this.checkpoint_id = checkpoint_id;
    }
}
