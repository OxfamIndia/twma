package com.infomatics.oxfam.twat.model.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contact_table")
public class ContactEntity {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("number")
    @Expose
    @ColumnInfo(name = "number")
    private String number;

    @SerializedName("role")
    @Expose
    @ColumnInfo(name="role")
    private String role;

    @SerializedName("posting")
    @Expose
    @ColumnInfo(name="posting")
    private String posting;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }
}
