package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.infomatics.oxfam.twat.model.room.entity.NotificationEntity;

import java.util.List;

@Dao
public interface NotificationDao {

    @Query("Select * from notification_table order by id DESC")
    LiveData<List<NotificationEntity>> getAllNotifications();

    @Insert
    void insert(NotificationEntity notificationEntity);
}
