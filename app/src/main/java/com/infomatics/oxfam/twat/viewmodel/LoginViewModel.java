package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.infomatics.oxfam.twat.model.checkincheckout.CPStatusResponse;
import com.infomatics.oxfam.twat.model.login.ForgotPasswordRequest;
import com.infomatics.oxfam.twat.model.login.ForgotPasswordResponse;
import com.infomatics.oxfam.twat.model.login.LoginRequest;
import com.infomatics.oxfam.twat.model.login.LoginResponse;
import com.infomatics.oxfam.twat.model.login.Permission;
import com.infomatics.oxfam.twat.model.room.dao.UserDao;
import com.infomatics.oxfam.twat.model.room.entity.CheckpointEntity;
import com.infomatics.oxfam.twat.model.room.entity.UserEntity;
import com.infomatics.oxfam.twat.repository.ApiRepository;
import com.infomatics.oxfam.twat.repository.AppDatabase;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<LoginResponse> loginResponse;
    private MutableLiveData<ForgotPasswordResponse> forgotResponse;
    private MutableLiveData<CPStatusResponse> cpStatusResponse;
    private AppDatabase appDatabase;
    private UserDao userDao;
    private ApiRepository apiRepository;

    public LoginViewModel(Application application){
        super(application);
        if(loginResponse != null && cpStatusResponse != null){
            return;
        }
        apiRepository = ApiRepository.getInstance();
        appDatabase = AppDatabase.getAppDatabase(application);
        userDao = appDatabase.userDao();
    }

    public CheckpointEntity getCpData(int cpId){
        return appDatabase.checkPointDao().getCpData(cpId);
    }

    public void insertCPData(CheckpointEntity checkpointEntity){
        appDatabase.checkPointDao().insert(checkpointEntity);
    }

    public void updateCPData(CheckpointEntity checkpointEntity){
        appDatabase.checkPointDao().update(checkpointEntity);
    }
    public LiveData<LoginResponse> login(LoginRequest loginRequest){
        loginResponse = apiRepository.login(loginRequest);
        return loginResponse;
    }

    public LiveData<CPStatusResponse> getCpDetails(int cpId){
        cpStatusResponse = apiRepository.getCPStatus(cpId);
        return cpStatusResponse;
    }

    public LiveData<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest){
        forgotResponse = apiRepository.forgotPassword(forgotPasswordRequest);
        return forgotResponse;
    }

    public UserEntity getUserDetails(String userId){
        return userDao.getUserDetails(userId);
    }

    public void updateUserDetails(UserEntity userEntity){
        appDatabase.userDao().update(userEntity);
    }

    public void insertUserDetails(UserEntity userEntity){
        appDatabase.userDao().insert(userEntity);
    }


}
