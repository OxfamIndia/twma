package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.CPEntryResponse;
import com.infomatics.oxfam.twat.model.checkincheckout.CPStatusResponse;
import com.infomatics.oxfam.twat.model.checkincheckout.ChangeCPStatusRequest;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutBean;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutResponse;
import com.infomatics.oxfam.twat.model.room.dao.DashboardDao;
import com.infomatics.oxfam.twat.model.room.dao.EntryDao;
import com.infomatics.oxfam.twat.model.room.dao.MemberDao;
import com.infomatics.oxfam.twat.model.room.dao.TeamDao;
import com.infomatics.oxfam.twat.model.room.entity.CheckpointEntity;
import com.infomatics.oxfam.twat.model.room.entity.DashboardEntity;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;
import com.infomatics.oxfam.twat.model.room.entity.MemberEntity;
import com.infomatics.oxfam.twat.model.room.entity.RetirementEntity;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.team.AllRfidResponse;
import com.infomatics.oxfam.twat.model.team.AllTeamsResponse;
import com.infomatics.oxfam.twat.repository.ApiRepository;
import com.infomatics.oxfam.twat.repository.AppDatabase;
import com.infomatics.oxfam.twat.util.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class CheckInCheckOutViewModel extends AndroidViewModel{

    private EntryDao entryDao;
    private MemberDao memberDao;
    private TeamDao teamDao;
    private DashboardDao dashboardDao;
    private AppDatabase appDatabase;
    private ApiRepository apiRepository;
    private MutableLiveData<AllRfidResponse> allRfids;
    private MutableLiveData<CheckInCheckOutResponse> updateEntryResponse;
    private MutableLiveData<BaseResponse> changeCpStatusResponse;
    private MutableLiveData<CPStatusResponse> cpStatusChange;
    private MutableLiveData<AllTeamsResponse> allTeamsResponse;
    private MutableLiveData<CPEntryResponse> cpStatusResponse;
    public CheckInCheckOutViewModel(Application application){
        super(application);
        if(allRfids != null && updateEntryResponse != null
        && allTeamsResponse != null && cpStatusResponse != null)
            return;
        appDatabase = AppDatabase.getAppDatabase(application);
        entryDao = appDatabase.entryDao();
        teamDao = appDatabase.teamDao();
        memberDao = appDatabase.memberDao();
        dashboardDao = appDatabase.dashboardDao();
        apiRepository = ApiRepository.getInstance();
    }

    public EntryLog getEntryWithbib(String bibNo, int cp){
        return entryDao.getEntryWithbibAndId(bibNo, cp);
    }

    public CheckpointEntity getCPData(int cpId){
        return appDatabase.checkPointDao().getCpData(cpId);
    }

    public void updateCPData(CheckpointEntity checkpointEntity){
        appDatabase.checkPointDao().update(checkpointEntity);
    }

    public int getTeamSizeWithBibNo(String bib){
        return memberDao.getCountOfBib(bib);
    }

    public LiveData<CPEntryResponse> getCheckpointProgress(){
        cpStatusResponse = apiRepository.getCheckpointProgress(AppConstants.CHECKPOINT_ID);
        return cpStatusResponse;
    }

    public int getAllWalkerCount(){
        return appDatabase.walkerDao().getAllWalkersCount();
    }

    public int getAllTeamCount(){
        return appDatabase.teamDao().getAllTeamCount();
    }

    public List<EntryLog> getUnSyncedEntries(){return entryDao.getAllUnSyncedEntries(AppConstants.CHECKPOINT_ID);}

    public DashboardEntity getDashboardData(){
        return dashboardDao.getDashboarddata(AppConstants.CHECKPOINT_ID);
    }

    public void insert(DashboardEntity dashboardEntity){
        dashboardDao.insert(dashboardEntity);
    }

    public void updateDashboardData(DashboardEntity dashboardEntity){
        dashboardDao.update(dashboardEntity);
    }

    public void insertDashboardData(DashboardEntity dashboardEntity){
        dashboardDao.insert(dashboardEntity);
    }

    public void updateAllEntries(List<EntryLog> entries){
        entryDao.updateEntries(entries.toArray(new EntryLog[entries.size()]));
    }
    public MemberEntity getbibNo(String tag){
        return memberDao.getMemberByTagId(tag);
    }

    public LiveData<List<EntryLog>> getAllEntries(){

            return entryDao.getAllEntries(AppConstants.CHECKPOINT_ID);
    }

    public RetirementEntity getRetirementIfExists(String bibNo){
        return appDatabase.retirementDao().getRetirementWithBibNo(bibNo);
    }

    public LiveData<AllTeamsResponse> getAllTeams(){
        return apiRepository.getAllTeams();
    }

    public LiveData<List<TeamEntity>> getAllTeamNames(){
        return teamDao.getAllTeamNames();
    }

    public void insert(EntryLog entryLog){
        appDatabase.entryDao().insert(entryLog);
    }

    public int findCountOfTeamMembersCheckedIn(String bib){
        return appDatabase.entryDao().getTeamCheckinCount(bib, AppConstants.CHECKPOINT_ID);
    }

    public int findCountOfTeamMembersCheckedOut(String bib){
        return appDatabase.entryDao().getTeamCheckoutCount(bib, AppConstants.CHECKPOINT_ID);
    }

    public int findCountOfTeamMembersRetirement(String bib){
        return appDatabase.entryDao().getTeamRetirementCount(bib, AppConstants.CHECKPOINT_ID);
    }
    public void update(EntryLog entryLog){
        appDatabase.entryDao().update(entryLog);
    }

    public int getTotalRetirements(){
        return appDatabase.entryDao().getRetirementCount(AppConstants.CHECKPOINT_ID);
    }

    public int getTotalCheckIns(){
        return appDatabase.entryDao().getCheckInCounts(AppConstants.CHECKPOINT_ID);
    }

    public int getTotalCheckOuts(){
        return appDatabase.entryDao().getCheckOutCounts(AppConstants.CHECKPOINT_ID);
    }
    public EntryLog findByTagId(String tagId){
            return appDatabase.entryDao().findEntryByTagId(tagId, AppConstants.CHECKPOINT_ID);
    }
    public EntryLog findIfTagRetired(String tagId){
        if(AppConstants.CHECKPOINT_ID > 1 && AppConstants.CHECKPOINT_ID != 6){
            return appDatabase.entryDao().findEntryByTagId(tagId, AppConstants.CHECKPOINT_ID-1);
        }else{
            return appDatabase.entryDao().findEntryByTagId(tagId, AppConstants.CHECKPOINT_ID);
        }
    }

    public EntryLog findByBibNo(String bibNo){
        return appDatabase.entryDao().findEntrybyBibNo(bibNo, AppConstants.CHECKPOINT_ID);
    }

    public LiveData<AllRfidResponse> getAllRfidMapping(){
        allRfids =  apiRepository.getAllRfids();
        return allRfids;
    }

    public LiveData<CheckInCheckOutResponse> updateAllUnsyncedEntries(ArrayList<CheckInCheckOutBean> checkInCheckOutBeans){
        updateEntryResponse = apiRepository.syncCheckInCheckOut(checkInCheckOutBeans);
        return updateEntryResponse;
    }

    public void removeAllRfids(){
        appDatabase.memberDao().removeAllEntries();
    }

    public void insertAllRfids(List<MemberEntity> memberEntities){
        appDatabase.memberDao().addMembers(memberEntities.toArray(new MemberEntity[memberEntities.size()]));
    }

    public TeamEntity getTeamWithLeaderId(int leaderId){
        return appDatabase.teamDao().getWalkerDetails(leaderId);
    }
    public void insertTeamData(List<TeamEntity> teamEntities){
        appDatabase.teamDao().addTeams(teamEntities.toArray(new TeamEntity[teamEntities.size()]));
    }

    public LiveData<BaseResponse> changeCpStatus(ChangeCPStatusRequest changeCPStatusRequest){
        changeCpStatusResponse = apiRepository.changeCPStatus(changeCPStatusRequest);
        return changeCpStatusResponse;
    }

    public LiveData<CPStatusResponse> getCPStatus(){
        cpStatusChange = apiRepository.getCPStatus(AppConstants.CHECKPOINT_ID);
        return cpStatusChange;
    }

    public int getRetirementCountOfBib(String bib){
        return entryDao.getTeamRetirementCount(bib, AppConstants.CHECKPOINT_ID-1);
    }
}
