package com.infomatics.oxfam.twat.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Walker {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("bibNo")
    @Expose
    private String bibNo;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("walkerId")
    @Expose
    private int walkerId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("running")
    @Expose
    private String running;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lastCP")
    @Expose
    private String lastCP;
    @SerializedName("lastCPTime")
    @Expose
    private String lastCPTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBibNo() {
        return bibNo;
    }

    public void setBibNo(String bibNo) {
        this.bibNo = bibNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastCP() {
        return lastCP;
    }

    public void setLastCP(String lastCP) {
        this.lastCP = lastCP;
    }

    public String getLastCPTime() {
        return lastCPTime;
    }

    public void setLastCPTime(String lastCPTime) {
        this.lastCPTime = lastCPTime;
    }
}
