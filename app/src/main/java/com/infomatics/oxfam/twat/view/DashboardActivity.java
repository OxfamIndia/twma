package com.infomatics.oxfam.twat.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.ActionAdapter;
import com.infomatics.oxfam.twat.adapter.NavigationAdapter;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.checkincheckout.ChangeCPStatusRequest;
import com.infomatics.oxfam.twat.model.dashboardhome.DashboardItemModel;
import com.infomatics.oxfam.twat.model.navigation.NavigationIconModel;
import com.infomatics.oxfam.twat.model.room.entity.CheckpointEntity;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.util.DonutProgress;
import com.infomatics.oxfam.twat.util.GridSpacingItemDecoration;
import com.infomatics.oxfam.twat.view.dutyregister.DutyRegisterAcitivity;
import com.infomatics.oxfam.twat.view.login.LoginActivity;
import com.infomatics.oxfam.twat.view.notification.NotificationActivity;
import com.infomatics.oxfam.twat.view.register.RegisterBands;
import com.infomatics.oxfam.twat.view.register.RegisterTeam;
import com.infomatics.oxfam.twat.view.reportincident.RecordIncident;
import com.infomatics.oxfam.twat.view.reportincident.ViewReports;
import com.infomatics.oxfam.twat.view.searchdirectory.SearchDirectoryActivity;
import com.infomatics.oxfam.twat.view.tagscan.TagScanActivity;
import com.infomatics.oxfam.twat.viewmodel.CheckInCheckOutViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DashboardActivity extends BaseActivity implements ItemPositionClickListener {

    private DrawerLayout drawer;
    private TextView txtName, txtWebsite;
    private RecyclerView navRecycler;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<NavigationIconModel> navActions;
    private NavigationAdapter navigationAdapter;
    private Toolbar toolbar;
    private DonutProgress donutProgress;
    private LinearLayout baPhoneDirectory;
    private LinearLayout baLostFound;
    private LinearLayout baReportIncident;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<DashboardItemModel> actions;
    private TextView toolbarText;
    private ActionAdapter adapter;
    private DonutProgress progressOne, progressTwo;
    private TextView offlineOnlineView;
    private ProgressDialog progressDialog;
    CheckInCheckOutViewModel viewModel;

    private Switch networkSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        toolbarText =findViewById(R.id.toolbar_text);
        offlineOnlineView = findViewById(R.id.online_offline);
        setSupportActionBar(toolbar);
        viewModel = ViewModelProviders.of(DashboardActivity.this).get(CheckInCheckOutViewModel.class);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(DashboardActivity.this),"Please wait..." , false);
        componentBinding();
    }

    private void componentBinding(){
        drawer =  findViewById(R.id.drawer_layout);
        txtName = findViewById(R.id.name);
        txtWebsite =  findViewById(R.id.role);
        navRecycler = findViewById(R.id.nav_recycler);
        baPhoneDirectory = findViewById(R.id.phone_directory);
        baReportIncident = findViewById(R.id.report_incident);
        baLostFound = findViewById(R.id.lost_found);
        progressOne = findViewById(R.id.donut_progress);
        progressTwo = findViewById(R.id.donut_progress2);
        networkSwitch = findViewById(R.id.is_network_sync_req);

        progressTwo.setFinishedStrokeColor(Color.rgb(255,129,6));
        progressTwo.setUnfinishedStrokeColor(Color.rgb(255,255,255));
        progressTwo.setTextColor(Color.rgb(255,129,6));

        progressOne.setFinishedStrokeColor(Color.rgb(255,129,6));
        progressOne.setUnfinishedStrokeColor(Color.rgb(255,255,255));
        progressOne.setTextColor(Color.rgb(255,129,6));


        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        navActions = new ArrayList<>();
        navigationAdapter = new NavigationAdapter(navActions, this);
        populateActions();
        navRecycler.setLayoutManager(mLinearLayoutManager);
        navRecycler.setAdapter(navigationAdapter);
        loadNavHeader();
        setUpNavigationView();
        if(AppConstants.USER_ROLE.getId() == 1){
            toolbarText.setText("Dashboard");
        }else {
            toolbarText.setText("CheckPoint "+AppConstants.CHECKPOINT_ID);
        }
        new Handler().postDelayed(() -> {
            int percent = 40;
            donutProgress = findViewById(R.id.donut_progress);
            donutProgress.setProgress(percent);
            donutProgress.setProgressWithAnimation(percent, 20);
        },300);

        recyclerView = findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        actions = new ArrayList<>();
        DashboardItemModel dashboardItemModel;
        if(AppConstants.USER_ROLE.getId() == 1) {
            dashboardItemModel = new DashboardItemModel();
            dashboardItemModel.setActionName("Register Bands");
            dashboardItemModel.setAction("registerband");
            dashboardItemModel.setResourceId(R.drawable.register_bands);
            actions.add(dashboardItemModel);
        }

        dashboardItemModel = new DashboardItemModel();
        dashboardItemModel.setActionName("Search");
        dashboardItemModel.setAction("registerTeam");
        dashboardItemModel.setResourceId(R.drawable.search);
        actions.add(dashboardItemModel);

        dashboardItemModel = new DashboardItemModel();
        dashboardItemModel.setActionName("Band Details");
        dashboardItemModel.setAction("tagDetails");
        dashboardItemModel.setResourceId(R.drawable.band_details);
        actions.add(dashboardItemModel);

        if(AppConstants.USER_ROLE.getId() != 1) {
            dashboardItemModel = new DashboardItemModel();
            dashboardItemModel.setActionName("Check Ins/Check Outs");
            dashboardItemModel.setAction("checkin");
            dashboardItemModel.setResourceId(R.drawable.checkin);
            actions.add(dashboardItemModel);

            dashboardItemModel = new DashboardItemModel();
            dashboardItemModel.setActionName("Retirement");
            dashboardItemModel.setAction("retirement");
            dashboardItemModel.setResourceId(R.drawable.retire);
            actions.add(dashboardItemModel);
        }

        dashboardItemModel = new DashboardItemModel();
        dashboardItemModel.setActionName("Duty Register");
        dashboardItemModel.setAction("dutyregister");
        dashboardItemModel.setResourceId(R.drawable.duty_register);
        actions.add(dashboardItemModel);

        dashboardItemModel = new DashboardItemModel();
        dashboardItemModel.setActionName("CP Reports");
        dashboardItemModel.setAction("reports");
        dashboardItemModel.setResourceId(R.drawable.cp_report);
        actions.add(dashboardItemModel);

        dashboardItemModel = new DashboardItemModel();
        dashboardItemModel.setActionName("Team Progress");
        dashboardItemModel.setAction("teamreport");
        dashboardItemModel.setResourceId(R.drawable.team_report);
        actions.add(dashboardItemModel);

        dashboardItemModel = new DashboardItemModel();
        dashboardItemModel.setActionName("Retirements");
        dashboardItemModel.setAction("retirements");
        dashboardItemModel.setResourceId(R.drawable.reports);
        actions.add(dashboardItemModel);


        adapter = new ActionAdapter(actions, position -> {
            switch (actions.get(position).getAction()){
                case "checkin":
                case "checkout":
                    startActivity(new Intent(DashboardActivity.this, CheckInCheckOutActivity.class));
                    if(AppConstants.USER_ROLE.getId() != 1)
                        DashboardActivity.this.finish();
                    break;
                case "retirement":
                    startActivity(new Intent(DashboardActivity.this, TagScanActivity.class)
                            .putExtra(AppConstants.SCREEN_NAME,actions.get(position).getActionName()));
                    break;
                case "dutyregister":
                    startActivity(new Intent(DashboardActivity.this, DutyRegisterAcitivity.class));
                    break;
                case "registerband":
                    if(AppConstants.USER_ROLE.getId() == 1)
                        startActivity(new Intent(DashboardActivity.this, RegisterBands.class));
                    break;
                case "registerTeam":
                    if(ApplicationUtils.isNetworkAvailable(DashboardActivity.this)) {
                        startActivity(new Intent(DashboardActivity.this, RegisterTeam.class));
                    }else{
                        ApplicationUtils.showToast(DashboardActivity.this, "This functionality is not available in offline mode");
                    }
                    break;
                case "tagDetails":
                    startActivity(new Intent(DashboardActivity.this, TagScanActivity.class)
                            .putExtra(AppConstants.SCREEN_NAME,"Band Details")
                            .putExtra(AppConstants.TAG_DETAILS, true));
                    break;
                case "reports":
                    startActivity(new Intent(DashboardActivity.this, ReportDataActivity.class));
                    break;
                case "teamreport":
                    startActivity(new Intent(DashboardActivity.this, CPReportActivity.class));
                    break;
                case "retirements":
                    startActivity(new Intent(DashboardActivity.this, RetirementActivity.class));

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));

        baPhoneDirectory.setOnClickListener(v->{
            startActivity(new Intent(DashboardActivity.this, SearchDirectoryActivity.class));
        });

        baReportIncident.setOnClickListener(v->{
            startActivity(new Intent(DashboardActivity.this, ViewReports.class)
                    .putExtra(AppConstants.IS_LOST_FOUND, false));
            /*if(AppConstants.USER_ROLE.getId() == 1){
                startActivity(new Intent(DashboardActivity.this, ViewReports.class)
                        .putExtra(AppConstants.IS_LOST_FOUND, false));
            }else {
                startActivity(new Intent(DashboardActivity.this, RecordIncident.class));
            }*/
        });
        baLostFound.setOnClickListener(v->{
            startActivity(new Intent(DashboardActivity.this, ViewReports.class)
                    .putExtra(AppConstants.IS_LOST_FOUND, true));
        });

        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.APP_PREF, MODE_PRIVATE);
        if(sharedPreferences.contains(AppConstants.NETWORK_KEY)){
            networkSwitch.setChecked(sharedPreferences.getBoolean(AppConstants.NETWORK_KEY, false));
        }else{
            sharedPreferences.edit().putBoolean(AppConstants.NETWORK_KEY, false).apply();
        }

        networkSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                sharedPreferences.edit().putBoolean(AppConstants.NETWORK_KEY, b).apply();
        });

        networkSwitch.setVisibility(View.GONE);


    }

    private void populateActions(){
        NavigationIconModel navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("home");
        navigationIconModel.setResourceId(R.drawable.ic_home_black_24dp);
        navigationIconModel.setTitle("Home");
        navActions.add(navigationIconModel);
        if(AppConstants.USER_ROLE.getId() != 1) {
            navigationIconModel = new NavigationIconModel();
            navigationIconModel.setAction("checkin");
            navigationIconModel.setResourceId(R.drawable.ic_watch_gray);
            navigationIconModel.setTitle("Check Ins/Check Outs");
            navActions.add(navigationIconModel);
        }

       /* navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("checkout");
        navigationIconModel.setResourceId(R.drawable.ic_home_black_24dp);
        navigationIconModel.setTitle("Check Outs");
        navActions.add(navigationIconModel);
*/
        navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("tagdetails");
        navigationIconModel.setResourceId(R.drawable.ic_watch_gray);
        navigationIconModel.setTitle("Band Details");
        navActions.add(navigationIconModel);
        if(AppConstants.USER_ROLE != null) {
            if (AppConstants.USER_ROLE.getId() == 1) {
                navigationIconModel = new NavigationIconModel();
                navigationIconModel.setAction("registerband");
                navigationIconModel.setResourceId(R.drawable.ic_watch_gray);
                navigationIconModel.setTitle("Register Bib");
                navActions.add(navigationIconModel);
            }
        }
        navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("registerTeam");
        navigationIconModel.setResourceId(R.drawable.ic_team_gray);
        navigationIconModel.setTitle("Search");
        navActions.add(navigationIconModel);
        if(AppConstants.USER_ROLE.getId() != 1) {
            navigationIconModel = new NavigationIconModel();
            navigationIconModel.setAction("retirement");
            navigationIconModel.setResourceId(R.drawable.ic_watch_gray);
            navigationIconModel.setTitle("Retirement");
            navActions.add(navigationIconModel);
        }

        navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("searchdirectory");
        navigationIconModel.setResourceId(R.drawable.ic_contact_phone_black_24dp);
        navigationIconModel.setTitle("Event Directory");
        navActions.add(navigationIconModel);

        navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("dutyregister");
        navigationIconModel.setResourceId(R.drawable.ic_register_gray);
        navigationIconModel.setTitle("Duty Register");
        navActions.add(navigationIconModel);

        navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("reports");
        navigationIconModel.setResourceId(R.drawable.ic_register_gray);
        navigationIconModel.setTitle("CP Reports");
        navActions.add(navigationIconModel);

        navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("teamProgress");
        navigationIconModel.setResourceId(R.drawable.ic_register_gray);
        navigationIconModel.setTitle("Team Progress");
        navActions.add(navigationIconModel);
        if(AppConstants.USER_ROLE.getId() != 1) {
            navigationIconModel = new NavigationIconModel();
            navigationIconModel.setAction("closecp");
            navigationIconModel.setResourceId(R.drawable.ic_logout);
            navigationIconModel.setTitle("Close CP");
            navActions.add(navigationIconModel);
        }

        navigationIconModel = new NavigationIconModel();
        navigationIconModel.setAction("logout");
        navigationIconModel.setResourceId(R.drawable.ic_logout);
        navigationIconModel.setTitle("Logout");
        navActions.add(navigationIconModel);

        navigationAdapter.notifyDataSetChanged();
    }
    private void loadNavHeader() {

        txtName.setText(AppConstants.FIRST_NAME+" "+AppConstants.LAST_NAME);
        if(AppConstants.USER_ROLE != null)
            txtWebsite.setText(AppConstants.USER_ROLE.getRole());
        else
            txtWebsite.setText("");
    }

    private void setUpNavigationView() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }


        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_notification){
            startActivity(new Intent(DashboardActivity.this, NotificationActivity.class));
            return true;
        }

        if(item.getItemId() == R.id.action_refresh){
            AppConstants.getData = false;
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isConnectedToInternet.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                runOnUiThread(() -> {
                    if(isConnectedToInternet.get()){
                        offlineOnlineView.setVisibility(View.GONE);
                    }else{
                        offlineOnlineView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        runOnUiThread(() -> {
            if(isConnectedToInternet.get()){
                offlineOnlineView.setVisibility(View.GONE);
            }else{
                offlineOnlineView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        new Handler().postDelayed(() -> {
            switch (navActions.get(position).getAction()){
                case "checkin":
                case "checkout":
                    startActivity(new Intent(DashboardActivity.this, CheckInCheckOutActivity.class));
                    if(AppConstants.USER_ROLE.getId() != 1)
                        DashboardActivity.this.finish();
                    break;
                case "retirement":
                    startActivity(new Intent(DashboardActivity.this, TagScanActivity.class)
                            .putExtra(AppConstants.SCREEN_NAME,navActions.get(position).getTitle()));
                    break;
                case "dutyregister":
                    startActivity(new Intent(DashboardActivity.this, DutyRegisterAcitivity.class));
                    break;
                case "home":
                    break;
                case "searchdirectory":
                    startActivity(new Intent(DashboardActivity.this, SearchDirectoryActivity.class));
                    break;
                case "registerband":
                    if(ApplicationUtils.isNetworkAvailable(DashboardActivity.this)) {
                        startActivity(new Intent(DashboardActivity.this, RegisterBands.class));
                    }else{
                        ApplicationUtils.showToast(DashboardActivity.this, "This functionality is not available in offline mode");
                    }
                    break;
                case "registerTeam":
                    startActivity(new Intent(DashboardActivity.this, RegisterTeam.class));
                    break;
                case "tagdetails":
                    startActivity(new Intent(DashboardActivity.this, TagScanActivity.class)
                            .putExtra(AppConstants.SCREEN_NAME,"Band Details")
                            .putExtra(AppConstants.TAG_DETAILS, true));
                    break;
                case "logout":
                    SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.APP_PREF, MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean(AppConstants.IS_LOGGED_IN, false).apply();
                    ApplicationUtils.showToast(DashboardActivity.this, "Logged Out");
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                    DashboardActivity.this.finish();
                    break;
                case "reports":
                    startActivity(new Intent(DashboardActivity.this, ReportDataActivity.class));
                    break;
                case "teamProgress":
                    startActivity(new Intent(DashboardActivity.this, CPReportActivity.class));
                    break;
                case"closecp":
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            DashboardActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("Close CP?");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Click yes to close this check point. you will not be able to receive any further checkins and checkouts will not be permitted! \n Are you sure?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", (dialog, id) -> {
                                dialog.cancel();
                                closeCP();
                            })
                            .setNegativeButton("No", (dialog, id) -> {
                                dialog.cancel();
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    break;
            }
        },300);
    }

    private void closeCP(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }

        ChangeCPStatusRequest changeCPStatusRequest = new ChangeCPStatusRequest();
        changeCPStatusRequest.setId(Integer.toString(AppConstants.CHECKPOINT_ID));
        changeCPStatusRequest.setStatus("CLOSED");
        changeCPStatusRequest.setRemark("CP HAS BEEN CLOSED!");
        viewModel.changeCpStatus(changeCPStatusRequest).observe(DashboardActivity.this, response->{
            if(progressDialog.isShowing()){
                progressDialog.cancel();
            }
            CheckpointEntity checkpointEntity = viewModel.getCPData(AppConstants.CHECKPOINT_ID);
            if(checkpointEntity != null){
                checkpointEntity.setCpStatus("CLOSED");
                viewModel.updateCPData(checkpointEntity);
            }
            ApplicationUtils.showToast(DashboardActivity.this, "Check Point has been closed.");
        });
    }
}


