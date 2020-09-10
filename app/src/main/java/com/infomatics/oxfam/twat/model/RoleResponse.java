package com.infomatics.oxfam.twat.model;

import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.login.Role;
import com.infomatics.oxfam.twat.model.room.entity.RoleEntity;

import java.util.ArrayList;

public class RoleResponse extends BaseResponse {

    @SerializedName("datalist")
    private ArrayList<RoleEntity> datalist;

    public ArrayList<RoleEntity> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<RoleEntity> datalist) {
        this.datalist = datalist;
    }
}
