package com.infomatics.oxfam.twat.util;

import android.databinding.ObservableBoolean;

import com.infomatics.oxfam.twat.model.login.Permission;
import com.infomatics.oxfam.twat.model.login.Role;

import java.util.ArrayList;
import java.util.List;

public class AppConstants {
    public static final String TAG = "OXFAM_ADMIN";
    public static final String SCREEN_NAME = "SCREEN_NAME";
    public static final String IS_LOST_FOUND = "LOST_AND_FOUND";
    public static final String BASE_URL = "https://twma.oxfamindia.org/apis/public/";
    public static final String DATE_FORMAT = "dd/MMM hh:mm:ss";
    public static final String TAG_DETAILS = "TAG_DETAILS";
    public static final int TEAM_SIZE = 3;
    public static final String NETWORK_KEY = "IS_NETWORK_REQ";
    public static final String UPDATED_FCM = "UPDATED_FCM";
    public static final String FCM_TOKEN = "FCM_TOKEN";
    public static final String DUTY_PERSON = "DUTY_PERSON";
    public static final String DUTY_PERSON_ID = "DUTY_PERSON_ID";
    public static String APP_PREF = "TWMA_PREF";
    public static String IS_LOGGED_IN = "isLoggedIn";
    public static boolean getData = false;
    public static final int SYNC_COUNTER = 5;
    public static List<Permission> PERMISSIONS = null;
    public static Role USER_ROLE = null;
    public static  String ROLE = "Admin";
    public static int CHECKPOINT_ID = 0;
    public static int USER_ID;
    public static final int LOCATION_REQUEST = 1000;
    public static final int GPS_REQUEST = 1001;
    public static final int REQUEST_PERMISSION = 1;
    public static final int REQUEST_SETTINGS = 2;
    public static String FIRST_NAME="";
    public static String LAST_NAME="";
    public static boolean ADDED_NEW_CONTACT = false;
    public static String USERNAME="";
    public static boolean hasFetchedCPData = false;
    public static String CP_NAME ="";
    public static String CP_STATUS = "";
    public static int WALKER_COUNT = 0;
    public static int TEAM_COUNT = 0;

    public static ArrayList<String> allBibNos = new ArrayList<>();
    public static ObservableBoolean hasFetchedAllData = new ObservableBoolean();
    public static boolean SYNCNG_DATA;
}
