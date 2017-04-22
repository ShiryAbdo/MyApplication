package com.example.shady.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.service.MyReceiver;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    TimePicker pickerTime;
    Button buttonSetAlarm;
    DatePicker pickerDate;
    TextView info;
    private int notification_id;
    MedicInformation medicInformation;
    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activty);
        pickerDate = (DatePicker)findViewById(R.id.pickerdate);
        pickerTime = (TimePicker)findViewById(R.id.pickertime);
        buttonSetAlarm=(Button)findViewById(R.id.setalarm);
        notification_id = (int) System.currentTimeMillis();
        medicInformation= new MedicInformation();

        final Calendar calendar = Calendar.getInstance();


        calendar.set(pickerDate.getYear(),
                pickerDate.getMonth(),
                pickerDate.getDayOfMonth(),
                pickerTime.getCurrentHour(),
                pickerTime.getCurrentMinute(),
                00);



        Intent intent1 = new Intent(AlarmActivity.this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                AlarmActivity.this, notification_id, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) AlarmActivity.this
                .getSystemService(AlarmActivity.this.ALARM_SERVICE);

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);



//// we can set time by open date and time picker dialog
//        buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }
}
