package com.infomatics.oxfam.twat.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.infomatics.oxfam.twat.interfaces.DashboardTaskComplete;
import com.infomatics.oxfam.twat.model.room.entity.DashboardEntity;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.viewmodel.CheckInCheckOutViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardBackgroundTask extends AsyncTask<Void, Void, Void> {
    DashboardTaskComplete dashboardTaskComplete;
    List<EntryLog> entries;
    DashboardEntity dashboardEntity;
    CheckInCheckOutViewModel viewModel;
    private List<Integer> teamBibsCheckIns = new ArrayList<>();
    private List<Integer> teamBibsCheckOuts = new ArrayList<>();
    private List<Integer> teamBibsRetirements = new ArrayList<>();
    int teamCheckInCount, teamCheckOutCount, teamRetirements;
    public DashboardBackgroundTask(CheckInCheckOutViewModel viewModel,
                                   List<EntryLog> entries,
                                   DashboardTaskComplete dashboardTaskComplete){
        this.dashboardTaskComplete = dashboardTaskComplete;
        this.entries = entries;
        this.dashboardEntity = new DashboardEntity();
        this.viewModel = viewModel;
        if(this.entries == null)
            this.entries = new ArrayList<>();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        for(EntryLog entry : entries){
            try {
                String e = entry.getBibNo().substring(0, entry.getBibNo().length() - 1) + "%";
                int count = viewModel.findCountOfTeamMembersCheckedIn(e);
                int countCheckOut = viewModel.findCountOfTeamMembersCheckedOut(e);
                int retireCount = viewModel.findCountOfTeamMembersRetirement(e);
                int TEAM_SIZE = viewModel.getTeamSizeWithBibNo(e);
                int teamBib = Integer.parseInt(entry.getBibNo().substring(0, entry.getBibNo().length() - 1));
                if (count >= 1 && !teamBibsCheckIns.contains(teamBib)) {
                    teamCheckInCount++;
                    teamBibsCheckIns.add(teamBib);
                    dashboardEntity.setTeamBibCheckIns(teamBibsCheckIns);
                    dashboardEntity.setTotalTeamCheckIns(teamCheckInCount);
                }

                if (countCheckOut >= 1 && !teamBibsCheckOuts.contains(teamBib)) {
                    teamCheckOutCount++;
                    teamBibsCheckOuts.add(teamBib);
                    dashboardEntity.setTotalTeamCheckOuts(teamCheckOutCount);
                    dashboardEntity.setTeamBibCheckOuts(teamBibsCheckOuts);
                }
                if(TEAM_SIZE == retireCount && !teamBibsRetirements.contains(teamBib)){
                    teamRetirements++;
                    teamBibsRetirements.add(teamBib);
                    dashboardEntity.setTotalTeamRetirements(teamRetirements);
                    dashboardEntity.setTeamRetirements(teamBibsRetirements);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dashboardTaskComplete.onTaskComplete(dashboardEntity);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        dashboardTaskComplete.onTaskComplete(null);
    }
}
