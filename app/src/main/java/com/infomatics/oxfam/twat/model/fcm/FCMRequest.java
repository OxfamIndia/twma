package com.infomatics.oxfam.twat.model.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FCMRequest {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("roleId")
    @Expose
    private int roleId;

    @SerializedName("userId")
    @Expose
    private int userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
