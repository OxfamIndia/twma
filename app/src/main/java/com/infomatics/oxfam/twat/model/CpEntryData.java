package com.infomatics.oxfam.twat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CpEntryData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bibno")
    @Expose
    private String bibno;
    @SerializedName("checkin_time")
    @Expose
    private Long checkinTime;
    @SerializedName("checkout_time")
    @Expose
    private Long checkoutTime;
    @SerializedName("checkpoint_id")
    @Expose
    private String checkpointId;
    @SerializedName("is_retire")
    @Expose
    private String isRetire;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBibno() {
        return bibno;
    }

    public void setBibno(String bibno) {
        this.bibno = bibno;
    }

    public Long getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Long checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Long getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Long checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(String checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getIsRetire() {
        return isRetire;
    }

    public void setIsRetire(String isRetire) {
        this.isRetire = isRetire;
    }
}
