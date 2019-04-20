package cis350.upenn.edu.doctorswithapp.data;

import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;

public class WebDoctorReader implements DoctorReader {
    public List<Doctor> getDoctors(){
        List<Doctor> doctors = new ArrayList<Doctor>();

        String largeDoctorString;

        try {
            URL url = new URL("http://10.0.2.2:3000/apiDoctor");
            DoctorWebTask task = new DoctorWebTask();
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
