package com.infomatics.oxfam.twat.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;

public class TeamSearchResponse extends BaseResponse {

    @SerializedName("datalist")
    @Expose
    private TeamEntity datalist;

    public TeamEntity getDatalist() {
        return datalist;
    }

    public void setDatalist(TeamEntity datalist) {
        this.datalist = datalist;
    }
}
