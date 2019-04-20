package cis350.upenn.edu.doctorswithapp.data;
import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;
import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import android.os.AsyncTask;
import java.util.TreeSet;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.sql.SQLException;
import org.json.JSONException;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;

public class MedicationWebTask extends AsyncTask<URL, String, String> implements MedicationReader{
    private boolean getD;

    public MedicationWebTask(){
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

    public Map<String, TreeSet<MedicationInfo>> getMedications() {

        Map<String, TreeSet<MedicationInfo>> medicationMap = new HashMap<>();

        getD = true;

        String largeMedicationString;

        try {
            URL url = new URL("http://10.0.2.2:3000/apiViewMedications");
            MedicationWebTask task = this;
            task.execute(url);
            largeMedicationString = task.get();
            Log.v("med string",largeMedicationString);
        }
        catch (Exception e) {
            return null;
        }

        Scanner in = null;
        try{
            in = new Scanner(largeMedicationString);
            in.useDelimiter("\t|\n");
            while(in.hasNext()){
                String name = in.next();
                Log.v("name", name);

                String patientName = in.next();
                Log.v("patientName", patientName);

                String sideEffect = in.next();
                Log.v("sideEffect", sideEffect);

                List<String> effect = new ArrayList<String>();

                String[] effectComponents = sideEffect.split(", ");
                for (int i = 0; i < effectComponents.length; i++) {
                    effect.add(effectComponents[i]);
                }
                String countStr = in.next();
                Log.v("countStr", countStr);

                int count = Integer.parseInt(countStr);

                String timeToTake = in.next();
                Log.v("timeToTake", timeToTake);

                List<String> schedule = new ArrayList<String>();
                String[] scheduleComponents = timeToTake.split(", ");
                for (int i = 0; i < scheduleComponents.length; i++) {
                    schedule.add(scheduleComponents[i]);
                }
                String timePerDayStr = in.next();
                Log.v("timePerDayStr", timePerDayStr);


                int timePerDay = Integer.parseInt(timePerDayStr);
                String reason = in.next();
                Log.v("reason", reason);

                List<String> usage = new ArrayList<String>();

                String[] usageComponents = reason.split(", ");
                for (int i = 0; i < usageComponents.length; i++) {
                    usage.add(usageComponents[i]);
                }
                String color = in.next();
                Log.v("color", color);


                String isPastPillStr = in.next();
                // Log.v("isPastPillStr", isPastPillStr);

                Boolean isPastPill = Boolean.parseBoolean(isPastPillStr);

//                boolean isPastPill = false;
//                if (isPastPillStr.equals("true")) {
//                    isPastPill = true;
//                }
                MedicationInfo medication = new MedicationInfo(name, patientName, count, timePerDay, schedule, effect,
                        usage, isPastPill, color);

                if (!medicationMap.containsKey(patientName)) {
                    TreeSet<MedicationInfo> medSet = new TreeSet<MedicationInfo>();
                    medSet.add(medication);
                    medicationMap.put(patientName, medSet);
                } else {
                    TreeSet<MedicationInfo> originalSet = medicationMap.get(patientName);
                    originalSet.add(medication);
                }

            }
        }
        catch(Exception e){
            throw new IllegalStateException(e);
        }
        finally{
            in.close();
        }

        return medicationMap;

    }

    public void put(String name, MedicationInfo med){}
}
