package com.infomatics.oxfam.twat.util;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infomatics.oxfam.twat.model.login.Permission;
import com.infomatics.oxfam.twat.model.team.Walker;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromCountryLangList(List<Walker> walkers) {
        if (walkers == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Walker>>() {
        }.getType();
        String json = gson.toJson(walkers, type);
        return json;
    }

    @TypeConverter
    public List<Walker> toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Walker>>() {
        }.getType();
        List<Walker> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }

    @TypeConverter
    public List<Permission> toPermissionList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Permission>>() {
        }.getType();
        List<Permission> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }

    @TypeConverter
    public String fromPermissionToString(List<Permission> permissions) {
        if (permissions == null)
            return null;

        Gson gson = new Gson();
        Type type = new TypeToken<List<Permission>>() {
        }.getType();
        String json = gson.toJson(permissions, type);
        return json;
    }


    @TypeConverter
    public List<Integer> toIntList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }

    @TypeConverter
    public String fromPermissionToInt(List<Integer> permissions) {
        if (permissions == null)
            return null;

        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        String json = gson.toJson(permissions, type);
        return json;
    }


}
