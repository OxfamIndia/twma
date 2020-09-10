package com.infomatics.oxfam.twat.model.contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.room.entity.ContactEntity;

import java.util.ArrayList;

public class SearchDirectoryResponse extends BaseResponse {

    @SerializedName("datalist")
    @Expose
    private ArrayList<ContactEntity> datalist;

    public ArrayList<ContactEntity> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<ContactEntity> datalist) {
        this.datalist = datalist;
    }
}
