package com.makhalibagas.searchgithub.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.makhalibagas.searchgithub.MyReceiver;
import com.makhalibagas.searchgithub.R;

import java.util.Calendar;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {


    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int notificationDaily = preferences.getInt("user_notification", 0);
        Switch switch1 = findViewById(R.id.switch1);
        if (notificationDaily == 1){
            switch1.setChecked(true);
        }else {
            switch1.setChecked(false);
        }



        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.settings);
        TextView language = findViewById(R.id.bahasa);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });

        TextView fav = findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(mIntent);
            }
        });
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setReminderDailyOn(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("user_notification",1);
                    editor.apply();
                    Toast.makeText(SettingsActivity.this, "daily reminder active", Toast.LENGTH_SHORT).show();
                }else {
                    setReminderDailyOff(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("user_notification",0);
                    editor.apply();
                    Toast.makeText(SettingsActivity.this, "daily reminder not active", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    private void setReminderDailyOn(Context applicationContext) {
        Intent intent = new Intent(applicationContext, MyReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 99, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        if (alarmManager != null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }
    private void setReminderDailyOff(Context applicationContext) {
        Intent intent = new Intent(SettingsActivity.this, MyReceiver.class);

        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 99, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }
}
