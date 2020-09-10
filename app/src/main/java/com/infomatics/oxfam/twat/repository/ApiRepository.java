package com.infomatics.oxfam.twat.repository;

import android.arch.lifecycle.MutableLiveData;

import com.infomatics.oxfam.twat.model.AllBibNosResponse;
import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.CPEntryResponse;
import com.infomatics.oxfam.twat.model.RoleResponse;
import com.infomatics.oxfam.twat.model.TeamReportReponse;
import com.infomatics.oxfam.twat.model.checkincheckout.CPStatusResponse;
import com.infomatics.oxfam.twat.model.checkincheckout.ChangeCPStatusRequest;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutBean;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutResponse;
import com.infomatics.oxfam.twat.model.contact.ContactModel;
import com.infomatics.oxfam.twat.model.contact.SearchDirectoryResponse;
import com.infomatics.oxfam.twat.model.dashboard.CPDashboardResponse;
import com.infomatics.oxfam.twat.model.dutyregister.ChangeDutyRequest;
import com.infomatics.oxfam.twat.model.dutyregister.DutyRegisterResponse;
import com.infomatics.oxfam.twat.model.dutyregister.DutyResponse;
import com.infomatics.oxfam.twat.model.fcm.FCMRequest;
import com.infomatics.oxfam.twat.model.login.ForgotPasswordRequest;
import com.infomatics.oxfam.twat.model.login.ForgotPasswordResponse;
import com.infomatics.oxfam.twat.model.login.LoginRequest;
import com.infomatics.oxfam.twat.model.login.LoginResponse;
import com.infomatics.oxfam.twat.model.register.RegisterModel;
import com.infomatics.oxfam.twat.model.reports.PostReportRequest;
import com.infomatics.oxfam.twat.model.reports.ReportResponse;
import com.infomatics.oxfam.twat.model.reports.ReportStatusRequest;
import com.infomatics.oxfam.twat.model.retirement.RetirementResponse;
import com.infomatics.oxfam.twat.model.search.TeamSearchResponse;
import com.infomatics.oxfam.twat.model.search.WalkerSearchResponse;
import com.infomatics.oxfam.twat.model.team.AllRfidResponse;
import com.infomatics.oxfam.twat.model.team.AllTeamsResponse;
import com.infomatics.oxfam.twat.util.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {
    public static ApiRepository apiRepository;
    private OxfamApis oxfamApis;
    public static ApiRepository getInstance(){
        if(apiRepository == null){
            apiRepository = new ApiRepository();
        }
        return apiRepository;
    }

    public ApiRepository(){
        oxfamApis = RetrofitService.cteateService(OxfamApis.class);
    }

    public MutableLiveData<RetirementResponse> getAllRetirements(){
        MutableLiveData<RetirementResponse> allRetirements = new MutableLiveData<>();
        oxfamApis.getAllRetirements().enqueue(new Callback<RetirementResponse>() {
            @Override
            public void onResponse(Call<RetirementResponse> call, Response<RetirementResponse> response) {
                if(response.isSuccessful())
                    allRetirements.setValue(response.body());
                else
                    allRetirements.setValue(null);
            }

            @Override
            public void onFailure(Call<RetirementResponse> call, Throwable t) {
                allRetirements.setValue(null);
            }
        });
        return allRetirements;
    }

    public MutableLiveData<ArrayList<CPDashboardResponse>> getDashboardData(int cpId){
        MutableLiveData<ArrayList<CPDashboardResponse>> dashboardResponse = new MutableLiveData<>();
        oxfamApis.getDashboardData(cpId).enqueue(new Callback<ArrayList<CPDashboardResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CPDashboardResponse>> call, Response<ArrayList<CPDashboardResponse>> response) {
                if (response.isSuccessful()){
                    dashboardResponse.setValue(response.body());
                }else{
                    dashboardResponse.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<CPDashboardResponse>> call, Throwable t) {
                dashboardResponse.setValue(null);
            }
        });
        return dashboardResponse;
    }
    public MutableLiveData<LoginResponse> login(LoginRequest loginRequest){
        MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
        oxfamApis.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    loginResponse.setValue(response.body());
                }else{
                    loginResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.setValue(null);
            }
        });
        return loginResponse;
    }

    public void updateFCM(String token){
        FCMRequest fcmRequest = new FCMRequest();
        fcmRequest.setRoleId(AppConstants.USER_ROLE.getId());
        fcmRequest.setToken(token);
        fcmRequest.setUserId(AppConstants.USER_ID);
        oxfamApis.updateToken(fcmRequest).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<TeamReportReponse> getTeamProgressReport(String type){
        MutableLiveData<TeamReportReponse> getTeamResponse = new MutableLiveData<>();
        oxfamApis.getTeamReport(type).enqueue(new Callback<TeamReportReponse>() {
            @Override
            public void onResponse(Call<TeamReportReponse> call, Response<TeamReportReponse> response) {
                if(response.isSuccessful())
                    getTeamResponse.setValue(response.body());
                else
                    getTeamResponse.setValue(null);
            }

            @Override
            public void onFailure(Call<TeamReportReponse> call, Throwable t) {
                getTeamResponse.setValue(null);
            }
        });
        return getTeamResponse;
    }
    public MutableLiveData<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest){
        MutableLiveData<ForgotPasswordResponse> forgotResponse = new MutableLiveData<>();
        oxfamApis.forgotPassword(forgotPasswordRequest).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if(response.isSuccessful()){
                    forgotResponse.setValue(response.body());
                }else{
                    forgotResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                forgotResponse.setValue(null);
            }
        });
        return forgotResponse;
    }

    public MutableLiveData<AllBibNosResponse> getAllBibNos(){
        MutableLiveData<AllBibNosResponse> allBibs = new MutableLiveData<>();
        oxfamApis.getAllBibNos().enqueue(new Callback<AllBibNosResponse>() {
            @Override
            public void onResponse(Call<AllBibNosResponse> call, Response<AllBibNosResponse> response) {
                if(response.isSuccessful())
                    allBibs.setValue(response.body());
                else
                    allBibs.setValue(null);
            }

            @Override
            public void onFailure(Call<AllBibNosResponse> call, Throwable t) {
                allBibs.setValue(null);
            }
        });
        return allBibs;
    }

    public MutableLiveData<DutyRegisterResponse> getDutyRegister(){
        MutableLiveData<DutyRegisterResponse> dutyRegisterResponse = new MutableLiveData<>();
        oxfamApis.getDutyRegister().enqueue(new Callback<DutyRegisterResponse>() {
            @Override
            public void onResponse(Call<DutyRegisterResponse> call, Response<DutyRegisterResponse> response) {
                if(response.isSuccessful())
                    dutyRegisterResponse.setValue(response.body());
                else
                    dutyRegisterResponse.setValue(null);
            }

            @Override
            public void onFailure(Call<DutyRegisterResponse> call, Throwable t) {
                dutyRegisterResponse.setValue(null);
            }
        });
        return dutyRegisterResponse;
    }

    public MutableLiveData<AllRfidResponse> getAllRfids(){
        MutableLiveData<AllRfidResponse> allRfids = new MutableLiveData<>();
        oxfamApis.getAllRfids().enqueue(new Callback<AllRfidResponse>() {
            @Override
            public void onResponse(Call<AllRfidResponse> call, Response<AllRfidResponse> response) {
                if(response.isSuccessful())
                    allRfids.setValue(response.body());
                else
                    allRfids.setValue(null);
            }

            @Override
            public void onFailure(Call<AllRfidResponse> call, Throwable t) {
                allRfids.setValue(null);
            }
        });
        return allRfids;
    }

    public MutableLiveData<DutyResponse> getAllUsersForDuty(){
        MutableLiveData<DutyResponse> allDuties = new MutableLiveData<>();
        oxfamApis.getAllUsersForDuty().enqueue(new Callback<DutyResponse>() {
            @Override
            public void onResponse(Call<DutyResponse> call, Response<DutyResponse> response) {
                if(response.isSuccessful())
                    allDuties.setValue(response.body());
                else
                    allDuties.setValue(null);
            }

            @Override
            public void onFailure(Call<DutyResponse> call, Throwable t) {
                allDuties.setValue(null);
            }
        });
        return allDuties;
    }

    public MutableLiveData<BaseResponse> changeUserDuty(ChangeDutyRequest changeDutyRequest){
        MutableLiveData<BaseResponse> changeDuty = new MutableLiveData<>();
        oxfamApis.changeUserDuty(changeDutyRequest).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful())
                    changeDuty.setValue(response.body());
                else
                    changeDuty.setValue(null);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                changeDuty.setValue(null);
            }
        });
        return changeDuty;
    }
    public MutableLiveData<CPEntryResponse> getCheckpointProgress(int checkpointId){
        MutableLiveData<CPEntryResponse> allRfids = new MutableLiveData<>();
        oxfamApis.getCpDetails(checkpointId).enqueue(new Callback<CPEntryResponse>() {
            @Override
            public void onResponse(Call<CPEntryResponse> call, Response<CPEntryResponse> response) {
                if(response.isSuccessful())
                    allRfids.setValue(response.body());
                else
                    allRfids.setValue(null);
            }

            @Override
            public void onFailure(Call<CPEntryResponse> call, Throwable t) {
                allRfids.setValue(null);
            }
        });
        return allRfids;
    }

    public MutableLiveData<CheckInCheckOutResponse> syncCheckInCheckOut(ArrayList<CheckInCheckOutBean> checkInCheckOutBeans){
        MutableLiveData<CheckInCheckOutResponse> syncResponse = new MutableLiveData<>();
        oxfamApis.syncCheckInCheckOut(checkInCheckOutBeans).enqueue(new Callback<CheckInCheckOutResponse>() {
            @Override
            public void onResponse(Call<CheckInCheckOutResponse> call, Response<CheckInCheckOutResponse> response) {
                if(response.isSuccessful())
                    syncResponse.setValue(response.body());
                else
                    syncResponse.setValue(null);
            }

            @Override
            public void onFailure(Call<CheckInCheckOutResponse> call, Throwable t) {
                syncResponse.setValue(null);
            }
        });
        return syncResponse;
    }

    public MutableLiveData<AllTeamsResponse> getAllTeams(){
        MutableLiveData<AllTeamsResponse> allTeams = new MutableLiveData<>();
        oxfamApis.getAllTeams().enqueue(new Callback<AllTeamsResponse>() {
            @Override
            public void onResponse(Call<AllTeamsResponse> call, Response<AllTeamsResponse> response) {
                if(response.isSuccessful()){
                    allTeams.setValue(response.body());
                }else{
                    allTeams.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AllTeamsResponse> call, Throwable t) {
                allTeams.setValue(null);
            }
        });
        return allTeams;
    }

    public MutableLiveData<ReportResponse> getLostNFound(){
        MutableLiveData<ReportResponse> reports = new MutableLiveData<>();
        oxfamApis.getLostnFound().enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if(response.isSuccessful())
                    reports.setValue(response.body());
                else
                    reports.setValue(null);
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                reports.setValue(null);
            }
        });
        return reports;
    }

    public MutableLiveData<ReportResponse> getReportedIncidents(){
        MutableLiveData<ReportResponse> reports = new MutableLiveData<>();
        oxfamApis.getReportedIncidents().enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if(response.isSuccessful())
                    reports.setValue(response.body());
                else
                    reports.setValue(null);
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                reports.setValue(null);
            }
        });
        return reports;
    }
    public MutableLiveData<BaseResponse> mapRfids(ArrayList<RegisterModel> registerModels){
        MutableLiveData<BaseResponse> res = new MutableLiveData<>();
        oxfamApis.mapRfid(registerModels).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful())
                    res.setValue(response.body());
                else
                    res.setValue(null);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
        return res;
    }

    public MutableLiveData<BaseResponse> postReport(PostReportRequest postReportRequest, boolean isLostnFound){
        MutableLiveData<BaseResponse> postResponse = new MutableLiveData<>();
        if(isLostnFound){
            oxfamApis.postLostnFound(postReportRequest).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(response.isSuccessful())
                        postResponse.setValue(response.body());
                    else
                        postResponse.setValue(null);
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    postResponse.setValue(null);
                }
            });
        }else{
            oxfamApis.postIncident(postReportRequest).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(response.isSuccessful())
                        postResponse.setValue(response.body());
                    else
                        postResponse.setValue(null);
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    postResponse.setValue(null);
                }
            });
        }
        return postResponse;
    }

    public MutableLiveData<RoleResponse> getAllRoles(){
        MutableLiveData<RoleResponse> roles = new MutableLiveData<>();
        oxfamApis.getAllRoles().enqueue(new Callback<RoleResponse>() {
            @Override
            public void onResponse(Call<RoleResponse> call, Response<RoleResponse> response) {
                if(response.isSuccessful())
                    roles.setValue(response.body());
                else
                    roles.setValue(null);
            }

            @Override
            public void onFailure(Call<RoleResponse> call, Throwable t) {
                roles.setValue(null);
            }
        });
        return roles;
    }
    public MutableLiveData<SearchDirectoryResponse> getAllContacts(){
        MutableLiveData<SearchDirectoryResponse> contacts = new MutableLiveData<>();
        oxfamApis.getAllContacts().enqueue(new Callback<SearchDirectoryResponse>() {
            @Override
            public void onResponse(Call<SearchDirectoryResponse> call, Response<SearchDirectoryResponse> response) {
                if(response.isSuccessful())
                    contacts.setValue(response.body());
                else
                    contacts.setValue(null);
            }

            @Override
            public void onFailure(Call<SearchDirectoryResponse> call, Throwable t) {
                contacts.setValue(null);
            }
        });
        return contacts;
    }

    public MutableLiveData<BaseResponse> saveContact(ContactModel contactModel){
        MutableLiveData<BaseResponse> res = new MutableLiveData<>();
        oxfamApis.saveContact(contactModel).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful())
                    res.setValue(response.body());
                else{
                    res.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
        return res;
    }

    public MutableLiveData<BaseResponse> changeCPStatus(ChangeCPStatusRequest changeCPStatusRequest){
        MutableLiveData<BaseResponse> res = new MutableLiveData<>();
        oxfamApis.changeCPStatus(changeCPStatusRequest).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful())
                    res.setValue(response.body());
                else{
                    res.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
        return res;
    }
    public MutableLiveData<CPStatusResponse> getCPStatus(int checkPointId){
        MutableLiveData<CPStatusResponse> res = new MutableLiveData<>();
        oxfamApis.getCPStatus(checkPointId).enqueue(new Callback<CPStatusResponse>() {
            @Override
            public void onResponse(Call<CPStatusResponse> call, Response<CPStatusResponse> response) {
                if(response.isSuccessful())
                    res.setValue(response.body());
                else{
                    res.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CPStatusResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
        return res;
    }

    public MutableLiveData<BaseResponse> changeReportStatus(ReportStatusRequest reportStatusRequest){
        MutableLiveData<BaseResponse> res = new MutableLiveData<>();
        oxfamApis.changeReportStatus(reportStatusRequest).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful())
                    res.setValue(response.body());
                else
                    res.setValue(null);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
        return res;
    }

    public MutableLiveData<TeamSearchResponse> getTeamDetails(String teamId){
        MutableLiveData<TeamSearchResponse> res = new MutableLiveData<>();
        oxfamApis.getTeamDetails(teamId).enqueue(new Callback<TeamSearchResponse>() {
            @Override
            public void onResponse(Call<TeamSearchResponse> call, Response<TeamSearchResponse> response) {
                if(response.isSuccessful())
                    res.setValue(response.body());
                else
                    res.setValue(null);
            }

            @Override
            public void onFailure(Call<TeamSearchResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
        return res;
    }

    public MutableLiveData<WalkerSearchResponse> getWalkerDetails(String walkerId){
        MutableLiveData<WalkerSearchResponse> res = new MutableLiveData<>();
        oxfamApis.getWalkerDetail(walkerId).enqueue(new Callback<WalkerSearchResponse>() {
            @Override
            public void onResponse(Call<WalkerSearchResponse> call, Response<WalkerSearchResponse> response) {
                if(response.isSuccessful())
                    res.setValue(response.body());
                else
                    res.setValue(null);
            }

            @Override
            public void onFailure(Call<WalkerSearchResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
        return res;
    }

    public MutableLiveData<RetirementResponse> getAllRetirements(String id){
        MutableLiveData<RetirementResponse> res = new MutableLiveData<>();
        oxfamApis.getAllRetirements(id).enqueue(new Callback<RetirementResponse>() {
            @Override
            public void onResponse(Call<RetirementResponse> call, Response<RetirementResponse> response) {
                if(response.isSuccessful()){
                    res.setValue(response.body());
                }else{
                    res.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<RetirementResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
        return res;
    }
}
