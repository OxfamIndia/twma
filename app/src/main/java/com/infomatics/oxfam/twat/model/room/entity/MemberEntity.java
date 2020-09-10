package com.infomatics.oxfam.twat.model.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "rfid_mapping")
public class MemberEntity {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    @ColumnInfo(name="rfid")
    @SerializedName("rfid")
    @Expose
    private String rfid;

    @ColumnInfo(name="bibno")
    @SerializedName("bibno")
    @Expose
    private String bibno;

    @ColumnInfo(name="event_id")
    @SerializedName("event_id")
    @Expose
    private String eventId;

    @ColumnInfo(name="leaderId")
    @SerializedName("leaderId")
    @Expose
    private String leaderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getBibno() {
        return bibno;
    }

    public void setBibno(String bibno) {
        this.bibno = bibno;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
}
