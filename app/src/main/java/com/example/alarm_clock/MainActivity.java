package com.example.alarm_clock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    TimePicker tp;
    PendingIntent pi;
    AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tp = findViewById(R.id.timepicker);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
    }
    public void OnToggleClicked(View view){
        long time;
        if(((ToggleButton) view).isChecked()){
            Toast.makeText(MainActivity.this,"Alarm On",Toast.LENGTH_SHORT).show();
            Calendar cal=Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY,tp.getCurrentHour());
            cal.set(Calendar.MINUTE,tp.getCurrentMinute());
            Intent intent=new Intent(this,AlarmReceiver.class);
            pi=PendingIntent.getBroadcast(this,0,intent,0);
            time=(cal.getTimeInMillis()-(cal.getTimeInMillis()%60000));
            if(System.currentTimeMillis()>time){
                if(cal.AM_PM==0)
                    time=time+(1000*60*60*12);
                else
                    time=time+(1000*60*60*24);
                am.setRepeating(AlarmManager.RTC_WAKEUP,time,10000,pi);
            }
            else{
                am.cancel(pi);
                Toast.makeText(MainActivity.this,"Alarm off",Toast.LENGTH_SHORT).show();
            }
        }
    }

}