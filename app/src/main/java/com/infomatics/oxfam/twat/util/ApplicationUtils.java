package com.infomatics.oxfam.twat.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ApplicationUtils {



    public static boolean isNull(String strCheckNull) {
        return (strCheckNull == null || "null".equals(strCheckNull) || (strCheckNull.trim().length() == 0)) ? true : false;
    }

    public static ProgressDialog getProgressDialog(WeakReference<AppCompatActivity> context, String message, boolean isCancelable){
        ProgressDialog progressDialog = new ProgressDialog(context.get());
        progressDialog.setCancelable(isCancelable);
        progressDialog.setMessage(message);
        return progressDialog;
    }
    public static void showToast(Context context, String message){
        if(message != null)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
