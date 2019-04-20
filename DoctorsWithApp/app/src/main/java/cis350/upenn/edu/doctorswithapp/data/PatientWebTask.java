package cis350.upenn.edu.doctorswithapp.data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PatientWebTask extends AsyncTask<URL, String, String>{


    public PatientWebTask(){

    }

    /*
    This method is called in background when this object's "execute"
    method is invoked.
    The arguments passed to "execute" are passed to this method.
    */
    protected String doInBackground(URL... urls) {
        StringBuilder sb = new StringBuilder();
        URL url = urls[0];

        if(url.toString().equals("http://10.0.2.2:3000/apiPatient")) {
            try {
                HttpURLConnection conn =
                        (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                // read first line of data that is returned
                Scanner in = new Scanner(url.openStream());

                String msg = in.nextLine().trim();

                if(msg.length() == 0) return "";

                if(msg.charAt(0) == '{'){
                    JSONObject jo = new JSONObject(msg);
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
                else {
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
}
