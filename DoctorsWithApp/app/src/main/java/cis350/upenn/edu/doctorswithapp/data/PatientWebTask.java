package cis350.upenn.edu.doctorswithapp.data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;
import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;

public class PatientWebTask extends AsyncTask<URL, String, String> implements PatientReader {
    private boolean getD;


    public PatientWebTask(){

    }

    /*
    This method is called in background when this object's "execute"
    method is invoked.
    The arguments passed to "execute" are passed to this method.
    */
    protected String doInBackground(URL... urls) {
        StringBuilder sb = new StringBuilder();

        if(getD) {
            try {
                // get the first URL from the array
                URL url = urls[0];
                // create connection and send HTTP request
                HttpURLConnection conn =
                        (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                // read first line of data that is returned
                Scanner in = new Scanner(url.openStream());
                String msg = in.nextLine();
                // use Android JSON library to parse JSON
                JSONArray arr = new JSONArray(msg);


                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jo = arr.getJSONObject(i);
                    // assumes that JSON object contains a "name" field
                    String username = jo.getString("username");
                    sb.append(username + "\t");

                    String password = jo.getString("password");
                    sb.append(password + "\t");

                    String name = jo.getString("name");
                    sb.append(name + "\t");

                    int age = jo.getInt("age");
                    sb.append(age + "\t");

                    String gender = jo.getString("gender");
                    sb.append(gender + "\t");

                    sb.append("[");
                    JSONArray docArray = jo.getJSONArray("doctorArray");
                    for (int j = 0; j < docArray.length(); j++) {
                        sb.append(docArray.optString(j) + ",");
                    }
                    sb.append("]\t");

                    String insuranceComp = jo.getString("insComp");
                    sb.append(insuranceComp + "\t");

                    String insuranceNumber = jo.getString("insNum");
                    sb.append(insuranceNumber + "\t");

                    String aller = jo.getString("allergies");
                    sb.append(aller + "\t");

                    String pastSurgeries = jo.getString("pastSurg");
                    sb.append(pastSurgeries + "\n");
                }
            } catch (Exception e) {
                return e.toString();
            }
        }
        else{
            try {
                //sample code for what to do when we want to post data, when getD is false

                URL url = urls[0];
                HttpURLConnection conn =
                        (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();

            } catch (Exception e) {
                return e.toString();
            }
        }

        return sb.toString();
    }

    /*
    This method is called in foreground after doInBackground finishes.
    It can access and update Views in user interface.
    */
    protected void onPostExecute(String msg) {
        // not implemented but you can use this if you’d like
    }

    @Override
    public Map<String, PatientInfo> getPatients() {
        Map<String, PatientInfo> patients = new TreeMap<String, PatientInfo>();

        getD = true;
        String largePatientString;
        try {
            URL url = new URL("http://10.0.2.2:3000/apiPatient");
            PatientWebTask task = this;
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
