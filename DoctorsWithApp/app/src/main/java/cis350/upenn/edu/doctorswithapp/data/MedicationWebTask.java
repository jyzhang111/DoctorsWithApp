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
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;

public class MedicationWebTask extends AsyncTask<URL, String, String> implements MedicationReader{
    public MedicationWebTask(){

    }

    /*
    This method is called in background when this object's "execute"
    method is invoked.
    The arguments passed to "execute" are passed to this method.
    */
    protected String doInBackground(URL... urls) {
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
            JSONObject jo = new JSONObject(msg);
            // assumes that JSON object contains a "name" field
            String name = jo.getString("name");
            // this will be passed to onPostExecute method
            return name;
        }
        catch (Exception e) {
            return e.toString();
        }
    }

    /*
    This method is called in foreground after doInBackground finishes.
    It can access and update Views in user interface.
    */
    protected void onPostExecute(String msg) {
        // not implemented but you can use this if you’d like
    }

    public Map<String, TreeSet<MedicationInfo>> getMedications() {
        return new HashMap<String, TreeSet<MedicationInfo>>();
    }

    public void put(String name, MedicationInfo med){}
}