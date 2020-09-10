package com.infomatics.oxfam.twat.model.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.infomatics.oxfam.twat.model.room.entity.TeamEntity;
import com.infomatics.oxfam.twat.model.team.Walker;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addTeams(TeamEntity... teamEntities);

    @Query("Delete from team_table")
    void removeAll();

    @Query("SELECT Count(id) from team_table")
    int getAllTeamCount();

    @Query("Select teamTitle from team_table WHERE teamTitle LIKE :teamName")
    List<String> getTeamsByName(String teamName);

    @Query("Select * from team_table")
    LiveData<List<TeamEntity>> getAllTeamNames();

    @Query("Select Count(id) from team_table where type = :type")
    int getTeamCountByType(int type);

    @Query("Select * from team_table where teamId LIKE :teamId")
    TeamEntity getTeamById(int teamId);

    @Query("Select * from team_table where leaderId LIKE :leaderId")
    TeamEntity getWalkerDetails(int leaderId);
}
