package com.infomatics.oxfam.twat.model.checkincheckout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckInCheckOutBean {

    @SerializedName("checkin_time")
    @Expose
    private String checkin_time;

    @SerializedName("checkout_time")
    @Expose
    private String checkout_time;

    @SerializedName("is_retire")
    @Expose
    private int is_retire;

    @SerializedName("bibno")
    @Expose
    private String bibno;

    @SerializedName("checkpoint_id")
    @Expose
    private int checkpoint_id;

    public String getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(String checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(String checkout_time) {
        this.checkout_time = checkout_time;
    }

    public int getIs_retire() {
        return is_retire;
    }

    public void setIs_retire(int is_retire) {
        this.is_retire = is_retire;
    }

    public String getBibno() {
        return bibno;
    }

    public void setBibno(String bibno) {
        this.bibno = bibno;
    }

    public int getCheckpoint_id() {
        return checkpoint_id;
    }

    public void setCheckpoint_id(int checkpoint_id) {
        this.checkpoint_id = checkpoint_id;
    }
}
