package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.infomatics.oxfam.twat.model.room.entity.UserEntity;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserEntity userEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(UserEntity userEntity);

    @Query("Select * FROM user_table WHERE userContact LIKE :userId")
    UserEntity getUserDetails(String userId);

}
