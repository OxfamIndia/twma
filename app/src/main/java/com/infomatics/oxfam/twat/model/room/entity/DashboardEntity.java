package com.infomatics.oxfam.twat.model.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.infomatics.oxfam.twat.util.DataConverter;

import java.util.List;

@Entity(tableName = "dashboard_table")
public class DashboardEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="team_bib_checkins")
    @TypeConverters(DataConverter.class)
    private List<Integer> teamBibCheckIns;

    @ColumnInfo(name="team_bib_checkouts")
    @TypeConverters(DataConverter.class)
    private List<Integer> teamBibCheckOuts;

  @ColumnInfo(name="team_bib_retirements")
    @TypeConverters(DataConverter.class)
    private List<Integer> teamBibRetirements;

    @ColumnInfo(name = "totalTeamCheckins")
    private int totalTeamCheckIns;

    @ColumnInfo(name = "totalTeamCheckOuts")
    private int totalTeamCheckOuts;

    @ColumnInfo(name = "totalTeamRetirements")
    private int totalTeamRetirements;

    @ColumnInfo(name="checkpoint_id")
    private int checkpointId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalTeamCheckIns() {
        return totalTeamCheckIns;
    }

    public void setTotalTeamCheckIns(int totalTeamCheckIns) {
        this.totalTeamCheckIns = totalTeamCheckIns;
    }

    public int getTotalTeamCheckOuts() {
        return totalTeamCheckOuts;
    }

    public void setTotalTeamCheckOuts(int totalTeamCheckOuts) {
        this.totalTeamCheckOuts = totalTeamCheckOuts;
    }

    public void setTotalTeamRetirements(int totalTeamRetirements){
        this.totalTeamRetirements = totalTeamRetirements;
    }

    public int getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(int checkpointId) {
        this.checkpointId = checkpointId;
    }

    public List<Integer> getTeamBibCheckIns() {
        return teamBibCheckIns;
    }

    public void setTeamBibCheckIns(List<Integer> teamBibCheckIns) {
        this.teamBibCheckIns = teamBibCheckIns;
    }

    public List<Integer> getTeamBibCheckOuts() {
        return teamBibCheckOuts;
    }

    public void setTeamBibCheckOuts(List<Integer> teamBibCheckOuts) {
        this.teamBibCheckOuts = teamBibCheckOuts;
    }

    public void setTeamRetirements(List<Integer> teamBibRetirements){
        this.teamBibRetirements = teamBibRetirements;
    }
    public int getTotalTeamRetirements() {
        return totalTeamRetirements;
    }

    public List<Integer> getTeamBibRetirements() {
        return teamBibRetirements;
    }

    public void setTeamBibRetirements(List<Integer> teamBibRetirements) {
        this.teamBibRetirements = teamBibRetirements;
    }
}
