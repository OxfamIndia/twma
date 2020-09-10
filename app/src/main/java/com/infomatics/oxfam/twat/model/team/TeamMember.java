package com.infomatics.oxfam.twat.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamMember {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("bibNo")
    @Expose
    private String bibNo;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("allMale")
    @Expose
    private String allMale;
    @SerializedName("allFemale")
    @Expose
    private String allFemale;
    @SerializedName("mixedTeam")
    @Expose
    private String mixedTeam;
    @SerializedName("above50")
    @Expose
    private String above50;
    @SerializedName("emergency")
    @Expose
    private Object emergency;
    @SerializedName("participation")
    @Expose
    private String participation;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("running")
    @Expose
    private String running;
    @SerializedName("deviceId")
    @Expose
    private Object deviceId;
    @SerializedName("platform")
    @Expose
    private Object platform;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("teamId")
    @Expose
    private String teamId;
    @SerializedName("teamName")
    @Expose
    private String teamName;
    @SerializedName("km")
    @Expose
    private String km;
    @SerializedName("walkerName")
    @Expose
    private String walkerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBibNo() {
        return bibNo;
    }

    public void setBibNo(String bibNo) {
        this.bibNo = bibNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAllMale() {
        return allMale;
    }

    public void setAllMale(String allMale) {
        this.allMale = allMale;
    }

    public String getAllFemale() {
        return allFemale;
    }

    public void setAllFemale(String allFemale) {
        this.allFemale = allFemale;
    }

    public String getMixedTeam() {
        return mixedTeam;
    }

    public void setMixedTeam(String mixedTeam) {
        this.mixedTeam = mixedTeam;
    }

    public String getAbove50() {
        return above50;
    }

    public void setAbove50(String above50) {
        this.above50 = above50;
    }

    public Object getEmergency() {
        return emergency;
    }

    public void setEmergency(Object emergency) {
        this.emergency = emergency;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    public Object getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Object deviceId) {
        this.deviceId = deviceId;
    }

    public Object getPlatform() {
        return platform;
    }

    public void setPlatform(Object platform) {
        this.platform = platform;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getWalkerName() {
        return walkerName;
    }

    public void setWalkerName(String walkerName) {
        this.walkerName = walkerName;
    }

}
