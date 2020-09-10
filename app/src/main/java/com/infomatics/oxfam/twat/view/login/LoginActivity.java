package com.infomatics.oxfam.twat.view.login;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.login.LoginRequest;
import com.infomatics.oxfam.twat.model.login.Role;
import com.infomatics.oxfam.twat.model.room.entity.CheckpointEntity;
import com.infomatics.oxfam.twat.model.room.entity.UserEntity;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.util.CustomTextWatcher;
import com.infomatics.oxfam.twat.view.BaseActivity;
import com.infomatics.oxfam.twat.view.CheckInCheckOutActivity;
import com.infomatics.oxfam.twat.view.DashboardActivity;
import com.infomatics.oxfam.twat.viewmodel.LoginViewModel;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {


    private TextInputLayout txtUserId;
    private TextInputLayout txtPassword;
    private EditText etuserName;
    private EditText etPassword;
    private TextView btnLogin;
    private TextView forgotPassword;
    private boolean hasEnteredUserId;
    private boolean hasEnteredPassword;

    private LoginViewModel loginViewModel;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);
        AppConstants.hasFetchedCPData = false;
        componentBinding();
    }

    private void componentBinding(){
        txtUserId = findViewById(R.id.textInputUserId);
        txtPassword = findViewById(R.id.textInputPassword);
        etuserName  = findViewById(R.id.txt_user_id);
        etPassword = findViewById(R.id.txt_password);
        btnLogin = findViewById(R.id.btn_sign_in);
        forgotPassword = findViewById(R.id.forgot_pasword);
        etuserName.addTextChangedListener(new CustomTextWatcher(etuserName, value -> {
            hasEnteredUserId = value;
            toggleLoginButton();
        }));

        etPassword.addTextChangedListener(new CustomTextWatcher(etPassword, value -> {
            hasEnteredPassword = value;
            toggleLoginButton();
        }));
       progressDialog = ApplicationUtils.getProgressDialog(new WeakReference<>(LoginActivity.this),"Please wait..." , false);
        btnLogin.setOnClickListener(v->{
            if(hasEnteredUserId && hasEnteredPassword){
                sharedPreferences = getSharedPreferences(AppConstants.APP_PREF, MODE_PRIVATE);
                if(ApplicationUtils.isNetworkAvailable(LoginActivity.this)) {
                    progressDialog.show();
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setPassword(etPassword.getText().toString());
                    loginRequest.setUserid(etuserName.getText().toString());

                    loginViewModel.login(loginRequest).observe(LoginActivity.this, response -> {
                        if (response != null && response.getStatus()) {

                            sharedPreferences.edit().putBoolean(AppConstants.IS_LOGGED_IN, true).apply();
                            sharedPreferences.edit().putString(AppConstants.USERNAME, etuserName.getText().toString()).apply();
                            try {
                                UserEntity userEntity = loginViewModel.getUserDetails(etuserName.getText().toString());
                                boolean update = false;
                                if(userEntity != null)
                                    update = true;
                                else
                                    userEntity = new UserEntity();
                                if(etuserName.getText().toString().toLowerCase().contains("cp")
                                && etuserName.getText().toString().length() <= 4){
                                    switch(etuserName.getText().toString().toLowerCase()){
                                        case "cp1":
                                            AppConstants.CHECKPOINT_ID = 1;
                                            break;
                                        case "cp2":
                                            AppConstants.CHECKPOINT_ID = 2;
                                            break;
                                        case "cp3":
                                            AppConstants.CHECKPOINT_ID = 3;
                                            break;
                                        case "cp4":
                                            AppConstants.CHECKPOINT_ID = 4;
                                            break;
                                        case "cp5":
                                            AppConstants.CHECKPOINT_ID = 5;
                                            break;
                                        case "cp6":
                                            AppConstants.CHECKPOINT_ID = 6;
                                            break;
                                        case "cp7":
                                            AppConstants.CHECKPOINT_ID = 7;
                                            break;
                                        case "cp8":
                                            AppConstants.CHECKPOINT_ID = 8;
                                            break;
                                        case "cp9":
                                            AppConstants.CHECKPOINT_ID = 9;
                                            break;
                                        case "cp10":
                                            AppConstants.CHECKPOINT_ID = 10;
                                            break;
                                        case "fp":
                                            AppConstants.CHECKPOINT_ID = 10;
                                            break;
                                    }
                                }else {
                                    AppConstants.CHECKPOINT_ID = 0;
                                }
                                AppConstants.USER_ID = response.getDatalist().getId();
                                AppConstants.USER_ROLE = response.getDatalist().getRole();
                                AppConstants.PERMISSIONS = response.getDatalist().getPermissions();
                                AppConstants.FIRST_NAME = response.getDatalist().getFirstName();
                                AppConstants.LAST_NAME = response.getDatalist().getLastName();

                                userEntity.setCpId(AppConstants.CHECKPOINT_ID);
                                userEntity.setUserContact(etuserName.getText().toString());
                                userEntity.setUserId(AppConstants.USER_ID);
                                userEntity.setPassword(etPassword.getText().toString());
                                userEntity.setRole(AppConstants.USER_ROLE.getRole());
                                userEntity.setRoleId(AppConstants.USER_ROLE.getId());
                                userEntity.setUserName(AppConstants.FIRST_NAME+" "+AppConstants.LAST_NAME);
                                userEntity.setPermissions(AppConstants.PERMISSIONS);

                                if(update){
                                    loginViewModel.updateUserDetails(userEntity);
                                }else{
                                    loginViewModel.insertUserDetails(userEntity);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(AppConstants.USER_ROLE.getId() != 1) {
                                loginViewModel.getCpDetails(AppConstants.CHECKPOINT_ID).observe(LoginActivity.this, res -> {
                                    if (res != null && res.getDatalist() != null) {
                                        CheckpointEntity checkpointEntity = loginViewModel.getCpData(AppConstants.CHECKPOINT_ID);
                                        boolean isNew = false;
                                        if (checkpointEntity == null) {
                                            isNew = true;
                                            checkpointEntity = new CheckpointEntity();
                                        }
                                        checkpointEntity.setCpId(res.getDatalist().getId());
                                        checkpointEntity.setCpName(res.getDatalist().getName());
                                        checkpointEntity.setCpStatus(res.getDatalist().getStatus());
                                        checkpointEntity.setLat(res.getDatalist().getLatitude());
                                        checkpointEntity.setLng(res.getDatalist().getLongitude());
                                        checkpointEntity.setRemarks(res.getDatalist().getRemark());
                                        if (isNew)
                                            loginViewModel.insertCPData(checkpointEntity);
                                        else
                                            loginViewModel.updateCPData(checkpointEntity);

                                        AppConstants.CP_NAME = res.getDatalist().getName();
                                        AppConstants.CP_STATUS = res.getDatalist().getStatus();
                                        switch (AppConstants.USER_ROLE.getId()) {
                                            case 1:
                                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                                break;
                                            default:
                                                startActivity(new Intent(LoginActivity.this, CheckInCheckOutActivity.class));
                                                break;
                                        }
                                        LoginActivity.this.finish();
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }
                                });
                            }else{
                                CheckpointEntity checkpointEntity = loginViewModel.getCpData(AppConstants.CHECKPOINT_ID);
                                boolean isNew = false;
                                if (checkpointEntity == null) {
                                    isNew = true;
                                    checkpointEntity = new CheckpointEntity();
                                }
                                checkpointEntity.setCpId(0);
                                checkpointEntity.setCpName("SP");
                                checkpointEntity.setCpStatus("Open");
                                if (isNew)
                                    loginViewModel.insertCPData(checkpointEntity);
                                else
                                    loginViewModel.updateCPData(checkpointEntity);

                                AppConstants.CP_NAME = "SP";
                                AppConstants.CP_STATUS = "Open";
                                switch (AppConstants.USER_ROLE.getId()) {
                                    case 1:
                                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                        break;
                                    default:
                                        startActivity(new Intent(LoginActivity.this, CheckInCheckOutActivity.class));
                                        break;
                                }
                                LoginActivity.this.finish();
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }

                        }else{
                            progressDialog.dismiss();
                            if(response != null) {
                                ApplicationUtils.showToast(LoginActivity.this, "Invalid Credentials, please try again.");
                            }else{
                                ApplicationUtils.showToast(LoginActivity.this, "Unable to connect to network, please try again.");
                            }
                        }
                    });
                }else{
                    UserEntity userEntity = loginViewModel.getUserDetails(etuserName.getText().toString());

                    if(userEntity != null){
                        if(etPassword.getText().toString().equals(userEntity.getPassword())) {
                            sharedPreferences.edit().putBoolean(AppConstants.IS_LOGGED_IN, true).apply();
                            AppConstants.CHECKPOINT_ID = userEntity.getCpId();
                            AppConstants.USER_ID = userEntity.getUserId();
                            Role role = new Role();
                            role.setId(userEntity.getRoleId());
                            role.setRole(userEntity.getRole());
                            AppConstants.USER_ROLE = role;
                            AppConstants.PERMISSIONS = userEntity.getPermissions();
                            AppConstants.FIRST_NAME = userEntity.getUserName().split(" ")[0];
                            try {
                                AppConstants.LAST_NAME = userEntity.getUserName().split(" ")[1];
                            } catch (Exception e) {
                                e.printStackTrace();
                                AppConstants.LAST_NAME = "";
                            }

                            CheckpointEntity checkpointEntity = loginViewModel.getCpData(AppConstants.CHECKPOINT_ID);
                            if(checkpointEntity != null){
                                AppConstants.CP_NAME = checkpointEntity.getCpName();
                                AppConstants.CP_STATUS = checkpointEntity.getCpStatus();
                            }

                            switch (AppConstants.USER_ROLE.getId()) {
                                case 1:
                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                    break;
                                default:
                                    startActivity(new Intent(LoginActivity.this, CheckInCheckOutActivity.class));
                                    break;
                            }
                            ApplicationUtils.showToast(LoginActivity.this, "LoggedIn in Offline Mode.");
                            LoginActivity.this.finish();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        else{
                            ApplicationUtils.showToast(LoginActivity.this, "Invalid Credentials, please try again.");
                        }
                    }else{
                        ApplicationUtils.showToast(LoginActivity.this, "First time offline login is not allowed. Please connect to internet to login.");
                    }
                }
            }
        });
        forgotPassword.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
        });
    }
    private void toggleLoginButton(){

    }
}
