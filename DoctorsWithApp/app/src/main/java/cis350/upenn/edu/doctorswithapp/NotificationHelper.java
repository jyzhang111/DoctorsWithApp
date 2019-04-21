package cis350.upenn.edu.doctorswithapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


import java.util.Map;
import java.util.TreeSet;

import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;

public class NotificationHelper extends ContextWrapper {
    public static final String channelL1ID = "channel1ID";
    public static final String channelL1Name = "Channel 1";

    private NotificationManager mManager;
    boolean isFirstTime = true;


    private Map<String, TreeSet<MedicationInfo>> medications = MainActivity.processor.getMedications();




    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel channel1 = new NotificationChannel(channelL1ID, channelL1Name,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message) {
        if (isFirstTime) {
            isFirstTime = false;
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message).setSmallIcon(R.drawable.ic_red);

        } else {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message).setSmallIcon(R.drawable.ic_blue);
        }
//        String color = message.split(":")[0];
//        if (color.equals("red")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_red);
//        } else if (color.equals("orange")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_orange);
//        } else if (color.equals("yellow")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_yellow);
//        } else if (color.equals("green")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_green);
//        } else if (color.equals("blue")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_blue);
//        } else if (color.equals("indigo")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_indigo);
//        } else if( color.equals("violet")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_violet);
//        } else if (color.equals("brown")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_brown);
//        } else if (color.equals("white")) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_white);
//        } else {
//            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
//                    .setContentText(message).setSmallIcon(R.drawable.ic_black);
//        }
    }
}
