package cis350.upenn.edu.doctorswithapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Map;
import java.util.TreeSet;

import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;

public class AlertReceiver extends BroadcastReceiver {
    private Map<String, TreeSet<MedicationInfo>> medications = MainActivity.processor.getMedications();

    @Override
    public void onReceive(Context context, Intent intent) {
        TreeSet<MedicationInfo> medicationSet = medications.get(HomePageActivity.patientName);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("Pill Reminder",
                "Take ");
        notificationHelper.getManager().notify(1, nb.build());
    }
}
