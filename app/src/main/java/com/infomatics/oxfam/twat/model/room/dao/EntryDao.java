package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.infomatics.oxfam.twat.model.room.entity.EntryLog;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM entry_log where tag_id LIKE :tagId AND checkpoint_id= :cp")
    EntryLog findEntryByTagId(String tagId, int cp);

    @Query("SELECT * FROM entry_log where bibNo LIKE :bibNo AND checkpoint_id= :cp")
    EntryLog findEntrybyBibNo(String bibNo, int cp);

    @Query("SELECT * FROM entry_log where bibNo = :bibNo AND checkpoint_id = :cp")
    EntryLog getEntryWithbibAndId(String bibNo, int cp);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntryLog entryLog);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(EntryLog...entryLogs);

    @Query("DELETE FROM entry_log")
    void removeAll();

    @Query("DELETE FROM entry_log where checkpoint_id = :cpId")
    void removeAll(int cpId);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(EntryLog entryLog);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateEntries(EntryLog...entryLog);

    @Query("SELECT * FROM entry_log WHERE  checkpoint_id LIKE :checkpointId ORDER BY activity_time DESC")
    LiveData<List<EntryLog>> getAllEntries(int checkpointId);


    @Query("SELECT * FROM entry_log where hasRetired = 1")
    List<EntryLog> getAllRetirements();

    @Query("SELECT * FROM entry_log where is_synced = 0 AND checkpoint_id = :checkpointId")
    List<EntryLog> getAllUnSyncedEntries(int checkpointId);

    @Query("SELECT COUNT(_id) FROM entry_log WHERE hasRetired = 1 AND checkpoint_id = :cpId")
    int getRetirementCount(int cpId);

    @Query("SELECT COUNT(_id) FROM entry_log Where checkpoint_id = :cpId")
    int getCheckInCounts(int cpId);

    @Query("SELECT COUNT(_id) FROM entry_log WHERE hasCheckedOut = 1 AND hasRetired = 0 AND checkpoint_id = :cpId")
    int getCheckOutCounts(int cpId);

    @Query("SELECT COUNT(_id) FROM entry_log WHERE bibNo LIKE :bibNo AND hasRetired = 0 AND checkpoint_id = :checkPointId")
    int getTeamCheckinCount(String bibNo, int checkPointId);

    @Query("SELECT COUNT(_id) FROM entry_log WHERE hasCheckedOut = 1 AND bibNo LIKE :bibNo AND checkpoint_id LIKE :checkPointId")
    int getTeamCheckoutCount(String bibNo, int checkPointId);

    @Query("SELECT COUNT(_id) FROM entry_log WHERE hasRetired = 1 AND bibNo LIKE :bibNo AND checkpoint_id LIKE :checkPointId")
    int getTeamRetirementCount(String bibNo , int checkPointId);


}
