package com.example.shady.myapplication.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shady.myapplication.R;

public class DialogActivity extends AppCompatActivity {
    Ringtone mAlarmSound;
    Button btunStart ,laterbtn,Cancel ;
    int MID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        btunStart = (Button)findViewById(R.id.startbtn);
        laterbtn = (Button)findViewById(R.id.laterbtn);
        Cancel = (Button)findViewById(R.id.cancelbtn2);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        mAlarmSound = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        TextView btnDone = (TextView)findViewById(R.id.DoneButton);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mAlarmSound.isPlaying())
                    mAlarmSound.play();
            }
        }, 500);

        laterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DialogActivity.this,"on receive",Toast.LENGTH_LONG).show();

                long when = System.currentTimeMillis();
                NotificationManager notificationManager = (NotificationManager) DialogActivity.this
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                Uri urii=Uri.parse("google.navigation:q=55.74274,37.56577 (name)");
                // int compareTo (Uri urii);
                Intent notificationIntent = new Intent(Intent.ACTION_VIEW, urii);

                //Intent notificationIntent = new Intent(DialogActivity.this, NextActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(DialogActivity.this, 0,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                        DialogActivity.this).setSmallIcon(R.drawable.noti)
                        .setContentTitle("Your tripe")
                        .setContentText("Frist Trip").setSound(alarmSound)
                        .setAutoCancel(true).setWhen(when)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                notificationManager.notify(MID, mNotifyBuilder.build());
                MID++;
                mAlarmSound.stop();
                finish();

            }
        });
        btunStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmSound.stop();
                Uri urii=Uri.parse("google.navigation:q=55.74274,37.56577 (name)");
                // int compareTo (Uri urii);
                Intent intent = new Intent(Intent.ACTION_VIEW, urii);
                //intent.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
                startActivity(intent);

                finish();

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmSound.stop();
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        mAlarmSound.stop();
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            return false;
        }
        return true;
    }





    public static class AlarmReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {

            if (intent.getBooleanExtra("Test", false)){
                Log.i("Test", "Found");
            } else{
                Log.i("Test", "Nada");
            }
            Intent dialogIntent = new Intent(context, DialogActivity.class);
            dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dialogIntent);
        }


    }
}
