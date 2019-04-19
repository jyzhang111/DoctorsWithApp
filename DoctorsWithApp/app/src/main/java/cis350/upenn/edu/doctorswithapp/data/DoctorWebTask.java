package cis350.upenn.edu.doctorswithapp.data;
import cis350.upenn.edu.doctorswithapp.MainActivity;
import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

public class DoctorWebTask extends AsyncTask<URL, String, String> implements DoctorReader {
    private boolean getD;

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

    public List<Doctor> getDoctors(){
        List<Doctor> doctors = new ArrayList<Doctor>();

        getD = true;

        String largeDoctorString;

        try {
            URL url = new URL("http://10.0.2.2:3000/apiDoctor");
            DoctorWebTask task = this;
            task.execute(url);
            largeDoctorString = task.get();
            Log.v("doc string",largeDoctorString);
        }
        catch (Exception e) {
            return null;
        }

        Scanner in = null;
        try{
            in = new Scanner(largeDoctorString);
            in.useDelimiter("\t|\n");
            while(in.hasNext()){
                String name = in.next();
                String patArrayString = in.next();
                in.nextLine();

                patArrayString = patArrayString.substring(1, patArrayString.length()-1);
                String[] patNames = patArrayString.split(",");

                Doctor temp = new Doctor(name, patNames);
                doctors.add(temp);
            }
        }
        catch(Exception e){
            throw new IllegalStateException(e);
        }
        finally{
            in.close();
        }
        return doctors;
    }
}