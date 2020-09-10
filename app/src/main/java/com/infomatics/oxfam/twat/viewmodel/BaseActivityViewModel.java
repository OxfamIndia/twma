package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.infomatics.oxfam.twat.model.AllBibNosResponse;
import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.CPEntryResponse;
import com.infomatics.oxfam.twat.model.checkincheckout.CPStatusResponse;
import com.infomatics.oxfam.twat.model.checkincheckout.ChangeCPStatusRequest;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutBean;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutResponse;
import com.infomatics.oxfam.twat.model.retirement.RetirementResponse;
import com.infomatics.oxfam.twat.model.room.dao.DashboardDao;
import com.infomatics.oxfam.twat.model.room.dao.EntryDao;
import com.infomatics.oxfam.twat.model.room.dao.MemberDao;
import com.infomatics.oxfam.twat.model.room.dao.TeamDao;
import com.infomatics.oxfam.twat.model.room.entity.DashboardEntity;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;
import com.infomatics.oxfam.twat.model.room.entity.MemberEntity;
import com.infomatics.oxfam.twat.model.room.entity.RetirementEntity;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.room.entity.WalkerEntity;
import com.infomatics.oxfam.twat.model.team.AllRfidResponse;
import com.infomatics.oxfam.twat.model.team.AllTeamsResponse;
import com.infomatics.oxfam.twat.repository.ApiRepository;
import com.infomatics.oxfam.twat.repository.AppDatabase;
import com.infomatics.oxfam.twat.util.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class BaseActivityViewModel extends AndroidViewModel {
    private EntryDao entryDao;
    private MemberDao memberDao;
    private TeamDao teamDao;
    private DashboardDao dashboardDao;
    private AppDatabase appDatabase;
    private ApiRepository apiRepository;
    private MutableLiveData<AllRfidResponse> allRfids;
    private MutableLiveData<CPEntryResponse> cpStatusResponse;
    private MutableLiveData<CPEntryResponse> currentCpStatusResponse;
    private MutableLiveData<RetirementResponse> allretirementResponse;
    private MutableLiveData<CheckInCheckOutResponse> updateEntryResponse;
    private MutableLiveData<BaseResponse> changeCpStatusResponse;
    private MutableLiveData<AllTeamsResponse> allTeamsResponse;
    private MutableLiveData<AllBibNosResponse> allbibNos;
    public BaseActivityViewModel(Application application){
        super(application);
        if(allRfids != null && updateEntryResponse != null
                && allTeamsResponse != null && cpStatusResponse != null
                && currentCpStatusResponse != null
        && allretirementResponse != null)
            return;
        appDatabase = AppDatabase.getAppDatabase(application);
        entryDao = appDatabase.entryDao();
        teamDao = appDatabase.teamDao();
        memberDao = appDatabase.memberDao();
        dashboardDao = appDatabase.dashboardDao();
        apiRepository = ApiRepository.getInstance();
    }

    public int getAllTeamCount(){
        return appDatabase.teamDao().getAllTeamCount();
    }

    public int getTeamCountByType(){
        return appDatabase.teamDao().getTeamCountByType(100);
    }

    public int getAllWalkerCount(){
        return appDatabase.walkerDao().getAllWalkersCount();
    }

    public void insert(DashboardEntity dashboardEntity){
        dashboardDao.insert(dashboardEntity);
    }

    public LiveData<AllTeamsResponse> getAllTeams(){
        return apiRepository.getAllTeams();
    }

    public void insert(EntryLog entryLog){
        appDatabase.entryDao().insert(entryLog);
    }

    public void update(EntryLog entryLog){
        appDatabase.entryDao().update(entryLog);
    }

    public LiveData<AllRfidResponse> getAllRfidMapping(){
        allRfids =  apiRepository.getAllRfids();
        return allRfids;
    }

    public LiveData<AllBibNosResponse> getAllBibNos(){
        allbibNos = apiRepository.getAllBibNos();
        return allbibNos;
    }

    public LiveData<CPEntryResponse> getCheckpointProgress(){
        cpStatusResponse = apiRepository.getCheckpointProgress(AppConstants.CHECKPOINT_ID-1);
        return cpStatusResponse;
    }

    public LiveData<RetirementResponse> getAllRetirements(){
        allretirementResponse = apiRepository.getAllRetirements(Integer.toString(AppConstants.CHECKPOINT_ID));
        return allretirementResponse;
    }

    public LiveData<CPEntryResponse> getCurrentCheckPointProgress(){
        currentCpStatusResponse = apiRepository.getCheckpointProgress(AppConstants.CHECKPOINT_ID);
        return currentCpStatusResponse;
    }

    public void clearRetirementData(){
        appDatabase.retirementDao().removeAll();
    }

    public void insertAll(List<RetirementEntity> retirements){
        appDatabase.retirementDao().insertAll(retirements.toArray(new RetirementEntity[retirements.size()]));
    }

    public void removeAllRfids(){
        appDatabase.memberDao().removeAllEntries();
    }

    public void insertAllRfids(List<MemberEntity> memberEntities){
        appDatabase.memberDao().addMembers(memberEntities.toArray(new MemberEntity[memberEntities.size()]));
    }

    public void insertTeamData(List<TeamEntity> teamEntities){
        appDatabase.teamDao().addTeams(teamEntities.toArray(new TeamEntity[teamEntities.size()]));
    }

    public void removeAllTeams(){
        appDatabase.teamDao().removeAll();
    }

    public void removeAllWalkers(){
        appDatabase.walkerDao().removeAll();
    }
    public void insertWalkers(List<WalkerEntity> walkers){
        appDatabase.walkerDao().insertAll(walkers.toArray(new WalkerEntity[walkers.size()]));
    }

    public int getHunderedWalkerCount(){
        return appDatabase.walkerDao().getHundredWalkerCount();
    }

    public void updateFCMToken(String token){
        apiRepository.updateFCM(token);
    }
}
