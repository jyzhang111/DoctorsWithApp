package cis350.upenn.edu.doctorswithapp.data;

import android.app.Activity;
import android.util.Log;

import cis350.upenn.edu.doctorswithapp.MainActivity;
import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;
import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import android.content.Context;

public class FilePatientReader implements PatientReader {

    public FilePatientReader(){

    }

    public Map<String, PatientInfo> getPatients() {
        Map<String, PatientInfo> patients = new TreeMap<String, PatientInfo>();
        File path = MainActivity.context.getFilesDir();
        File file = new File(path, "patients.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            }
            catch(IOException e){
                e.printStackTrace();
                Log.v("blah", "couldn't create file.");
            }

            return patients;
        }
        Scanner in = null;
        try{
            in = new Scanner(file);
            in.useDelimiter("\t|\n");
            while(in.hasNext()){
                String username = in.next();
                String password = in.next();
                String name = in.next();
                int age = Integer.parseInt(in.next());
                String gender = in.next();
                String ds = in.next();
                String[] docs = ds.split("[\\[\\],]");
                Doctor doctor = new Doctor(docs[0], new String[0]);
                String insComp = in.next();
                String insNum = in.next();
                String allergies = in.next();
                String pastSurg = in.next();
                int phoneNum = Integer.parseInt(in.next());
                if(in.hasNext()) in.nextLine();

                PatientInfo pi = new PatientInfo(password, name, age, gender, doctor, insComp, insNum, allergies, pastSurg, phoneNum);

                for(int i = 1; i < docs.length; i++){
                    doctor = new Doctor(docs[i], new String[0]);
                    pi.addDoctor(doctor);
                }

                patients.put(username, pi);
            }
        }
        catch(Exception e){
            Log.v("here", "could not create Scanner in getPatients");
        }
        finally{
            if(in != null) {
                in.close();
            }
        }
        return patients;
    }

    public void put(String user, PatientInfo pi){ //dumb implementation for our current dumb database
        //I just rewrite the entire log file cause I'm lazy
        Map<String, PatientInfo> patients = getPatients();

        patients.put(user, pi);

        File path = MainActivity.context.getFilesDir();
        File file = new File(path, "patients.txt");

        if(!file.exists()){
            try {
                file.createNewFile();
            }
            catch(IOException e){
                Log.v("filestuff", "could not create file in put");
            }
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(file, false));

            for (Map.Entry<String, PatientInfo> entry : patients.entrySet()) {
                List<Doctor> doctors = entry.getValue().getDoctors();

                String docString = "[";
                for(Doctor d : doctors){
                    docString = docString+ d.getName() + ",";
                }

                docString = docString.substring(0, docString.length()-1);
                docString = docString + "]";

                writer.println(entry.getKey() + "\t" + entry.getValue().getPassword() + "\t"
                        + entry.getValue().getName() + "\t" + entry.getValue().getAge() + "\t"
                        + entry.getValue().getGender() + "\t" + docString + "\t"
                        + entry.getValue().getInsComp() + "\t" + entry.getValue().getInsNum() + "\t"
                        + entry.getValue().getAllergies() + "\t"  + entry.getValue().getPastSurgeries() + "\t"
                        + entry.getValue().getPhoneNum());



            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            writer.close();
        }
    }

}
