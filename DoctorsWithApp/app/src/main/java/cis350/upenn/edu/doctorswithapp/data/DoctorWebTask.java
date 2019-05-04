package cis350.upenn.edu.doctorswithapp.data;

import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import android.os.AsyncTask;
import android.util.Log;

import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;

public class DoctorWebTask extends AsyncTask<URL, String, String> {
    /*
    This method is called in background when this object's "execute"
    method is invoked.
    The arguments passed to "execute" are passed to this method.
    */
    private ArrayList<String> message;
    private String docName;
    private String patientName;
    public DoctorWebTask(String docName, ArrayList<String> message, String patientName){
        this.message = message;
        this.docName = docName;
        this.patientName = patientName;
    }

    public DoctorWebTask(){}
    protected String doInBackground(URL... urls) {
        StringBuilder sb = new StringBuilder();
        URL url = urls[0];

        if(url.toString().equals("http://10.0.2.2:3000/apiDoctor")) {

            try {
                HttpURLConnection conn =
                        (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                // read first line of data that is returned
                Scanner in = new Scanner(url.openStream());
                String msg = in.nextLine().trim();

                if(msg.length() == 0) return "";

                if(msg.charAt(0) == '{'){
                    JSONObject jo = new JSONObject(msg);
                    // assumes that JSON object contains a "name" field
                    String name = jo.getString("name");
                    sb.append(name + "\t");

//                    String password = jo.getString("docPassword");
//                    sb.append(password + "\t");

                    sb.append("[");
                    JSONArray patientArray = jo.getJSONArray("patArray");
                    for (int j = 0; j < patientArray.length(); j++) {
                        sb.append(patientArray.optString(j) + ",");
                    }
                    sb.append("]\n");
                }
                else {
                    // use Android JSON library to parse JSON
                    JSONArray arr = new JSONArray(msg);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jo = arr.getJSONObject(i);
                        // assumes that JSON object contains a "name" field
                        String name = jo.getString("name");
                        sb.append(name + "\t");

//                    String password = jo.getString("docPassword");
//                    sb.append(password + "\t");

                        sb.append("[");
                        JSONArray patientArray = jo.getJSONArray("patArray");
                        for (int j = 0; j < patientArray.length(); j++) {
                            sb.append(patientArray.optString(j) + ",");
                        }
                        sb.append("]\n");
                    }
                }

            } catch (Exception e) {
                return e.toString();
            }
        }
        else{
            try {
                HttpURLConnection conn =
                        (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                JSONObject obj = new JSONObject();

                obj.put("messageArray", new JSONArray(message));
                obj.put("doctorName", docName);
                obj.put("patientName", patientName);
                OutputStream os = conn.getOutputStream();
                Log.v("debug1", docName);
                os.write(obj.toString().getBytes("UTF-8"));
                os.close();
                conn.getResponseCode();
                conn.disconnect();

                return "Ok";
            } catch (Exception e) {
                return e.toString();
            }
        }

        return sb.toString();
    }
}