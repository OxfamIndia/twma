package com.infomatics.oxfam.twat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.infomatics.oxfam.twat.model.room.entity.NotificationEntity;
import com.infomatics.oxfam.twat.repository.AppDatabase;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    public NotificationViewModel(Application application){
        super(application);
        appDatabase = AppDatabase.getAppDatabase(application);
    }

    public LiveData<List<NotificationEntity>> getAllNotifications(){
        return appDatabase.notificationDao().getAllNotifications();
    }
}
