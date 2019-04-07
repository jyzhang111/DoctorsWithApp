package cis350.upenn.edu.doctorswithapp.shared_classes;

import java.util.List;
import java.util.ArrayList;

public class PatientInfo {
    private String password;
    private String name;
    private int age;
    private String gender;
    //put more fields here, make new getters and setter for them
    private List<Doctor> doctors;

    public PatientInfo(String password, String name, int age, String gender, Doctor doctor){
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;

        doctors = new ArrayList<Doctor>();
        doctors.add(doctor);
    }

    public String getPassword(){
        return password;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getGender(){
        return gender;
    }
    public List<Doctor> getDoctors(){
        return doctors;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void addDoctor(Doctor docter){
        doctors.add(docter);
    }
}
