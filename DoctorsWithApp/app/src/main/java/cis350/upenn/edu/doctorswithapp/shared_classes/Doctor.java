package cis350.upenn.edu.doctorswithapp.shared_classes;

public class Doctor {
    private String name;
    private String[] patNames;

    public Doctor(String name, String[] patNames){
        this.name = name;
        this.patNames = patNames;
    }

    public String getName(){
        return name;
    }
    public String[] getPatNames() { return patNames; }
}
