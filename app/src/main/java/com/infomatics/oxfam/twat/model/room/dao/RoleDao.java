package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.infomatics.oxfam.twat.model.room.entity.RoleEntity;

import java.util.List;

@Dao
public interface RoleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RoleEntity roleEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(RoleEntity... roles);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(RoleEntity roleEntity);

    @Query("SELECT * FROM role_table")
   List<RoleEntity> getAllRoles();
}
