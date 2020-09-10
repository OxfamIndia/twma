package com.infomatics.oxfam.twat.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Permission {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("actionCode")
    @Expose
    private String actionCode;
    @SerializedName("actionDesc")
    @Expose
    private String actionDesc;
    @SerializedName("actions")
    @Expose
    private List<String> actions = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }
}
