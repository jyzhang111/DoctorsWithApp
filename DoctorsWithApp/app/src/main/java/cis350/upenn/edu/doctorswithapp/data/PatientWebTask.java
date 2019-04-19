package cis350.upenn.edu.doctorswithapp.data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
                    String name = jo.getString("name");
                    sb.append(name + "\t");

                    String password = jo.getString("docPassword");
                    sb.append(password + "\t");

                    sb.append("[");
                    JSONArray patientArray = jo.getJSONArray("patArray");
                    for (int j = 0; j < patientArray.length(); j++) {
                        sb.append(patientArray.optString(j) + ",");
                    }
                    sb.append("]\n");
                }


//                String username = in.next();
//                String password = in.next();
//                String name = in.next();
//                int age = Integer.parseInt(in.next());
//                String gender = in.next();
//                String ds = in.next();
//                String[] docs = ds.split("[\\[\\],]");
//                Doctor doctor = new Doctor(docs[0], new String[0]);
//                String insComp = in.next();
//                String insNum = in.next();
//                String allergies = in.next();
//                String pastSurg = in.next();
//                if(in.hasNext()) in.nextLine();

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
        return new HashMap<String, PatientInfo>();
    }

    @Override
    public void put(String user, PatientInfo pi) {

    }
}
