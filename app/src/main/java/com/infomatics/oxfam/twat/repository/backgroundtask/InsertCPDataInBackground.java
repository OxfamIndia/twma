package com.infomatics.oxfam.twat.repository.backgroundtask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.infomatics.oxfam.twat.interfaces.DashboardTaskComplete;
import com.infomatics.oxfam.twat.model.CpEntryData;
import com.infomatics.oxfam.twat.model.room.entity.DashboardEntity;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;
import com.infomatics.oxfam.twat.repository.AppDatabase;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class InsertCPDataInBackground extends AsyncTask<Void, Void, Void> {
    private ArrayList<CpEntryData> dataList;
    private int cpId;
    private AppDatabase appDatabase;
    private DashboardTaskComplete dashboardTaskComplete;
    public InsertCPDataInBackground(Context context, int cpId, ArrayList<CpEntryData> dataList, DashboardTaskComplete dashboardTaskComplete){
        this.dataList = dataList;
        this.cpId = cpId;
        appDatabase = AppDatabase.getAppDatabase(context);
        this.dashboardTaskComplete = dashboardTaskComplete;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ArrayList<EntryLog> entries = new ArrayList<>();
        for(CpEntryData cpEntryData : dataList){
            boolean exists = appDatabase.entryDao().findEntrybyBibNo(cpEntryData.getBibno(), cpId) != null;
            if(!exists){
                EntryLog entryLog = new EntryLog();
                entryLog.setBibNo(cpEntryData.getBibno());
                String bib = appDatabase.memberDao().getRfidByBibNo(cpEntryData.getBibno());
                if(bib != null)
                    entryLog.setTagId(bib);
                else
                    entryLog.setTagId("-");

                if(cpEntryData.getCheckoutTime() > 0) {
                    entryLog.setCheckOutTime(ApplicationUtils.getDate(cpEntryData.getCheckoutTime()));
                    entryLog.setActivityTime(ApplicationUtils.getDate(cpEntryData.getCheckoutTime()));
                    entryLog.setCheckOutMillis(Long.toString(cpEntryData.getCheckoutTime()));
                }
                if(cpEntryData.getCheckinTime() > 0){
                    entryLog.setCheckInMillis(Long.toString(cpEntryData.getCheckinTime()));
                    entryLog.setCheckInTime(ApplicationUtils.getDate(cpEntryData.getCheckinTime()));
                }
                if(entryLog.getCheckOutMillis() != null && entryLog.getCheckOutMillis().length() > 3)
                entryLog.setHasCheckedOut(1);
                entryLog.setHasRetired(Integer.parseInt(cpEntryData.getIsRetire()));
                entryLog.setCheckpoint_id(Integer.parseInt(cpEntryData.getCheckpointId()));
                entryLog.setIsSynced(1);
                entries.add(entryLog);
            }
        }
        if(cpId != AppConstants.CHECKPOINT_ID) {
            appDatabase.entryDao().removeAll(cpId);
        }
        appDatabase.entryDao().insertAll(entries.toArray(new EntryLog[entries.size()]));
        dashboardTaskComplete.onTaskComplete(new DashboardEntity());
        Log.e(AppConstants.TAG, "All CP Data inserted");
        return null;
    }
}
