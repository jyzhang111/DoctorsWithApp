package cis350.upenn.edu.doctorswithapp;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import android.content.Context;



public class HomePageActivity extends AppCompatActivity {
    public static String patientName = "";
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            patientName = extras.getString("USERNAME");
        }
    }

    public void onLogoutButtonClick(View view){
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void onInfoButtonClick(View view){
        Intent i = new Intent(this, InfoClass.class);
        i.putExtra("USERNAME", patientName);
        startActivity(i);

    }

    public void onPastPillsButtonClick(View view){
        Intent i = new Intent(this, PastPillsActivity.class);
        startActivity(i);
    }
    public void onSideEffectsButtonClick(View view){
        Intent i = new Intent(this, SideEffectsActivity.class);
        startActivity(i);
    }
    public void onCurrentPillsButtonClick(View view){
        Intent i = new Intent(this, CurrentPillsActivity.class);
        startActivity(i);
    }
    public void onSendMessageButtonClick(View view){
        Intent i = new Intent(this, MessageActivity.class);
        i.putExtra("USERNAME", patientName);
        startActivity(i);
    }
}
