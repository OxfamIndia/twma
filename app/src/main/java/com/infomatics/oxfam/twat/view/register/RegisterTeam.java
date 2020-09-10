package com.infomatics.oxfam.twat.view.register;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.adapter.RegistrationAdapter;
import com.infomatics.oxfam.twat.interfaces.ItemPositionClickListener;
import com.infomatics.oxfam.twat.model.checkincheckout.CheckInCheckOutBean;
import com.infomatics.oxfam.twat.model.register.RegisterModel;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;
import com.infomatics.oxfam.twat.model.room.entity.MemberEntity;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.room.entity.WalkerEntity;
import com.infomatics.oxfam.twat.model.team.Walker;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.util.nfc.NdefMessageParser;
import com.infomatics.oxfam.twat.util.nfc.ParsedNdefRecord;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.view.tagscan.TagScanActivity;
import com.infomatics.oxfam.twat.viewmodel.RegistrationViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class RegisterTeam extends BaseActivity implements ItemPositionClickListener {

    private AutoCompleteTextView etTeamName;
    private ImageView imgSearch;
    private RecyclerView memberList;
    private TextView walkerInfo;
    private ArrayList<RegisterModel> registerModels;
    private List<TeamEntity> searchData = new ArrayList<>();
    private List<WalkerEntity> walkerData = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private RegistrationAdapter registrationAdapter;
    private RegistrationViewModel registrationViewModel;
    private ProgressDialog progressDialog;
    private ImageView imgBack;
    private TextView tvHeaderText;
    private int selectedPosition;
    private RadioButton rbTeam,rbWalker, rbPhone;
    private TextView registerTeam;
    private EditText bibNo;
    private LinearLayout mappingLayout;
    private boolean completed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);
        registrationViewModel = ViewModelProviders.of(RegisterTeam.this).get(RegistrationViewModel.class);
        progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(RegisterTeam.this), "Please wait...", false);
        progressDialog.show();
        walkerData = new ArrayList<>();
        walkerData.addAll(registrationViewModel.getAllWalkers());
        registrationViewModel.getAllTeamNames().observe(RegisterTeam.this, list->{
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(list != null && list.size()>0){
                searchData.clear();
                searchData.addAll(list);
                Log.e(AppConstants.TAG, "All Team Data fetched");
            }
        });
        componentBinding();
    }

    private void componentBinding(){
        etTeamName = findViewById(R.id.team_name);
        imgSearch = findViewById(R.id.img_search);
        memberList = findViewById(R.id.team_walker_recycler);
        imgBack = findViewById(R.id.btn_back);
        tvHeaderText = findViewById(R.id.tv_header_text);
        registerTeam = findViewById(R.id.btn_register);
        bibNo = findViewById(R.id.txt_bibno);
        rbTeam = findViewById(R.id.rbTeam);
        walkerInfo = findViewById(R.id.walkerInfo);
        rbWalker = findViewById(R.id.rbWalker);
        rbPhone = findViewById(R.id.rbPhone);
        mappingLayout = findViewById(R.id.mappingLayout);
        mappingLayout.setVisibility(View.GONE);
        registerModels = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(RegisterTeam.this, LinearLayoutManager.VERTICAL, false);
        registrationAdapter = new RegistrationAdapter(registerModels, this);
        memberList.setLayoutManager(linearLayoutManager);
        memberList.setAdapter(registrationAdapter);
        ArrayAdapter<TeamEntity> adapter = new ArrayAdapter<TeamEntity>
                (this, android.R.layout.select_dialog_item, searchData);
        etTeamName.setThreshold(1);
        etTeamName.setAdapter(adapter);
        etTeamName.setOnItemClickListener((parent, view, position, id) -> {
            TeamEntity selectedTeam = (TeamEntity) parent.getAdapter().getItem(position);
            if(selectedTeam != null) {
                registerModels.clear();
                Log.e(AppConstants.TAG, "" + selectedTeam.getTeamTitle() + " : " + selectedTeam.getId());
                /*List<WalkerEntity> walkerEntities = registrationViewModel.getWalkerByTeamName(selectedTeam.getTeamTitle());
                for(WalkerEntity walker : walkerEntities){
                    RegisterModel registerModel = new RegisterModel();
                    registerModel.setSelected(false);
                    registerModel.setBibNo(walker.getBibNo());
                    registerModel.setTeamName(selectedTeam.getTeamTitle());
                    registerModel.setWalkerName(walker.getWalkerName());
                    registerModel.setPhone(walker.getMobile());
                    registerModels.add(registerModel);
                }
                mappingLayout.setVisibility(View.VISIBLE);
                registrationAdapter.notifyDataSetChanged();*/
                getTeamDetails(selectedTeam.getTeamId());
            }
        });
        etTeamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etTeamName.getText().toString().length() > 0){
                    imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                }else{
                    imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
                }
            }
        });
        imgSearch.setOnClickListener(v->{
            if(etTeamName.getText().toString().length() > 0){
                etTeamName.setText("");
                registerModels.clear();
                bibNo.setText("");
                mappingLayout.setVisibility(View.GONE);
            }
        });
        tvHeaderText.setText("Search");
        imgBack.setOnClickListener(v->{
            RegisterTeam.this.finish();
        });
        imgBack.setVisibility(View.VISIBLE);

        rbTeam.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                etTeamName.setText("");
                registerTeam.setVisibility(View.GONE);
                ArrayAdapter<TeamEntity> adapter2 = new ArrayAdapter<TeamEntity>
                        (this, android.R.layout.select_dialog_item, searchData);
                etTeamName.setThreshold(1);
                etTeamName.setHint("Team Name");
                etTeamName.setAdapter(adapter2);
                memberList.setVisibility(View.VISIBLE);
                walkerInfo.setVisibility(View.GONE);
                etTeamName.setOnItemClickListener((parent, view, position, id) -> {
                    TeamEntity selectedTeam = (TeamEntity) parent.getAdapter().getItem(position);
                    if(selectedTeam != null) {
                        registerModels.clear();
                       getTeamDetails(selectedTeam.getTeamId());
                    }
                });
            }
        });
        rbWalker.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                etTeamName.setText("");
                registerTeam.setVisibility(View.VISIBLE);
                etTeamName.setHint("Walker Name");
                ArrayAdapter<WalkerEntity> adapter2 = new ArrayAdapter<>
                        (this, android.R.layout.select_dialog_item, walkerData);
                etTeamName.setThreshold(1);
                etTeamName.setAdapter(adapter2);
                memberList.setVisibility(View.GONE);
                walkerInfo.setVisibility(View.VISIBLE);
                etTeamName.setOnItemClickListener((parent, view, position, id) -> {
                    WalkerEntity selectedTeam = (WalkerEntity) parent.getAdapter().getItem(position);
                    if(selectedTeam != null) {
                        registrationViewModel.getWalkerById(Integer.toString(selectedTeam.getWalkerId())).observe(RegisterTeam.this, res->{
                            if(res != null){
                                registerModels.clear();
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append("Walker Name: "+selectedTeam.getWalkerName());
                                stringBuffer.append("\nTeam Name: "+selectedTeam.getTeamName());
                                stringBuffer.append("\nMobile: "+selectedTeam.getMobile());
                                stringBuffer.append("\nBib No: "+res.getWalker().getBibNo());
                                stringBuffer.append("\nLast CP: "+res.getWalker().getLastCP());
                                stringBuffer.append("\nLast CP Time: "+res.getWalker().getLastCPTime());
                                if(selectedTeam.getRunning() != null){
                                    if(selectedTeam.getRunning().equalsIgnoreCase("0"))
                                        stringBuffer.append("\nWalking: No");
                                    else
                                        stringBuffer.append("\nWalking: Yes");
                                }
                                walkerInfo.setText(stringBuffer.toString());
                                mappingLayout.setVisibility(View.VISIBLE);
                                registrationAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                });
            }
        });

        rbPhone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                etTeamName.setText("");
                registerTeam.setVisibility(View.VISIBLE);
                etTeamName.setHint("Phone Number");
                ArrayList<String> phoneData = new ArrayList<>();
                for(WalkerEntity walkerEntity : walkerData){
                    if(walkerEntity.getMobile() != null && walkerEntity.getMobile().length() > 0)
                        phoneData.add(walkerEntity.getMobile());
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>
                        (this, android.R.layout.select_dialog_item, phoneData);
                etTeamName.setThreshold(1);
                etTeamName.setAdapter(adapter2);
                memberList.setVisibility(View.GONE);
                walkerInfo.setVisibility(View.VISIBLE);
                etTeamName.setOnItemClickListener((parent, view, position, id) -> {
                    String phone = (String) parent.getAdapter().getItem(position);
                    WalkerEntity selectedTeam = registrationViewModel.getWalkerByphone(phone);
                    if(selectedTeam != null) {
                        registerModels.clear();
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Walker Name:   "+selectedTeam.getWalkerName());
                        stringBuffer.append("\nTeam Name:   "+selectedTeam.getTeamName());
                        stringBuffer.append("\nMobile:   "+selectedTeam.getMobile());
                        stringBuffer.append("\nBib No:   "+selectedTeam.getBibNo());
                        stringBuffer.append("\nWalker Id:   "+selectedTeam.getWalkerId());
                        if(selectedTeam.getRunning() != null){
                            if(selectedTeam.getRunning().equalsIgnoreCase("0"))
                                stringBuffer.append("\nWalking:   No");
                            else
                                stringBuffer.append("\nWalking:   Yes");
                        }
                        walkerInfo.setText(stringBuffer.toString());
                        mappingLayout.setVisibility(View.VISIBLE);
                        registrationAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
       /* registerTeam.setOnClickListener(v->{
            if(bibNo.getText().toString().trim().length()==0){
                ApplicationUtils.showToast(RegisterTeam.this, "Please enter BIB No.");
                return;
            }
            if(completed){
                String bib = bibNo.getText().toString().trim();
               registerModels.get(0).setBibNo(bib+"A");
               registerModels.get(1).setBibNo(bib+"B");
               registerModels.get(2).setBibNo(bib+"C");
               registerModels.get(3).setBibNo(bib+"D");
               registrationViewModel.mapRfids(registerModels).observe(RegisterTeam.this, response->{
                   Log.e(AppConstants.TAG,"GOT REGISTER RESPONSE");
                   if(response != null){
                       if(response.getMessage() != null) {
                           ApplicationUtils.showToast(RegisterTeam.this, response.getMessage());
                           etTeamName.setText("");
                       }
                   }
               });
            }else{
                ApplicationUtils.showToast(RegisterTeam.this, "Please assign RFIDs to all walkers");
            }
        });*/
    }

    private void getTeamDetails(int teamId){
        registerModels.clear();
        registrationViewModel.getTeamDetailById(Integer.toString(teamId)).observe(RegisterTeam.this,res->{
            if(res != null){
                List<Walker> walkerEntities = res.getDatalist().getWalker();
                if(walkerEntities != null && walkerEntities.size() > 0) {
                    for (Walker walker : walkerEntities) {
                        RegisterModel registerModel = new RegisterModel();
                        registerModel.setSelected(false);
                        registerModel.setBibNo(walker.getBibNo());
                        registerModel.setTeamName(res.getDatalist().getTeamTitle());
                        registerModel.setWalkerName(walker.getFirstName() + " " + walker.getLastName());
                        registerModel.setPhone(walker.getMobile());
                        registerModel.setLastCP(res.getDatalist().getLastCP());
                        registerModel.setLastCPTime(res.getDatalist().getLastCPTime());
                        registerModels.add(registerModel);
                    }
                    mappingLayout.setVisibility(View.VISIBLE);
                    registrationAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isConnectedToInternet.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                runOnUiThread(() -> {
                    if(isConnectedToInternet.get()){
                        findViewById(R.id.online_offline).setVisibility(View.GONE);
                    }else{
                        findViewById(R.id.online_offline).setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        runOnUiThread(() -> {
            if(isConnectedToInternet.get()){
                findViewById(R.id.online_offline).setVisibility(View.GONE);
            }else{
                findViewById(R.id.online_offline).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
      /*  for(int i=0;i< registerModels.size();i++){
            if(i==position){
                selectedPosition = position;
                registerModels.get(i).setSelected(true);
            }else{
                registerModels.get(i).setSelected(false);
            }
        }
        registrationAdapter.notifyDataSetChanged();*/
    }


}
