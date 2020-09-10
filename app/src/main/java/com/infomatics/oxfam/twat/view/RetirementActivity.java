package com.infomatics.oxfam.twat.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.RetirementAdapter;
import com.infomatics.oxfam.twat.model.retirement.Datalist;
import com.infomatics.oxfam.twat.repository.ApiRepository;
import com.infomatics.oxfam.twat.util.ApplicationUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RetirementActivity extends AppCompatActivity {

    private RecyclerView retirementList;
    private LinearLayoutManager mLinearLayoutManager;
    private RetirementAdapter retirementAdapter;
    private ArrayList<Datalist> retirements;
    private ImageView btnBack;
    private TextView tvHeaderText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement);
        componentBinding();
    }

    private void componentBinding(){
        retirementList = findViewById(R.id.retirement_list);
        retirements = new ArrayList<>();
        btnBack = findViewById(R.id.btn_back);
        tvHeaderText = findViewById(R.id.tv_header_text);

        tvHeaderText.setText("Retirements");
        btnBack.setVisibility(View.VISIBLE);
        tvHeaderText.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(v->{
            RetirementActivity.this.finish();
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        retirementList.setLayoutManager(mLinearLayoutManager);
        retirementAdapter = new RetirementAdapter(retirements);
        retirementList.setAdapter(retirementAdapter);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(RetirementActivity.this), "Please wait...", false);
        getRetirements();
    }

    private void getRetirements(){
        progressDialog.show();
        ApiRepository apiRepository = ApiRepository.getInstance();
        apiRepository.getAllRetirements().observe(RetirementActivity.this, res->{
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(res != null && res.getDataList() != null && res.getDataList().size() > 0){
                retirements.clear();
                retirements.addAll(new ArrayList<>(res.getDataList()));
                Collections.sort(retirements, (o1, o2) -> o1.getCpName().compareTo(o2.getCpName()));
                retirementAdapter.notifyDataSetChanged();
            }else{
                ApplicationUtils.showToast(RetirementActivity.this,"No Retirements Yet.");
            }
        });
    }
}
