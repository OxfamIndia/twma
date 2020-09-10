package com.infomatics.oxfam.twat.view.reportincident;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.ReportViewAdapter;
import com.infomatics.oxfam.twat.model.reports.Datalist;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.viewmodel.ReportsViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ViewReports extends BaseActivity {

    private ImageView btnBack;
    private TextView tvHeaderText;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private LinearLayoutManager mLinearLayoutManager;
    private ReportViewAdapter reportsAdapter;
    private ArrayList<Datalist> reports;
    private ProgressDialog progressDialog;
    private ReportsViewModel reportsViewModel;
    private boolean isLostnFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);
        reportsViewModel = ViewModelProviders.of(ViewReports.this).get(ReportsViewModel.class);
        componentBinding();
    }

    private void componentBinding(){
        btnBack = findViewById(R.id.btn_back);
        tvHeaderText = findViewById(R.id.tv_header_text);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.add_report);
        mLinearLayoutManager = new LinearLayoutManager(ViewReports.this, LinearLayoutManager.VERTICAL, false);
        reports = new ArrayList<>();

        recyclerView.setLayoutManager(mLinearLayoutManager);
        isLostnFound = getIntent().getBooleanExtra(AppConstants.IS_LOST_FOUND,false);
        reportsAdapter = new ReportViewAdapter(reports);
        recyclerView.setAdapter(reportsAdapter);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(ViewReports.this), "Please wait...", false);
        fab.setOnClickListener(v->{
            if(!isLostnFound){
                startActivity(new Intent(ViewReports.this, RecordIncident.class));
                ViewReports.this.finish();
            }else{
                startActivity(new Intent(ViewReports.this, RecordIncident.class)
                        .putExtra(AppConstants.IS_LOST_FOUND, true));
                ViewReports.this.finish();
            }
        });

        if(isLostnFound) {
            getLostnFound();
            tvHeaderText.setText("Lost And Found");
        }
        else{
            tvHeaderText.setText("Reported Incidents");
          //  if(AppConstants.ROLE.equalsIgnoreCase("admin")){
                getIncidents();
          //  }
        }

        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(v->{
            ViewReports.this.finish();
        });

    }

    public boolean isLostnFound(){
        return isLostnFound;
    }

    private void getIncidents(){
        progressDialog.show();
        reportsViewModel.getReportedIncidents().observe(ViewReports.this, response->{
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(response != null){
                if(response.getDatalist()!= null && response.getDatalist().size() > 0){
                    reports.addAll(response.getDatalist());
                    reportsAdapter.notifyDataSetChanged();
                }else{
                    ApplicationUtils.showToast(ViewReports.this, "No Records Found");
                }
            }
        });
    }
    private void getLostnFound(){
        progressDialog.show();
        reportsViewModel.getLostNFound().observe(ViewReports.this, response->{
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(response != null){
                if(response.getDatalist()!= null && response.getDatalist().size() > 0){
                    reports.addAll(response.getDatalist());
                    reportsAdapter.notifyDataSetChanged();
                }else{
                    ApplicationUtils.showToast(ViewReports.this, "No Records Found");
                }
            }
        });
    }
}
