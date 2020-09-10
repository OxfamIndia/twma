package com.infomatics.oxfam.twat.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.TeamProgressAdapter;
import com.infomatics.oxfam.twat.model.ReportList;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.viewmodel.ReportsViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CPReportActivity extends BaseActivity {

    private ImageView btnBack;
    private TextView tvHeader;
    private RecyclerView recyclerView;
    private ArrayList<ReportList> reportList;
    private ProgressDialog progressDialog;
    private TeamProgressAdapter teamProgressAdapter;
    private ReportsViewModel reportsViewModel;
    private LinearLayoutManager linearLayoutManager;
    private TextView tvFifty;
    private TextView tvHundred;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpreport);
        reportsViewModel = ViewModelProviders.of(CPReportActivity.this).get(ReportsViewModel.class);
        componentBinding();
    }

    private void componentBinding(){
        tvHeader = findViewById(R.id.tv_header_text);
        btnBack = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.report_recycler);
        tvFifty = findViewById(R.id.tvFifty);
        reportList = new ArrayList<>();
        tvHundred = findViewById(R.id.tvHundred);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(CPReportActivity.this)
                , "Please wait...", false);

        tvHeader.setText("Team Progress");
        btnBack.setVisibility(View.VISIBLE);

        teamProgressAdapter = new TeamProgressAdapter(reportList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(teamProgressAdapter);
       checkNetworkAndCall("100");

        tvFifty.setOnClickListener(v->{
            tvFifty.setTextColor(getResources().getColor(R.color.white));
            tvHundred.setTextColor(getResources().getColor(R.color.offwhite ));
            checkNetworkAndCall("50");
        });

        tvHundred.setOnClickListener(v->{
            tvFifty.setTextColor(getResources().getColor(R.color.offwhite));
            tvHundred.setTextColor(getResources().getColor(R.color.white ));
            checkNetworkAndCall("100");
        });

        btnBack.setOnClickListener(v->{
            CPReportActivity.this.finish();
        });
    }

    private void checkNetworkAndCall(String type){
        if(isConnectedToInternet.get()){
            getData(type);
        }
        isConnectedToInternet.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(isConnectedToInternet.get()){
                    getData(type);
                }
            }
        });
    }

    private void getData(String type){
        progressDialog.show();
        reportsViewModel.getTeamProgress(type).observe(CPReportActivity.this, res->{
            if(progressDialog.isShowing())
                progressDialog.cancel();
            if(res != null){
                if(res.getDatalist() != null){
                    reportList.clear();
                    ArrayList<ReportList> list = new ArrayList<>(res.getDatalist());
                 //   Collections.sort(list, (o1, o2)-> o1.getTIMEELAPSED().compareTo(o2.getTIMEELAPSED()));
                    reportList.addAll(list);
                    teamProgressAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
