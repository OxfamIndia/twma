package com.infomatics.oxfam.twat.view.dutyregister;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.ViewPagerAdapter;
import com.infomatics.oxfam.twat.model.dutyregister.ChangeDutyRequest;
import com.infomatics.oxfam.twat.model.dutyregister.Datalist;
import com.infomatics.oxfam.twat.model.dutyregister.DutyList;
import com.infomatics.oxfam.twat.model.dutyregister.DutyRegisterResponse;
import com.infomatics.oxfam.twat.model.dutyregister.User;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.view.dutyregister.fragments.AllCPDutyFragment;
import com.infomatics.oxfam.twat.view.dutyregister.fragments.DutyFragment;
import com.infomatics.oxfam.twat.viewmodel.DutyRegisterViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class DutyRegisterAcitivity extends BaseActivity {

    private ImageView btnBack;
    private TextView tvHeader;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AllCPDutyFragment allCP;
    private DutyFragment duty;
    private DutyRegisterViewModel dutyRegisterViewModel;
    List<String> expandableListTitle;
    private List<DutyList> currentCp;
    TreeMap<String, List<User>> expandableListDetail;
    List<User> allDuties = new ArrayList<>();
    private ProgressDialog progressDialog;
    public ObservableBoolean hasFetched = new ObservableBoolean();

    public List<String> getExpandableListTitle() {
        return expandableListTitle;
    }

    public void setExpandableListTitle(List<String> expandableListTitle) {
        this.expandableListTitle = expandableListTitle;
    }

    public TreeMap<String, List<User>> getExpandableListDetail() {
        return expandableListDetail;
    }

    public void setExpandableListDetail(TreeMap<String, List<User>> expandableListDetail) {
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_register_acitivity);
        dutyRegisterViewModel = ViewModelProviders.of(DutyRegisterAcitivity.this).get(DutyRegisterViewModel.class);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(DutyRegisterAcitivity.this),"Please wait...", false);
        componentBinding();
    }

    private void componentBinding(){
        btnBack = findViewById(R.id.btn_back);
        tvHeader = findViewById(R.id.tv_header_text);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(v->{
            DutyRegisterAcitivity.this.finish();
        });

        tvHeader.setText("Duty Register");
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        progressDialog.show();
        dutyRegisterViewModel.getDutyRegister().observe(DutyRegisterAcitivity.this, response->{
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(response != null){
                makedataAndNotify(response);
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                allCP = new AllCPDutyFragment();
                duty = new DutyFragment();
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        getAllUsersForDuty();
    }

    public void getDuties(){
        if(!progressDialog.isShowing())
            progressDialog.show();
        dutyRegisterViewModel.getDutyRegister().observe(DutyRegisterAcitivity.this, response->{
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(response != null){
                makedataAndNotify(response);
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                allCP = new AllCPDutyFragment();
                duty = new DutyFragment();
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    public void getAllUsersForDuty(){
        if(!progressDialog.isShowing())
            progressDialog.show();
        hasFetched.set(false);
        dutyRegisterViewModel.getAllUsersForDuty().observe(DutyRegisterAcitivity.this, res->{
            if(progressDialog.isShowing())
                progressDialog.cancel();
            if(res != null){
                currentCp = new ArrayList<>(res.getDatalist());
                hasFetched.set(true);
            }
        });
    }
    private void makedataAndNotify(DutyRegisterResponse response){
        expandableListDetail = getDutyData(response.getDatalist());
        if(duty != null && hasFetched.get())
            duty.updateUsers();
    }


    public TreeMap<String, List<User>> getDutyData(List<Datalist> allRoles) {
        TreeMap<String, List<User>> expandableListDetail = new TreeMap<>();
        for(Datalist roleEntity : allRoles){
            if(roleEntity.getUser() != null)
            expandableListDetail.put(roleEntity.getName()+"("+roleEntity.getUser().size()+")", roleEntity.getUser());
            if(roleEntity.getName().equalsIgnoreCase(AppConstants.CP_NAME)){
                allDuties.clear();
                allDuties.addAll(roleEntity.getUser());
                if(duty != null)
                    duty.updateUsers();
            }
        }
        return expandableListDetail;
    }

    public List<User> getAllDuties() {
        return allDuties;
    }

    public List<DutyList> getCurrentCp() {
        return currentCp;
    }

    public void changeuserDuty(String userid){
        if(!progressDialog.isShowing())
            progressDialog.show();
        ChangeDutyRequest changeDutyRequest = new ChangeDutyRequest();
        changeDutyRequest.setCpid(Integer.toString(AppConstants.CHECKPOINT_ID));
        changeDutyRequest.setUserid(userid);
        dutyRegisterViewModel.changeUserDuty(changeDutyRequest).observe(DutyRegisterAcitivity.this, res->{
            if(progressDialog.isShowing())
                progressDialog.cancel();
            if(res != null){
            if(res.status){
                ApplicationUtils.showToast(DutyRegisterAcitivity.this,"Duty Changed");
                hasFetched.set(false);
                getAllUsersForDuty();
                getDuties();
            }
            }
        });
    }
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(AppConstants.USER_ROLE.getId() != 1) {
            adapter.addFragment(duty, "Cp Duty");
        }
        adapter.addFragment(allCP, "All Duties");
        viewPager.setAdapter(adapter);
    }


}
