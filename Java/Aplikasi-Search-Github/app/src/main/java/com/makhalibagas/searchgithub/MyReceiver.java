package com.makhalibagas.searchgithub;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.makhalibagas.searchgithub.View.Activity.MainActivity;

import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        intent = new Intent(context, MainActivity.class);
        PendingIntent pending = PendingIntent.getActivity(context, 99, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager systemService = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "99")
                .setContentIntent(pending)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite))
                .setContentTitle(context.getResources().getString(R.string.ContentTittleNotif))
                .setContentText(context.getResources().getString(R.string.ContentTittleNotif))
                .setAutoCancel(true);

        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("99", "github Channel", NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId("99");

            Objects.requireNonNull(systemService).createNotificationChannel(notificationChannel);
        }

        Objects.requireNonNull(systemService).notify(2, notification);
    }
}