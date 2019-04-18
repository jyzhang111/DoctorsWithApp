package cis350.upenn.edu.doctorswithapp.data;

import android.util.Log;

import cis350.upenn.edu.doctorswithapp.MainActivity;
import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class FileDoctorReader implements DoctorReader{
    public FileDoctorReader(){
        File path = MainActivity.context.getFilesDir();
        File file = new File(path, "doctors.txt");

        try {
            file.createNewFile();
            PrintWriter out = new PrintWriter(new FileOutputStream(file, false));
            out.print("Alice\t[]\nBob\t[]\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("blah", "couldn't create file in FileDoctorReader.");
        }
    }

    public List<Doctor> getDoctors(){
        List<Doctor> doctors = new ArrayList<Doctor>();

        File path = MainActivity.context.getFilesDir();
        File file = new File(path, "doctors.txt");

        Scanner in = null;
        try{
            in = new Scanner(file);
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
