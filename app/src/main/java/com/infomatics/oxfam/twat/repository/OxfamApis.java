package com.infomatics.oxfam.twat.repository;

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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OxfamApis {

    @POST("users/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("users/forgotPassword")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("notification/saveToken")
    Call<BaseResponse> updateToken(@Body FCMRequest fcmRequest);

    @GET("participants/getAllRfids")
    Call<AllRfidResponse> getAllRfids();

    @POST("progress/checkInOut")
    Call<CheckInCheckOutResponse> syncCheckInCheckOut(@Body ArrayList<CheckInCheckOutBean> checkOutBeanArrayList);

    @GET("team/getAll")
    Call<AllTeamsResponse> getAllTeams();

    @GET("lostFound")
    Call<ReportResponse> getLostnFound();

    @POST("lostFound")
    Call<BaseResponse> postLostnFound(@Body PostReportRequest postReportRequest);

    @POST("incidents")
    Call<BaseResponse> postIncident(@Body PostReportRequest postReportRequest);

    @GET("incidents")
    Call<ReportResponse> getReportedIncidents();

    @POST("participants/mapRfid")
    Call<BaseResponse> mapRfid(@Body ArrayList<RegisterModel> registerModels);

    @GET("role")
    Call<RoleResponse> getAllRoles();

    @GET("eventDirectory/getData")
    Call<SearchDirectoryResponse> getAllContacts();

    @POST("eventDirectory/saveOrUpdate")
    Call<BaseResponse> saveContact(@Body ContactModel contactModel);

    @POST("checkpoint/changeStatus")
    Call<BaseResponse> changeCPStatus(@Body ChangeCPStatusRequest changeCPStatusRequest);

    @GET("checkpoint/{checkpointId}")
    Call<CPStatusResponse> getCPStatus(@Path("checkpointId") int checkpointId);

    @GET("progress/{checkpointId}")
    Call<CPEntryResponse> getCpDetails(@Path("checkpointId") int checkpointId);

    @GET("checkpoint/getDashBoardCheckpoints/{checkpointId}")
    Call<ArrayList<CPDashboardResponse>> getDashboardData(@Path("checkpointId") int checkpointId);

    @GET("participants/getAllBibno")
    Call<AllBibNosResponse> getAllBibNos();

    @GET("checkpoint")
    Call<DutyRegisterResponse> getDutyRegister();

    @GET("users/get-duty-user")
    Call<DutyResponse> getAllUsersForDuty();

    @POST("users/change-user-duty")
    Call<BaseResponse> changeUserDuty(@Body ChangeDutyRequest changeDutyRequest);

    @GET("reports/top-teams")
    Call<TeamReportReponse> getTeamReport(@Query("type") String type);

    @GET("progress/getAllRetirements")
    Call<RetirementResponse> getAllRetirements();

    @POST("lostFound/changeStatus")
    Call<BaseResponse> changeReportStatus(@Body ReportStatusRequest reportStatusRequest);

    @GET("team/teamDetails/{teamId}")
    Call<TeamSearchResponse> getTeamDetails(@Path("teamId") String teamId);

    @GET("walker/getDetail/{walkerId}")
    Call<WalkerSearchResponse> getWalkerDetail(@Path("walkerId") String walkerId);

    @GET("progress/getAllRetirements/{id}")
    Call<RetirementResponse> getAllRetirements(@Path("id") String id);

}
