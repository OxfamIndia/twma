package com.infomatics.oxfam.twat.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.dashboard.CPDashboardResponse;
import com.infomatics.oxfam.twat.repository.ApiRepository;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.view.notification.NotificationActivity;
import com.infomatics.oxfam.twat.viewmodel.DashboardDataViewModel;

import java.lang.ref.WeakReference;

public class ReportDataActivity extends AppCompatActivity {


    private TextView tvTeamCheckInCount;
    private TextView tvTeamCheckOutCount;
    private TextView tvTeamRetirementCount;

    private TextView tvWalkerCheckInCount;
    private TextView tvWalkerCheckOutCount;
    private TextView tvWalkerRetirementCount;

    private TextView totalTeamCheckIn, totalTeamCheckOut, totalTeamRetires, totalWalkersCheckIn, totalWalkerCheckOut, totalWalkerRetirement;

    private TextView totalTeams;

    private TextView tvHundred, tvFifty;
    private Toolbar toolbar;
    private TextView tvHeader;
    private DashboardDataViewModel dashboardDataViewModel;
    private ProgressDialog progressDialog;
    private CPDashboardResponse response;
    private TextView offlineOnlineView;
    int cpId = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_data);
        toolbar = findViewById(R.id.toolbar);
        tvHeader =findViewById(R.id.toolbar_text);
        offlineOnlineView = findViewById(R.id.online_offline);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white_icon);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(ReportDataActivity.this), "Please wait...", false);
        dashboardDataViewModel = ViewModelProviders.of(ReportDataActivity.this).get(DashboardDataViewModel.class);
        componentBinding();
    }
    private void toggle100UI(){
        tvHundred.setTextColor(getResources().getColor(R.color.white));
        tvFifty.setTextColor(getResources().getColor(R.color.offwhite));

        tvTeamRetirementCount.setText(""+response.getHundred().getTeam().getRetire());
        tvTeamCheckOutCount.setText(""+response.getHundred().getTeam().getCheckout());
        tvTeamCheckInCount.setText(""+response.getHundred().getTeam().getCheckin());
        tvWalkerRetirementCount.setText(""+response.getHundred().getWalker().getRetire());
        tvWalkerCheckOutCount.setText(""+response.getHundred().getWalker().getCheckout());
        tvWalkerCheckInCount.setText(""+response.getHundred().getWalker().getCheckin());
        totalTeams.setText(""+response.getTeamCount());
        totalTeamCheckIn.setText(""+response.getHundred().getTotalTeams());
        totalTeamCheckOut.setText(""+response.getHundred().getTotalTeams());
        totalTeamRetires.setText(""+response.getHundred().getTotalTeams());
        totalWalkersCheckIn.setText(""+response.getHundred().getTotalWalkers());
        totalWalkerCheckOut.setText(""+response.getHundred().getTotalWalkers());
        totalWalkerRetirement.setText(""+response.getHundred().getTotalWalkers());


    }

    private void toggleData(){

        tvTeamRetirementCount.setText("0");
        tvTeamCheckOutCount.setText("0");
        tvTeamCheckInCount.setText("0");
        tvWalkerRetirementCount.setText("0");
        tvWalkerCheckOutCount.setText("0");
        tvWalkerCheckInCount.setText("0");
        totalTeams.setText("0");

    }

    private void toggle50UI(){
        tvHundred.setTextColor(getResources().getColor(R.color.offwhite));
        tvFifty.setTextColor(getResources().getColor(R.color.white));
        try {
            tvTeamRetirementCount.setText("" + response.getFifty().getTeam().getRetire());
            tvTeamCheckOutCount.setText("" + response.getFifty().getTeam().getCheckout());
            tvTeamCheckInCount.setText("" + response.getFifty().getTeam().getCheckin());
            tvWalkerRetirementCount.setText("" + response.getFifty().getWalker().getRetire());
            tvWalkerCheckOutCount.setText("" + response.getFifty().getWalker().getCheckout());
            tvWalkerCheckInCount.setText("" + response.getFifty().getWalker().getCheckin());
            totalTeams.setText("" + response.getTeamCount());
            totalTeamCheckIn.setText(""+response.getFifty().getTotalTeams());
            totalTeamCheckOut.setText(""+response.getFifty().getTotalTeams());
            totalTeamRetires.setText(""+response.getFifty().getTotalTeams());
            totalWalkersCheckIn.setText(""+response.getFifty().getTotalWalkers());
            totalWalkerCheckOut.setText(""+response.getFifty().getTotalWalkers());
            totalWalkerRetirement.setText(""+response.getFifty().getTotalWalkers());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void componentBinding(){

        totalTeamCheckIn = findViewById(R.id.total_teams_ci);
         totalTeamCheckOut = findViewById(R.id.total_teams_co);
         totalTeamRetires = findViewById(R.id.total_teams_r);
         totalWalkersCheckIn  = findViewById(R.id.total_walkers);
         totalWalkerCheckOut  = findViewById(R.id.total_walkers_co);
         totalWalkerRetirement  = findViewById(R.id.total_walkers_r);
        tvTeamCheckInCount = findViewById(R.id.total_checkin_teams);
        tvTeamCheckOutCount = findViewById(R.id.total_team_check_outs);
        tvTeamRetirementCount = findViewById(R.id.totalTeamRetirement);
        tvWalkerCheckInCount = findViewById(R.id.total_check_ins);
        tvWalkerCheckOutCount = findViewById(R.id.total_check_outs);
        tvWalkerRetirementCount = findViewById(R.id.total_retirements);

        totalTeams = findViewById(R.id.teamCount);
        tvHundred = findViewById(R.id.tvhundred);
        tvFifty = findViewById(R.id.tvFifty);
        tvFifty.setVisibility(View.GONE);
        getDashboardData();

        tvFifty.setOnClickListener(v->{
            toggleData();
            if(response != null)
                toggle50UI();
        });
        tvHundred.setOnClickListener(v->{
            toggleData();
            if(response != null)
                toggle100UI();
        });
        tvHeader.setText("Report (CP "+cpId+")");
    }
    private void getDashboardData(){
        progressDialog.show();
        dashboardDataViewModel.getDashboardData(cpId).observe(ReportDataActivity.this, cpDashboardResponse -> {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(cpDashboardResponse != null && cpDashboardResponse.size() > 0){
                response = cpDashboardResponse.get(0);
                toggle100UI();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.cp1:
                cpId = 1;
                break;
            case R.id.cp2:
                cpId = 2;
                break;
            case R.id.cp3:
                cpId = 3;
                break;
            case R.id.cp4:
                cpId = 4;
                break;
            case R.id.cp5:
                cpId = 5;
                break;
            case R.id.cp6:
                cpId = 6;
                break;
            case R.id.cp7:
                cpId = 7;
                break;
            case R.id.cp8:
                cpId = 8;
                break;
            case R.id.cp9:
                cpId = 9;
                break;
            case R.id.cp10:
                cpId = 10;
                break;
        }
        if(cpId < 5){
            tvFifty.setVisibility(View.GONE);
        }else{
            tvFifty.setVisibility(View.VISIBLE);
        }
        if(item.getItemId() != android.R.id.home) {
            tvHeader.setText("Report (CP " + cpId + ")");
            getDashboardData();
        }
        return super.onOptionsItemSelected(item);
    }


}
