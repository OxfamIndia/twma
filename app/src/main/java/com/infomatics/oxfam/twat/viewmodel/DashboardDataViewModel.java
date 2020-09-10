package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.infomatics.oxfam.twat.model.dashboard.CPDashboardResponse;
import com.infomatics.oxfam.twat.repository.ApiRepository;

import java.util.ArrayList;

public class DashboardDataViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<CPDashboardResponse>> dashboardResponse;
    private ApiRepository apiRepository;
    public DashboardDataViewModel(Application application){
        super(application);
        if(dashboardResponse != null){
            return;
        }
        apiRepository = ApiRepository.getInstance();
    }

    public LiveData<ArrayList<CPDashboardResponse>> getDashboardData(int cpId){
        dashboardResponse = apiRepository.getDashboardData(cpId);
        return dashboardResponse;
    }
}
