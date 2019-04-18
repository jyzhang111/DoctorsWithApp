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

public class DoctorWebTask  extends AsyncTask<URL, String, String> implements DoctorReader {
    /*
    This method is called in background when this object's "execute"
    method is invoked.
    The arguments passed to "execute" are passed to this method.
    */
    protected String doInBackground(URL... urls) {
        try {

            File path = MainActivity.context.getFilesDir();
            File file = new File(path, "doctors.txt");

            if(!file.exists()) {
                try {
                    file.createNewFile();
                    PrintWriter out = new PrintWriter(new FileOutputStream(file, false));
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
                    for(int i = 0; i < arr.length(); i++) {
                        JSONObject jo = arr.getJSONObject(i);
                        // assumes that JSON object contains a "name" field
                        String name = jo.getString("name");
                        out.print(name);
                        String password = jo.getString("docPassword");
                        out.print(password);
                        JSONArray patientArray = jo.getJSONArray("patArray");
                        for (int j = 0; j < patientArray.length(); j++) {
                            out.print(patientArray.optString(j));
                        }
                    }

                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v("blah", "couldn't create file in FileDoctorReader.");
                }
            }

        }
        catch (Exception e) {
            return e.toString();
        }

        return "";
    }

    public List<Doctor> getDoctors(){
        List<Doctor> doctors = new ArrayList<Doctor>();

        File path = MainActivity.context.getFilesDir();
        File file = new File(path, "doctors.txt");

        Scanner in = null;
        try{
            in = new Scanner(file);
            while(in.hasNext()){
                String name = in.next();
                Doctor temp = new Doctor(name);
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