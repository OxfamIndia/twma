package com.infomatics.oxfam.twat.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("bibno")
    @Expose
    private String bibNo;

    @SerializedName("rfid")
    @Expose
    private String rfid;

    @Expose(serialize = false)
    private int walkerId;

    @Expose(serialize =  false)
    private int lastCP;

    @Expose(serialize = false)
    private String lastCPTime;

    @Expose(serialize = false)
    private String walkerName;

    @Expose(serialize = false)
    private boolean isSelected;

    @Expose(serialize = false)
    private String teamName;

    @Expose(serialize = false)
    private String selectedColor;

    @Expose(serialize = false)
    private String phone;
    public String getBibNo() {
        return bibNo;
    }

    public void setBibNo(String bibNo) {
        this.bibNo = bibNo;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public int getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(int walkerId) {
        this.walkerId = walkerId;
    }

    public String getWalkerName() {
        return walkerName;
    }

    public void setWalkerName(String walkerName) {
        this.walkerName = walkerName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
