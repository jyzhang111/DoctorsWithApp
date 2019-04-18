package cis350.upenn.edu.doctorswithapp;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import org.json.JSONObject;
import android.os.AsyncTask;


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
            String name = jo.getString("name");
            // this will be passed to onPostExecute method
            return name;
        }
        catch (Exception e) {
            return e.toString();
        }
    }
}
