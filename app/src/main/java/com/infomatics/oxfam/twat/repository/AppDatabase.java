package com.infomatics.oxfam.twat.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.infomatics.oxfam.twat.model.room.dao.CheckPointDao;
import com.infomatics.oxfam.twat.model.room.dao.ContactDao;
import com.infomatics.oxfam.twat.model.room.dao.DashboardDao;
import com.infomatics.oxfam.twat.model.room.dao.EntryDao;
import com.infomatics.oxfam.twat.model.room.dao.MemberDao;
import com.infomatics.oxfam.twat.model.room.dao.NotificationDao;
import com.infomatics.oxfam.twat.model.room.dao.RetirementDao;
import com.infomatics.oxfam.twat.model.room.dao.RoleDao;
import com.infomatics.oxfam.twat.model.room.dao.TeamDao;
import com.infomatics.oxfam.twat.model.room.dao.UserDao;
import com.infomatics.oxfam.twat.model.room.dao.WalkerDao;
import com.infomatics.oxfam.twat.model.room.entity.CheckpointEntity;
import com.infomatics.oxfam.twat.model.room.entity.ContactEntity;
import com.infomatics.oxfam.twat.model.room.entity.DashboardEntity;
import com.infomatics.oxfam.twat.model.room.entity.EntryLog;
import com.infomatics.oxfam.twat.model.room.entity.MemberEntity;
import com.infomatics.oxfam.twat.model.room.entity.NotificationEntity;
import com.infomatics.oxfam.twat.model.room.entity.RetirementEntity;
import com.infomatics.oxfam.twat.model.room.entity.RoleEntity;
import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.room.entity.UserEntity;
import com.infomatics.oxfam.twat.model.room.entity.WalkerEntity;

@Database(entities = {EntryLog.class, MemberEntity.class, TeamEntity.class, ContactEntity.class,
        RoleEntity.class, UserEntity.class, DashboardEntity.class, CheckpointEntity.class,
        WalkerEntity.class, NotificationEntity.class, RetirementEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract EntryDao entryDao();
    public abstract MemberDao memberDao();
    public abstract TeamDao teamDao();
    public abstract ContactDao contactDao();
    public abstract RoleDao roleDao();
    public abstract UserDao userDao();
    public abstract DashboardDao dashboardDao();
    public abstract CheckPointDao checkPointDao();
    public abstract WalkerDao walkerDao();
    public abstract NotificationDao notificationDao();
    public abstract RetirementDao retirementDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}