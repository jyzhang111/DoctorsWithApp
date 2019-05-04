package cis350.upenn.edu.doctorswithapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeSet;

import cis350.upenn.edu.doctorswithapp.processor.Processor;
import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;
import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;

public class MessageActivity extends AppCompatActivity {
    private Map<String, TreeSet<MedicationInfo>> medications = MainActivity.processor.getMedications();
    String data;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_page);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
           data = extras.getString("USERNAME");
            MainActivity.processor.setDoctorName(data);
        }
        MainActivity.processor.setDoctorName(data);
    }

    public void onCreateButtonClick(View view){
        EditText m = (EditText)findViewById(R.id.message);
        String message = m.getText().toString();
        //PatientInfo patient = MainActivity.processor.getPatient(data);
        MainActivity.processor.writeMessage(message, data);
        //m.setText("");

        Intent i = new Intent();
        finish();
        m.setText("");
        //Log.v("here", "i am heer");
    }

    public void onCancelButtonClick(View view){
        Intent i = new Intent();
        finish();
    }

    public void onLogoutButtonClick(View view){
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


}
