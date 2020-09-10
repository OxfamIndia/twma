package com.infomatics.oxfam.twat.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;

import java.util.List;

public class AllTeamsResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("datalist")
    @Expose
    private List<TeamEntity> datalist = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TeamEntity> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<TeamEntity> datalist) {
        this.datalist = datalist;
    }

}
