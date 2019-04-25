package cis350.upenn.edu.doctorswithapp.data;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import android.os.AsyncTask;
import org.json.JSONException;

public class MedicationWebTask extends AsyncTask<URL, String, String>{

    public MedicationWebTask(){
    }

    /*
    This method is called in background when this object's "execute"
    method is invoked.
    The arguments passed to "execute" are passed to this method.
    */
    protected String doInBackground(URL... urls) {
        StringBuilder sb = new StringBuilder();
        URL url = urls[0];

        if(url.toString().equals("http://10.0.2.2:3000/apiViewMedications")) {

            try {

                HttpURLConnection conn =
                        (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                // read first line of data that is returned
                Scanner in = new Scanner(url.openStream());
                String msg = in.nextLine().trim();

                if(msg.length() == 0) return "";

                if(msg.equals("{}")) return "";

                if(msg.charAt(0) == '{'){
                    JSONObject jo = new JSONObject(msg);
                    // assumes that JSON object contains a "name" field
                    String name = jo.getString("name");
                    sb.append(name + "\t");

                    String patientName = jo.getString("patientName");
                    sb.append(patientName + "\t");

                    String sideEffect = jo.getString("sideEffect");
                    sb.append(sideEffect + "\t");

                    String count = jo.getString("count");
                    sb.append(count + "\t");

                    String timeToTake = jo.getString("timeToTake");
                    sb.append(timeToTake + "\t");

                    String timePerDay = jo.getString("timePerDay");
                    sb.append(timePerDay + "\t");

                    String reason = jo.getString("reason");
                    sb.append(reason + "\t");

                    String color = jo.getString("color");
                    sb.append(color + "\t");


                    String isPastPill = "";
                    try {
                        isPastPill = jo.getString("isPastPill");

                    } catch (JSONException e) {
                        isPastPill = "false";
                    }
//                    if(isPastPill == null) {
//                        isPastPill = "false";
//                    }
                    sb.append(isPastPill + "\n");
                }
                else {
                    // use Android JSON library to parse JSON
                    JSONArray arr = new JSONArray(msg);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jo = arr.getJSONObject(i);
                        // assumes that JSON object contains a "name" field
                        String name = jo.getString("name");
                        sb.append(name + "\t");

                        String patientName = jo.getString("patientName");
                        sb.append(patientName + "\t");

                        String sideEffect = jo.getString("sideEffect");
                        sb.append(sideEffect + "\t");

                        String count = jo.getString("count");
                        sb.append(count + "\t");

                        String timeToTake = jo.getString("timeToTake");
                        sb.append(timeToTake + "\t");

                        String timePerDay = jo.getString("timePerDay");
                        sb.append(timePerDay + "\t");

                        String reason = jo.getString("reason");
                        sb.append(reason + "\t");

                        String color = jo.getString("color");
                        sb.append(color + "\t");


                        String isPastPill = "";
                        try {
                            isPastPill = jo.getString("isPastPill");

                        } catch (JSONException e) {
                            isPastPill = "false";
                        }
//                    if(isPastPill == null) {
//                        isPastPill = "false";
//                    }
                        sb.append(isPastPill + "\n");


                    }
                }

            } catch (Exception e) {
                return e.toString();
            }
        }
        else{
            try {
                //sample code for what to do when we want to post data, when getD is false

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
