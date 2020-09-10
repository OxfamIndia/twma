package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.infomatics.oxfam.twat.model.room.entity.CheckpointEntity;

@Dao
public interface CheckPointDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CheckpointEntity checkpointEntity);

    @Query("SELECT * from checkpoint_data where cp_id LIKE :cpId")
    CheckpointEntity getCpData(int cpId);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(CheckpointEntity checkpointEntity);

}
