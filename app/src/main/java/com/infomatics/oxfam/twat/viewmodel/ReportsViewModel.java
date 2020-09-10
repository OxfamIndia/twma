package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.TeamReportReponse;
import com.infomatics.oxfam.twat.model.reports.PostReportRequest;
import com.infomatics.oxfam.twat.model.reports.ReportResponse;
import com.infomatics.oxfam.twat.model.reports.ReportStatusRequest;
import com.infomatics.oxfam.twat.repository.ApiRepository;

public class ReportsViewModel extends AndroidViewModel {
    private MutableLiveData<ReportResponse> reportsReponse;
    private MutableLiveData<ReportResponse> reportedIncidents;
    private MutableLiveData<BaseResponse> changeStatusResponse;
    private MutableLiveData<BaseResponse> postReport;
    private MutableLiveData<TeamReportReponse> teamProgressResponse;
    private ApiRepository apiRepository;

    public ReportsViewModel(Application application){
        super(application);
        if(reportsReponse != null && reportedIncidents != null && postReport != null
        && teamProgressResponse != null && changeStatusResponse != null)
            return;
        apiRepository = ApiRepository.getInstance();
    }

    public LiveData<TeamReportReponse> getTeamProgress(String type){
        teamProgressResponse = apiRepository.getTeamProgressReport(type);
        return teamProgressResponse;
    }

    public LiveData<ReportResponse> getLostNFound(){
        reportsReponse = apiRepository.getLostNFound();
        return reportsReponse;
    }

    public LiveData<ReportResponse> getReportedIncidents(){
        reportedIncidents = apiRepository.getReportedIncidents();
        return reportedIncidents;
    }

    public LiveData<BaseResponse> changeStatus(ReportStatusRequest reportStatusRequest){
        changeStatusResponse = apiRepository.changeReportStatus(reportStatusRequest);
        return changeStatusResponse;
    }
    public LiveData<BaseResponse> postReport(PostReportRequest postReportRequest, boolean isLostnFound){
        postReport = apiRepository.postReport(postReportRequest, isLostnFound);
        return postReport;
    }
}
