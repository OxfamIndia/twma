package com.infomatics.oxfam.twat.view.reportincident;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.reports.Datalist;
import com.infomatics.oxfam.twat.model.reports.ReportStatusRequest;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.viewmodel.ReportsViewModel;

import java.lang.ref.WeakReference;

public class LostNFoundDetail extends AppCompatActivity {

    private TextView tvHeader;
    private ImageView btnBack;
    private TextView tvDesc, tvCPName, tvStatus, updateStatus;
    private ImageView imageView;
    private Datalist datalist;
    private EditText remarks;
    private ProgressDialog progressDialog;

    private ReportsViewModel reportsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_nfound_detail);
        datalist = (Datalist) getIntent().getExtras().get("Data");
        reportsViewModel = ViewModelProviders.of(LostNFoundDetail.this).get(ReportsViewModel.class);
        componentBinding();
    }

    private void componentBinding() {
        tvHeader = findViewById(R.id.tv_header_text);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        tvDesc = findViewById(R.id.tvDesc);
        tvCPName = findViewById(R.id.cpName);
        tvStatus = findViewById(R.id.status);
        remarks = findViewById(R.id.etRemarks);
        updateStatus = findViewById(R.id.btn_update_status);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(LostNFoundDetail.this),"Please wait...", false);
        imageView = findViewById(R.id.img_view);
        tvHeader.setText("Details");
        if (datalist != null) {
            tvDesc.setText(datalist.getDescription());
            tvCPName.setText("Reported At Checkpoint: " + datalist.getCheckpointId());
            if (datalist.getStatus() == 0)
                tvStatus.setText("Current Status: Open");
            else
                tvStatus.setText("Current Status: Closed");


            if (datalist.getImage() != null) {
                Glide.with(LostNFoundDetail.this)
                        .load(datalist.getImage())
                        .into(imageView);
            }
        }
        btnBack.setOnClickListener(v -> {
            LostNFoundDetail.this.finish();
        });

        if(datalist.getStatus() == 1){
            updateStatus.setVisibility(View.GONE);
            remarks.setVisibility(View.GONE);
        }
        updateStatus.setOnClickListener(v->{
            if(remarks.getText().toString().trim().length() == 0){
                ApplicationUtils.showToast(LostNFoundDetail.this,"Please add some remarks.");
                return;
            }
            if(!progressDialog.isShowing())
                progressDialog.show();
            ReportStatusRequest reportStatusRequest = new ReportStatusRequest();
            reportStatusRequest.setId(Integer.toString(datalist.getId()));
            reportStatusRequest.setRemark(remarks.getText().toString().trim());
            reportStatusRequest.setStatus("1");
                reportsViewModel.changeStatus(reportStatusRequest).observe(LostNFoundDetail.this, res->{
                    try {
                        progressDialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(res != null){
                        ApplicationUtils.showToast(LostNFoundDetail.this, "Status Updated Successfully!");
                        LostNFoundDetail.this.finish();
                    }else{
                        ApplicationUtils.showToast(LostNFoundDetail.this, "Something went wrong, please try again");
                    }
                });
        });
    }
}
