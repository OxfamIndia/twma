package com.infomatics.oxfam.twat.model.dutyregister;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DutyList {
    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("id")
    @Expose
    private String id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.user_name;
    }
}
