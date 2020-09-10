package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.infomatics.oxfam.twat.model.room.entity.ContactEntity;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(ContactEntity... contacts);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ContactEntity contactEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(ContactEntity contactEntity);

    @Query("SELECT * FROM contact_table")
    LiveData<List<ContactEntity>> getAllContacts();
}
