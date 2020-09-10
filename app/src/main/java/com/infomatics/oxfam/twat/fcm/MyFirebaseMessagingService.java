package com.infomatics.oxfam.twat.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.infomatics.oxfam.twat.R;
import com.infomatics.oxfam.twat.model.room.entity.NotificationEntity;
import com.infomatics.oxfam.twat.repository.ApiRepository;
import com.infomatics.oxfam.twat.repository.AppDatabase;
import com.infomatics.oxfam.twat.util.AppConstants;
import com.infomatics.oxfam.twat.view.notification.NotificationActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    Map<String, String> data;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());



        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());


        }
        data = remoteMessage.getData();

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        sendNotification(remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.e(TAG, token);
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.APP_PREF, MODE_PRIVATE);
        if(AppConstants.USER_ROLE != null && sharedPreferences.getBoolean(AppConstants.IS_LOGGED_IN, false)){
        if(!sharedPreferences.getBoolean(AppConstants.UPDATED_FCM, false)){
            ApiRepository apiRepository = ApiRepository.getInstance();
            apiRepository.updateFCM(token);
            sharedPreferences.edit().putBoolean(AppConstants.UPDATED_FCM, true).apply();
        }else{
            sharedPreferences.edit().putString(AppConstants.FCM_TOKEN, token).apply();
        }}else{
            sharedPreferences.edit().putString(AppConstants.FCM_TOKEN, token).apply();
        }
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, NotificationActivity.class);
        try {
            if (data != null) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

     /*   Intent yesReceive = new Intent();//attach all keys starting with wzrk_ to your notification extras
        yesReceive.setAction("YES_ACTION");//replace with your custom value
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent noReceive = new Intent();//attach all keys starting with wzrk_ to your notification extras
        noReceive.setAction("NO_ACTION");//replace with your custom value
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(this, 12346, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);

*/
     try{
         AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
         NotificationEntity notificationEntity = new NotificationEntity();
         notificationEntity.setMessage(messageBody);
         appDatabase.notificationDao().insert(notificationEntity);
         Log.e(TAG,"Notification Inserted in db");
     }catch (Exception e){
         e.printStackTrace();
     }
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setWhen(0)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(messageBody))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
