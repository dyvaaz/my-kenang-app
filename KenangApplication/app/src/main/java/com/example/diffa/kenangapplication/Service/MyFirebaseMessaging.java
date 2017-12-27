package com.example.diffa.kenangapplication.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.diffa.kenangapplication.Model.Notifications;
import com.example.diffa.kenangapplication.R;
import com.example.diffa.kenangapplication.RateActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Diffa on 21/12/2017.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
      if(remoteMessage.getNotification().getTitle().equals("Cancel")){
          Handler handler = new Handler(Looper.getMainLooper());
          handler.post(new Runnable(){
              @Override
              public void run(){
                  Toast.makeText(MyFirebaseMessaging.this, ""+remoteMessage.getNotification().getBody(),Toast.LENGTH_SHORT).show();

              }

          });
      }else if(remoteMessage.getNotification().getTitle().equals("Arrived")){
         showArrivedNotification(remoteMessage.getNotification().getBody());
      }
      else if(remoteMessage.getNotification().getTitle().equals("DropOff")){
          openRateActivity(remoteMessage.getNotification().getBody());
      }
    }

    private void openRateActivity(String body) {
        Intent intent = new Intent(this, RateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showArrivedNotification(String body) {
        //work for android api 25 or higher
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),
                0,new Intent(), PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Arrived")
                .setContentText(body)
                .setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }
}
