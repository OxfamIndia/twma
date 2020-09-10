package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.infomatics.oxfam.twat.model.room.entity.WalkerEntity;
import com.infomatics.oxfam.twat.model.team.Walker;

import java.util.List;

@Dao
public interface WalkerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WalkerEntity walkerEntity);

    @Query("DELETE from walker_table")
    void removeAll();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updated(WalkerEntity walkerEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(WalkerEntity...walkerEntities);

    @Query("SELECT* FROM walker_table")
    List<WalkerEntity> getAllWalkers();

    @Query("SELECT COUNT(_id) FROM walker_table where type = 100")
    int getHundredWalkerCount();

    @Query("SELECT COUNT(_id) FROM walker_table")
    int getAllWalkersCount();

    @Query("Select * from walker_table where team_name = :teamName")
    List<WalkerEntity> getWalkerByTeamName(String teamName);

    @Query("Select * FROM walker_table where mobile = :phone")
    WalkerEntity getWalkerByPhone(String phone);

    @Query("SELECT bibNo From walker_table where walker_name LIKE :name")
    String getWalkerByName(String name);

}
