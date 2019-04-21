package cis350.upenn.edu.doctorswithapp;

import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;
import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.List;




public class InfoClass extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String data = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        TextView userName = (TextView) findViewById(R.id.username);
        TextView name = (TextView) findViewById(R.id.name);
        TextView gender = (TextView) findViewById(R.id.gender);
        TextView age = (TextView) findViewById(R.id.age);
        TextView insuranceComp = (TextView) findViewById(R.id.insuranceCompany);
        TextView insuranceNum = (TextView) findViewById(R.id.insuranceNumber);
        TextView doctor = (TextView) findViewById(R.id.doctor);
        TextView allergies = (TextView) findViewById(R.id.allergies);
        TextView pastSurgeries = (TextView) findViewById(R.id.pastSurgeries);
        TextView phoneNum = (TextView) findViewById(R.id.phoneNum);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            data = extras.getString("USERNAME");
        }

        PatientInfo patient = MainActivity.processor.getPatient(data);
        String patientName = patient.getName();
        List<Doctor> doctors = patient.getDoctors();
        String docString = "";
        for (Doctor currDoc : doctors) {
            if (currDoc != null) {
                docString += currDoc.getName() + "/";
            }
        }



        userName.setText("USERNAME: " + data);
        name.setText("NAME: " + patientName);
        gender.setText("GENDER: " + patient.getGender());
        age.setText("AGE: " + Integer.toString(patient.getAge()));
        insuranceComp.setText("INSURANCE COMPANY: " + patient.getInsComp());
        insuranceNum.setText("INSURANCE NUMBER: " + patient.getInsNum());
        doctor.setText("DOCTOR: " + docString);
        allergies.setText("ALLERGIES: " + patient.getAllergies());
        pastSurgeries.setText("PAST SURGERIES: " + patient.getPastSurgeries());
        phoneNum.setText("Patient Phone Number: "+ patient.getPhoneNum());

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
