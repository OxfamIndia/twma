package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.register.RegisterModel;
import com.infomatics.oxfam.twat.model.room.dao.TeamDao;
import com.infomatics.oxfam.twat.model.room.dao.WalkerDao;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.room.entity.WalkerEntity;
import com.infomatics.oxfam.twat.model.search.TeamSearchResponse;
import com.infomatics.oxfam.twat.model.search.WalkerSearchResponse;
import com.infomatics.oxfam.twat.model.team.AllTeamsResponse;
import com.infomatics.oxfam.twat.repository.ApiRepository;
import com.infomatics.oxfam.twat.repository.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistrationViewModel extends AndroidViewModel {

    private MutableLiveData<List<TeamEntity>> teamNames;
    private MutableLiveData<BaseResponse> mapRfidResponse;

    private MutableLiveData<TeamSearchResponse> teamDetailResponse;
    private MutableLiveData<WalkerSearchResponse> walkerDetailResponse;

    private List<String> filteredTeamNames;
    private TeamEntity teamEntity;
    private TeamDao teamDao;
    private WalkerDao walkerDao;
    private AppDatabase appDatabase;
    public ApiRepository apiRepository;
    public RegistrationViewModel(Application application) {
        super(application);
        if(teamNames!= null ||
                mapRfidResponse != null ||
                teamDetailResponse != null ||
                walkerDetailResponse != null){
            return;
        }
        apiRepository = ApiRepository.getInstance();
        appDatabase = AppDatabase.getAppDatabase(application);
        teamDao = appDatabase.teamDao();
        walkerDao = appDatabase.walkerDao();
    }

    public LiveData<BaseResponse> mapRfids(ArrayList<RegisterModel> registerModels){
        mapRfidResponse = apiRepository.mapRfids(registerModels);
        return mapRfidResponse;
    }

    public LiveData<AllTeamsResponse> getAllTeams(){
        return apiRepository.getAllTeams();
    }

    public List<String> getTeamByName(String name){
        filteredTeamNames = teamDao.getTeamsByName(name+"%");
        return filteredTeamNames;
    }
    public LiveData<List<TeamEntity>> getAllTeamNames(){
        return teamDao.getAllTeamNames();
    }

    public List<WalkerEntity> getWalkerByTeamName(String teamName){
        return walkerDao.getWalkerByTeamName(teamName);
    }
    public List<WalkerEntity> getAllWalkers(){
        return walkerDao.getAllWalkers();
    }

    public WalkerEntity getWalkerByphone(String phone){
        return walkerDao.getWalkerByPhone(phone);
    }

    public TeamEntity getTeamByTeamId(int teamId){
        teamEntity = teamDao.getTeamById(teamId);
        return teamEntity;
    }

    public void insertTeamData(List<TeamEntity> teamEntities){
        appDatabase.teamDao().addTeams(teamEntities.toArray(new TeamEntity[teamEntities.size()]));
    }

    public LiveData<TeamSearchResponse> getTeamDetailById(String teamId){
        teamDetailResponse = apiRepository.getTeamDetails(teamId);
        return teamDetailResponse;
    }

    public LiveData<WalkerSearchResponse> getWalkerById(String walkerId){
        walkerDetailResponse = apiRepository.getWalkerDetails(walkerId);
        return walkerDetailResponse;
    }
}
