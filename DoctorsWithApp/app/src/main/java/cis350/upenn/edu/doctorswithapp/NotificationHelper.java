package cis350.upenn.edu.doctorswithapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import java.util.Date;
import java.util.List;

import java.util.Map;
import java.util.TreeSet;

import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;

public class NotificationHelper extends ContextWrapper {
    public static final String channelL1ID = "channel1ID";
    public static final String channelL1Name = "Channel 1";

    private NotificationManager mManager;


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
        TreeSet<MedicationInfo> medicineSet = medications.get(HomePageActivity.patientName);

        Date currDate = new Date();
        int currHour = currDate.getHours();

        String medName = "";
        String medDosage = "";
        String medColor = "";
        String medUsage = "";
        String medSideEffect = "";


        for (MedicationInfo medicine : medicineSet) {
            List<String> schedule = medicine.getSchedule();
            for (int i = 0; i < schedule.size(); i++) {
                String scheduleHour = schedule.get(i).split(":")[0];
                if (Integer.parseInt(scheduleHour) == currHour) {
                    medName += medicine.getName();
                    medDosage += "" + medicine.getDosage();
                    medColor += medicine.getColor();
                    List<String> usageList = medicine.getUsages();
                    for (int j = 0; j < usageList.size(); j++) {
                        if (j == usageList.size() - 1) {
                            medUsage += usageList.get(j);
                        } else {
                            medUsage += usageList.get(j) + ", ";
                        }
                    }
                    List<String> sideEffectList = medicine.getSideEffectList();
                    for (int k = 0; k < sideEffectList.size(); k++) {
                        if (k == sideEffectList.size() - 1) {
                            medSideEffect += sideEffectList.get(k);
                        } else {
                            medSideEffect += sideEffectList.get(k) + ", ";
                        }
                    }
                }
            }
        }


        String modifiedMessage =  medName + "now! Dosage: " + medDosage + ", Color: " +
                medColor + ", Usage: " + medUsage + ", Side Effect: " + medSideEffect;

        if (medColor.equals("red")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_light));

        } else if (medColor.equals("orange")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_orange_dark));

        } else if (medColor.equals("yellow")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_orange_light));

        } else if (medColor.equals("green")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_green_dark));

        } else if (medColor.equals("blue")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_blue_bright));

        } else if (medColor.equals("indigo")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_blue_dark));

        } else if (medColor.equals("violet")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_purple));

        } else if (medColor.equals("brown")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark));

        } else if (medColor.equals("white")) {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
        } else {
            return new NotificationCompat.Builder(getApplicationContext(), channelL1ID).setContentTitle(title)
                    .setContentText(message + ", " + modifiedMessage).setSmallIcon(R.drawable.ic_default)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        }
    }
}
