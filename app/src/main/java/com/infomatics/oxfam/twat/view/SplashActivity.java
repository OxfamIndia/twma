package com.infomatics.oxfam.twat.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.login.Role;
import com.infomatics.oxfam.twat.model.room.entity.CheckpointEntity;
import com.infomatics.oxfam.twat.model.room.entity.UserEntity;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.util.ApplicationUtils;
import com.infomatics.oxfam.twat.view.login.LoginActivity;
import com.infomatics.oxfam.twat.viewmodel.LoginViewModel;

import java.util.Base64;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loginViewModel = ViewModelProviders.of(SplashActivity.this).get(LoginViewModel.class);

        new Handler().postDelayed(() -> {
            sharedPreferences = getSharedPreferences(AppConstants.APP_PREF, MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean(AppConstants.IS_LOGGED_IN, false);
            String userId = sharedPreferences.getString(AppConstants.USERNAME,"");
            if(isLoggedIn){
                UserEntity userEntity = loginViewModel.getUserDetails(userId);
                if(userEntity != null){
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
                                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                                break;
                            default:
                                startActivity(new Intent(SplashActivity.this, CheckInCheckOutActivity.class));
                                break;
                        }
                }else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }else{
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            SplashActivity.this.finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 1500);

    }
}
