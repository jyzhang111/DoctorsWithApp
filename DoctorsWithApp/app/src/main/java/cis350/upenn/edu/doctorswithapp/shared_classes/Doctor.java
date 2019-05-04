package cis350.upenn.edu.doctorswithapp.shared_classes;

import android.util.Log;

import java.util.ArrayList;

public class Doctor {
    private String name;
    private String[] patNames;
    private ArrayList<String> message = new ArrayList<String>();
    public Doctor(String name, String[] patNames){
        this.name = name;
        this.patNames = patNames;
    }

    public String getName(){
        return name;
    }
    public String[] getPatNames() { return patNames; }

    public void setMessage(String message) {
        this.message.add(message);
    }
    public ArrayList<String> getMessage(){
        return this.message;
    }
    public void printString(){
        for(String s:message){
            Log.v("name", name);
        }
    }
}
