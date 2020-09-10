package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.infomatics.oxfam.twat.model.room.entity.DashboardEntity;

@Dao
public interface DashboardDao {

    @Query("Select * from dashboard_table where checkpoint_id LIKE :checkpointId")
    DashboardEntity getDashboarddata(int checkpointId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DashboardEntity dashboardEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(DashboardEntity dashboardEntity);
}
