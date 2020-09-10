package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.infomatics.oxfam.twat.model.BaseResponse;
import com.infomatics.oxfam.twat.model.dutyregister.ChangeDutyRequest;
import com.infomatics.oxfam.twat.model.dutyregister.DutyRegisterResponse;
import com.infomatics.oxfam.twat.model.dutyregister.DutyResponse;
import com.infomatics.oxfam.twat.repository.ApiRepository;

public class DutyRegisterViewModel extends AndroidViewModel {
    private MutableLiveData<DutyRegisterResponse> dutyRegisterResponse;
    private MutableLiveData<DutyResponse> allDuties;
    private MutableLiveData<BaseResponse> changeDuty;
    private ApiRepository apiRepository;
    public DutyRegisterViewModel(Application application){
        super(application);
        if(dutyRegisterResponse != null && allDuties != null && changeDuty != null)
            return;
        apiRepository = ApiRepository.getInstance();
    }

    public LiveData<DutyRegisterResponse> getDutyRegister(){
        dutyRegisterResponse = apiRepository.getDutyRegister();
        return dutyRegisterResponse;
    }

    public LiveData<DutyResponse> getAllUsersForDuty(){
        allDuties = apiRepository.getAllUsersForDuty();
        return allDuties;
    }

    public LiveData<BaseResponse> changeUserDuty(ChangeDutyRequest changeDutyRequest){
        changeDuty = apiRepository.changeUserDuty(changeDutyRequest);
        return changeDuty;
    }
}
