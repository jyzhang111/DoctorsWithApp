package cis350.upenn.edu.doctorswithapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeSet;

import cis350.upenn.edu.doctorswithapp.data.FileMedicationReader;
import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;

public class SideEffectsActivity extends AppCompatActivity {
    private Map<String, TreeSet<MedicationInfo>> medications = MainActivity.processor.getMedications();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_effects_page);
        Button btn = (Button) findViewById(R.id.showSideEffects);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientName = HomePageActivity.patientName;
                //String patientName = "wenjie";
                TreeSet<MedicationInfo> patientMedicationInfo = medications.get(patientName);
                if(patientMedicationInfo == null){
                    TextView textView = (TextView) findViewById(R.id.sideEffectsDisplay);
                    textView.setText("Could not find any pills for patient: " + patientName);
                }
                else {
                    String content = "";
                    for (MedicationInfo eachMedication : patientMedicationInfo) {

                        content += eachMedication.getName();
                        content += ": ";
                        content += eachMedication.getSideEffectList().toString();
                    }

                    TextView textView = (TextView) findViewById(R.id.sideEffectsDisplay);
                    textView.setText("Here is side effects: \n" + content);
                }
            }
        });
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
