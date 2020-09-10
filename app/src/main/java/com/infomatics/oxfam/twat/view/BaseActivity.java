package com.infomatics.oxfam.twat.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.infomatics.oxfam.twat.interfaces.DashboardTaskComplete;
import com.infomatics.oxfam.twat.model.retirement.Datalist;
import com.infomatics.oxfam.twat.model.room.entity.DashboardEntity;
import com.infomatics.oxfam.twat.model.room.entity.RetirementEntity;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.room.entity.WalkerEntity;
import com.infomatics.oxfam.twat.model.team.Walker;
import com.infomatics.oxfam.twat.repository.backgroundtask.InsertCPDataInBackground;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.viewmodel.BaseActivityViewModel;
import com.infomatics.oxfam.twat.viewmodel.CheckInCheckOutViewModel;
import com.novoda.merlin.Merlin;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    public ObservableBoolean isConnectedToInternet= new ObservableBoolean();
    BaseActivityViewModel viewModel;
    Merlin merlin;

    private ObservableBoolean fetchedTeamData = new ObservableBoolean();
    private ObservableBoolean fetchedRfidMapping = new ObservableBoolean();
    private ObservableBoolean fetchedCurrentCheckPointData = new ObservableBoolean();
    private ObservableBoolean fetchedPreviousCheckPointData = new ObservableBoolean();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnectedToInternet.set(ApplicationUtils.isNetworkAvailable(BaseActivity.this));
        merlin = new Merlin.Builder().withAllCallbacks().build(BaseActivity.this);
        merlin.registerConnectable(() -> {
            isConnectedToInternet.set(true);
        });
        merlin.registerDisconnectable(()->{
            isConnectedToInternet.set(false);
        });
        viewModel = ViewModelProviders.of(BaseActivity.this).get(BaseActivityViewModel.class);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(BaseActivity.this), "Initialising app...", true);
        if(AppConstants.CHECKPOINT_ID < 6){
         AppConstants.TEAM_COUNT = viewModel.getTeamCountByType();
         AppConstants.WALKER_COUNT = viewModel.getHunderedWalkerCount();
        }else {
            AppConstants.TEAM_COUNT = viewModel.getAllTeamCount();
            AppConstants.WALKER_COUNT = viewModel.getAllWalkerCount();
        }
        if(!AppConstants.getData){
            if(isConnectedToInternet.get()) {
                progressDialog.show();
                AppConstants.getData = true;
                getTeamData();
            }
        }
        isConnectedToInternet.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(!AppConstants.getData){
                    progressDialog.show();
                    getTeamData();
                }
            }
        });
        fetchedTeamData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(fetchedTeamData.get()){
                    getRfidMapping();
                }
            }
        });

        fetchedRfidMapping.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(fetchedRfidMapping.get()){
                    if(AppConstants.USER_ROLE.getId() != 1) {
//                        getPreviousCheckPointData();
                        getCurrentCheckPointData();
                    }
                    else{
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        AppConstants.hasFetchedAllData.set(true);
                    }

                }
            }
        });

        fetchedPreviousCheckPointData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(fetchedPreviousCheckPointData.get()){
                    getCurrentCheckPointData();
                }
            }
        });
        fetchedCurrentCheckPointData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(fetchedCurrentCheckPointData.get()){
                    getRetirementData();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    AppConstants.hasFetchedAllData.set(true);
                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.APP_PREF, MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(AppConstants.UPDATED_FCM, false)){
            if(sharedPreferences.contains(AppConstants.FCM_TOKEN)
            && sharedPreferences.getString(AppConstants.FCM_TOKEN,"").length() > 0){
                viewModel.updateFCMToken(sharedPreferences.getString(AppConstants.FCM_TOKEN,""));
                sharedPreferences.edit().putBoolean(AppConstants.UPDATED_FCM, true).apply();
            }
        }
    }

    private void getRetirementData(){
        viewModel.getAllRetirements().observe(BaseActivity.this, res->{
            if(res != null && res.getDataList() != null){
                viewModel.clearRetirementData();
                List<RetirementEntity> retirementEntities = new ArrayList<>();
                for(Datalist d : res.getDataList()){
                    RetirementEntity retirementEntity = new RetirementEntity();
                    retirementEntity.setBibNo(d.getBibNo());
                    retirementEntity.setCpName(d.getCpName());
                    retirementEntity.setFirst_name(d.getFirst_name());
                    retirementEntity.setLast_name(d.getLast_name());
                    retirementEntity.setInTime(d.getInTime());

                    retirementEntities.add(retirementEntity);
                }
                viewModel.insertAll(retirementEntities);
                Log.e(AppConstants.TAG,"ALL RETIREMENT DATA INSERTED");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        merlin.bind();
    }

    @Override
    protected void onPause() {
        merlin.unbind();
        super.onPause();
    }

    private void getTeamData(){
        viewModel.getAllTeams().observe(BaseActivity.this, response -> {
            if (response != null) {
                if (response.getDatalist() != null) {
                    viewModel.removeAllTeams();
                    viewModel.removeAllWalkers();
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            List<WalkerEntity> walkers = new ArrayList<>();
                            List<WalkerEntity> hundred_walkers = new ArrayList<>();
                            List<TeamEntity> teams = new ArrayList<>();

                            for(TeamEntity teamEntity: response.getDatalist()){
                                boolean isRun = false;
                                if(teamEntity.getWalker() != null) {
                                    for (Walker walker : teamEntity.getWalker()) {
                                        WalkerEntity walkerEntity = new WalkerEntity();
                                        walkerEntity.setBibNo(walker.getBibNo());
                                        walkerEntity.setMobile(walker.getMobile());
                                        walkerEntity.setRunning(walker.getRunning());
                                        walkerEntity.setType(teamEntity.getType());
                                        if (walker.getRunning().equalsIgnoreCase("1"))
                                            isRun = true;
                                        walkerEntity.setWalkerId(walker.getId());
                                        walkerEntity.setTeamName(teamEntity.getTeamTitle());
                                        walkerEntity.setWalkerName(walker.getFirstName() + " " + walker.getLastName());
                                        if (walker.getRunning().equalsIgnoreCase("1")) {
                                            walkers.add(walkerEntity);
                                            if (teamEntity.getType() == 100) {
                                                hundred_walkers.add(walkerEntity);
                                            }
                                        }
                                    }
                                }
                                if(teamEntity.getIsActivated() == 1)
                                    teams.add(teamEntity);
                            }
                            viewModel.insertTeamData(teams);
                            viewModel.insertWalkers(walkers);
                            if(AppConstants.CHECKPOINT_ID < 6){
                                AppConstants.WALKER_COUNT = hundred_walkers.size();
                                AppConstants.TEAM_COUNT = viewModel.getTeamCountByType();
                            }else {
                                AppConstants.WALKER_COUNT = walkers.size();
                                AppConstants.TEAM_COUNT = teams.size();
                            }
                            Log.e(AppConstants.TAG, "All Teams & Walkers Inserted");
                            fetchedTeamData.set(true);
                            return null;
                        }
                    }.execute();
                }
            }
        });
    }

    private void getRfidMapping(){
        viewModel.getAllRfidMapping().observe(BaseActivity.this, response -> {
            if (response != null && response.getDatalist() != null) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        viewModel.removeAllRfids();
                        viewModel.insertAllRfids(response.getDatalist());
                        Log.e(AppConstants.TAG, "All RFIDs Inserted");
                        fetchedRfidMapping.set(true);
                        return null;
                    }
                }.execute();
            }
        });
    }

    private void getCurrentCheckPointData(){
        viewModel.getCurrentCheckPointProgress().observe(BaseActivity.this, r->{
            if(r != null && r.status){
                if(r.getDatalist() != null){
                    new InsertCPDataInBackground(BaseActivity.this, AppConstants.CHECKPOINT_ID,
                            r.getDatalist(), dashboardEntity -> {
                        fetchedCurrentCheckPointData.set(true);
                    }).execute();
                }
            }
        });
    }

    private void getPreviousCheckPointData(){
        if(AppConstants.CHECKPOINT_ID > 1) {
            viewModel.getCheckpointProgress().observe(BaseActivity.this, r->{
                if(r != null && r.status){
                    if(r.getDatalist() != null){
                        new InsertCPDataInBackground(BaseActivity.this, AppConstants.CHECKPOINT_ID - 1,
                                r.getDatalist(), dashboardEntity -> {
                                    fetchedPreviousCheckPointData.set(true);
                                }).execute();
                    }
                }
            });
        }else{
            if(AppConstants.CHECKPOINT_ID == 1){
                getCurrentCheckPointData();
            }
        }
    }
}
