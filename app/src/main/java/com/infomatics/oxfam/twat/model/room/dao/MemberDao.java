package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.infomatics.oxfam.twat.model.room.entity.MemberEntity;

import java.util.List;

@Dao
public interface MemberDao {

    @Query("SELECT * from rfid_mapping where rfid LIKE :tagId")
    MemberEntity getMemberByTagId(String tagId);

    @Query("SELECT rfid from rfid_mapping where bibno LIKE :bibNo")
    String getRfidByBibNo(String bibNo);

    @Query("SELECT COUNT(id) from rfid_mapping where bibno LIKE :bibNo")
    int getCountOfBib(String bibNo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMembers(MemberEntity... memberEntities);

    @Query("SELECT * from rfid_mapping where bibno LIKE :bibNo")
    List<MemberEntity> membersByBibNo(String bibNo);

    @Query("DELETE FROM rfid_mapping")
    void removeAllEntries();


}
