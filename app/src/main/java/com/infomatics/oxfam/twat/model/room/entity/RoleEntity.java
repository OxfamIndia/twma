package com.infomatics.oxfam.twat.model.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "role_table")
public class RoleEntity {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;

    @SerializedName("role")
    @Expose
    @ColumnInfo(name = "role")
    private String role;

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.role;
    }
}
