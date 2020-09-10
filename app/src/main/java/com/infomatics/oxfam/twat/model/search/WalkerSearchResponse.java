package com.infomatics.oxfam.twat.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.team.Walker;

public class WalkerSearchResponse extends BaseResponse {

    @SerializedName("datalist")
    @Expose
    private Walker walker;

    public Walker getWalker() {
        return walker;
    }

    public void setWalker(Walker walker) {
        this.walker = walker;
    }
}
