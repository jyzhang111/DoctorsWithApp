package cis350.upenn.edu.doctorswithapp.data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.List;
import java.io.OutputStream;

import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;
import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;

public class PatientWebTask extends AsyncTask<URL, String, String>{
    private String user;
    private PatientInfo pi;

    public PatientWebTask(){

    }

    public PatientWebTask(String user, PatientInfo pi){
        this.user = user;
        this.pi = pi;
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
                    sb.append(pastSurgeries + "\t");

                    long phoneNum = jo.getLong("phoneNum");
                    sb.append(phoneNum + "\n");
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
                        sb.append(pastSurgeries + "\t");

                        long phoneNum = jo.getLong("phoneNum");
                        sb.append(phoneNum + "\n");
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

                List<Doctor> l = pi.getDoctors();
                String[] doctorNames = new String[l.size()];
                int i = 0;
                for(Doctor d : l){
                    doctorNames[i] = d.getName();
                    i++;
                }


                JSONObject obj = new JSONObject();

                obj.put("username", user);
                obj.put("password", pi.getPassword());
                obj.put("name", pi.getName());
                obj.put("age", pi.getAge());
                obj.put("gender", pi.getGender().toLowerCase());
                obj.put("doctorArray", new JSONArray(doctorNames));
                obj.put("insComp", pi.getInsComp());
                obj.put("insNum", pi.getInsNum());
                obj.put("allergies", pi.getAllergies());
                obj.put("pastSurg", pi.getPastSurgeries());
                obj.put("phoneNum", pi.getPhoneNum());

                OutputStream os = conn.getOutputStream();
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

    /*
    This method is called in foreground after doInBackground finishes.
    It can access and update Views in user interface.
    */
    protected void onPostExecute(String msg) {
        // not implemented but you can use this if you’d like
    }
}
