package com.example.tkfinalproject.Utility;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.LastPage.lastPgae;
import com.example.tkfinalproject.UI.mainactivity.MainActivity;

import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    Intent notificationIntent;
    PendingIntent pendingIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Create an explicit intent for the activity to be launched when the notification is tapped
        if (!isAppRunning(context)) {
            // App is not running, start main activity
            notificationIntent = new Intent(context, MainActivity.class);
            // Use TaskStackBuilder to build the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntentWithParentStack(notificationIntent);
            pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        String messageBody = intent.getStringExtra("info");
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                .bigText(messageBody);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.newlogo)
                .setContentTitle("פרטי עסקת הטרייד-אין עם RePepHole")
                .setContentText("לחץ כדי להרחיב")
                .setStyle(bigTextStyle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
    // Method to check if the app is running in the foreground
    private boolean isAppRunning(Context context) {
        // Implement your logic to check if the app is running
        // For example, you can check if a specific activity is currently in the foreground
        // Here's a simple example:
        // Assuming YourCurrentActivity is your current activity
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            return topActivity.getPackageName().equals(context.getPackageName()) && topActivity.getClassName().equals(lastPgae.class.getName());
        }
        return false;
    }
}
