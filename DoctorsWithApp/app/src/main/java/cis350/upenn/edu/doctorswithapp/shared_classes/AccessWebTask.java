package cis350.upenn.edu.doctorswithapp.shared_classes;
import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import android.os.AsyncTask;
import java.util.List;
import java.util.ArrayList;



public class AccessWebTask extends AsyncTask<URL, String, String>{

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
            JSONArray jsonArray = jo.getJSONArray("doctorArray");

            String ans = "";
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){
                    Doctor doc = (Doctor)jsonArray.get(i);
                    ans+=doc.getName() + ",";
                }
            }
            return ans;
        }
        catch (Exception e) {
            return e.toString();
        }
    }
}
