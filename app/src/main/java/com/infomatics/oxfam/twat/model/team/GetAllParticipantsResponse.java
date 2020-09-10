package com.infomatics.oxfam.twat.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infomatics.oxfam.twat.model.BaseResponse;

import java.util.ArrayList;

public class GetAllParticipantsResponse extends BaseResponse{

   @SerializedName("datalist")
    @Expose
    private ArrayList<TeamMember> teams;

    public ArrayList<TeamMember> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<TeamMember> teams) {
        this.teams = teams;
    }
}
