package com.example.tkfinalproject.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tkfinalproject.R;

/**
 * AlarmReceiver is a BroadcastReceiver that handles alarm events.
 * When the alarm is triggered, it displays a notification to the user.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     * In this case, it builds and displays a notification based on the received Intent's information.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract the message body from the intent
        String messageBody = intent.getStringExtra("info");

        // Create a BigTextStyle for the notification to display a larger text
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                .bigText(messageBody);

        // Build the notification with various settings like icon, title, text, style, and priority
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.newlogo)  // Set the small icon for the notification
                .setContentTitle("פרטי עסקת הטרייד-אין עם RePepHole")  // Set the content title of the notification
                .setContentText("לחץ כדי להרחיב")  // Set the content text of the notification
                .setStyle(bigTextStyle)  // Set the style to BigTextStyle
                .setPriority(NotificationCompat.PRIORITY_HIGH)  // Set the priority of the notification to high
                .setAutoCancel(true);  // Set the notification to be automatically canceled when clicked

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Show the notification with a unique ID
        notificationManager.notify(1, builder.build());
    }
}

