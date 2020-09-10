package com.infomatics.oxfam.twat.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.room.entity.MemberEntity;

import java.util.List;

public class AllRfidResponse extends BaseResponse{

    @SerializedName("datalist")
    @Expose
    private List<MemberEntity> datalist;

    public List<MemberEntity> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<MemberEntity> datalist) {
        this.datalist = datalist;
    }
}
