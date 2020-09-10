package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.infomatics.oxfam.twat.model.room.entity.RetirementEntity;

import java.util.List;

@Dao
public interface RetirementDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RetirementEntity retirementEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(RetirementEntity... entries);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(RetirementEntity retirementEntity);

    @Query("SELECT * FROM retirement_table")
    List<RetirementEntity> getAllRetirements();


    @Query("DELETE FROM retirement_table")
    void removeAll();

    @Query("Select * FROM retirement_table where bibNo = :bibNo")
    RetirementEntity getRetirementWithBibNo(String bibNo);
}
