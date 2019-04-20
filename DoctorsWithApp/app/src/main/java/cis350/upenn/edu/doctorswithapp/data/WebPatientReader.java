package cis350.upenn.edu.doctorswithapp.data;

import android.util.Log;

import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;
import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;

public class WebPatientReader implements PatientReader {
    @Override
    public Map<String, PatientInfo> getPatients() {
        Map<String, PatientInfo> patients = new TreeMap<String, PatientInfo>();

        String largePatientString;
        try {
            URL url = new URL("http://10.0.2.2:3000/apiPatient");
            PatientWebTask task = new PatientWebTask();
            task.execute(url);
            largePatientString = task.get();
        }
        catch (Exception e) {
            return null;
        }

        Scanner in = null;
        try{
            in = new Scanner(largePatientString);
            in.useDelimiter("\t|\n");
            while(in.hasNext()){
                String username = in.next();
                Log.v("username", username);
                String password = in.next();
                Log.v("password", password);

                String name = in.next();
                Log.v("name", name);

                int age = Integer.parseInt(in.next());
                Log.v("age", age + "");

                String gender = in.next();
                Log.v("gender", gender);

                String ds = in.next();
                Log.v("doctors", ds);

                String[] docs = ds.split("[\\[\\],]");
                Doctor doctor = null;
                if(docs.length != 0){
                    doctor = new Doctor(docs[0], new String[0]);
                }

                String insComp = in.next();
                Log.v("insuranceComp", insComp);

                String insNum = in.next();
                Log.v("insuranceNum", insNum);

                String allergies = in.next();
                Log.v("allergies", allergies);

                String pastSurg = in.next();
                Log.v("pastSurgeries", pastSurg);


                PatientInfo pi = new PatientInfo(password, name, age, gender, doctor, insComp, insNum, allergies, pastSurg);
                for(int i =1; i <docs.length; i++) {
                    pi.addDoctor(new Doctor(docs[i], new String[0]));

                }
                patients.put(username, pi);
            }
        }
        catch(Exception e){
            Log.v("here", e.toString());
        }
        finally{
            if(in != null) {
                in.close();
            }
        }
        return patients;
    }

    @Override
    public void put(String user, PatientInfo pi) {

    }
}
