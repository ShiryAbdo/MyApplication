package com.example.shady.myapplication.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.shady.myapplication.activities.MainActivity;
import com.example.shady.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyReceiver extends BroadcastReceiver {
    int MID=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        long when = System.currentTimeMillis();
        String medName = intent.getStringExtra("name_of_medecines");
        int medHour = intent.getIntExtra("Hour_time", 0);
        int medMin = intent.getIntExtra("Mint_time", 0);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault());
        String dateFormatted = formatter.format(when);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.show_pill)
                .setContentTitle(medName)
                .setContentText(medHour + ":" + medMin)
                .setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;

    }
}


