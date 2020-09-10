package com.infomatics.oxfam.twat.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Walker {
    @SerializedName("checkin")
    @Expose
    private Integer checkin;
    @SerializedName("checkout")
    @Expose
    private Integer checkout;
    @SerializedName("retire")
    @Expose
    private Integer retire;

    public Integer getCheckin() {
        return checkin;
    }

    public void setCheckin(Integer checkin) {
        this.checkin = checkin;
    }

    public Integer getCheckout() {
        return checkout;
    }

    public void setCheckout(Integer checkout) {
        this.checkout = checkout;
    }

    public Integer getRetire() {
        return retire;
    }

    public void setRetire(Integer retire) {
        this.retire = retire;
    }
}
