
package cis350.upenn.edu.doctorswithapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;

public class CurrentPillsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private Map<String, TreeSet<MedicationInfo>> medications = MainActivity.processor.getMedications();
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_pills);

        mTextView = findViewById(R.id.textView);

        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });

        Button buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        int counter = 1;
        String patientName = HomePageActivity.patientName;

        if (medications == null || medications.size() == 0) {
            TextView pillNumber = (TextView) findViewById(R.id.pillInfo);
            pillNumber.setText("You do not have any assigned pills!");
        }

        TreeSet<MedicationInfo> patientMedInfo = medications.get(patientName);

        if (patientMedInfo == null || patientMedInfo.size() == 0){
            TextView pillNumber = (TextView) findViewById(R.id.pillInfo);
            pillNumber.setText("You do not have any assigned pills!");
        } else {
            String infoStr = "";

            for (MedicationInfo medInfo : patientMedInfo) {
                if(medInfo.getPillStatus()){
                    continue;
                }

                infoStr = infoStr + "<PILL NUMBER " + counter + ">\n";
                infoStr = infoStr + "NAME OF PILL:   " + medInfo.getName() + "\n";
                infoStr = infoStr + "DOSAGE:   " + medInfo.getDosage() + "\n";
                infoStr = infoStr + "NUMBER OF TIMES PER DAY:   " + medInfo.getNumPerDay() + "\n";

                infoStr = infoStr + "SCHEDULE:   ";
                List<String> scheduleList = medInfo.getSchedule();
                for (int i = 0; i < scheduleList.size(); i++) {
                    if (i != scheduleList.size() - 1) {
                        infoStr = infoStr + scheduleList.get(i) + ", ";
                    } else {
                        infoStr = infoStr + scheduleList.get(i) + "\n";
                    }
                }

                infoStr = infoStr + "USAGE:   ";
                List<String> usageList = medInfo.getUsages();
                for (int i = 0; i < usageList.size(); i++) {
                    if (i != usageList.size() - 1) {
                        infoStr = infoStr + usageList.get(i) + ", ";
                    } else {
                        infoStr = infoStr + usageList.get(i) + "\n";
                    }
                }
                infoStr = infoStr + "SIDE EFFECT(S):   ";
                List<String> sideEffectList = medInfo.getSideEffectList();
                for (int i = 0; i < sideEffectList.size(); i++) {
                    if (i != sideEffectList.size() - 1) {
                        infoStr = infoStr + sideEffectList.get(i) + ", ";
                    } else {
                        infoStr = infoStr + sideEffectList.get(i) + "\n";
                    }
                }

                infoStr = infoStr + "COLOR: " + medInfo.getColor() + "\n";

                infoStr = infoStr + "\n";
                counter = counter + 1;
            }

            if(infoStr.equals("")){
                infoStr = "You only have past pills!";
            }

            TextView pillInformation = (TextView) findViewById(R.id.pillInfo);
            pillInformation.setText(infoStr);
        }
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);

    }

    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        mTextView.setText(timeText);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm canceled");
    }

    public void onBackButtonClick(View view){
        Intent i = new Intent();
        finish();
    }

    public void onLogoutButtonClick(View view){
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
