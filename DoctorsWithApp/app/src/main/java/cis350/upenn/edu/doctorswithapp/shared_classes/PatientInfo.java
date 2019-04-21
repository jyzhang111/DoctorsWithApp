package cis350.upenn.edu.doctorswithapp.shared_classes;
import java.net.URL;


import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class PatientInfo {
    private String password;
    private String name;
    private int age;
    private String gender;
    //put more fields here, make new getters and setter for them
    private List<Doctor> doctors;
    private String insuranceCompany;
    private String insuranceNumber;
    private String allergies;
    private String pastSurgeries;
    private int phoneNum;

    public PatientInfo(String password, String name, int age, String gender, Doctor doctor,
                       String insuranceCompany, String insuranceNumber, String allergies, String pastSurgeries, int phoneNum){
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;

        doctors = new ArrayList<Doctor>();
        doctors.add(doctor);

        this.insuranceCompany = insuranceCompany;
        this.insuranceNumber = insuranceNumber;
        this.allergies = allergies;
        this.pastSurgeries = pastSurgeries;
        this.phoneNum = phoneNum;
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
    public  int getPhoneNum(){ return  phoneNum;}
//    public List<String> getDoctors() {
//        try {
//            URL url = new URL("http://10.0.2.2:3000/apiPatient?id=" + name);
//            AccessWebTask task = new AccessWebTask();
//            task.execute(url);
//            String name = task.get();
//            List<String> docs = Arrays.asList(name.split("\\s*,\\s*"));
//            return docs;
//        }
//        catch (Exception e) {
//            List<String> str = new ArrayList<String>();
//            return str;
//        }
//    }




    public String getInsComp() { return insuranceCompany; }
    public String getInsNum() { return insuranceNumber; }
    public String getAllergies() {return allergies; }
    public String getPastSurgeries() { return pastSurgeries; }
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
    public void setInsComp(String insComp) { this.insuranceCompany = insComp;  }
    public void setInsNum(String insNum) { this.insuranceNumber = insNum; }
    public void addAllergy(String allerg) { this.allergies = this.allergies + allerg; }
    public void addSurgery(String surgery) { this.pastSurgeries = this.pastSurgeries + surgery; }
    public void setPhoneNum(int phoneNum){this.phoneNum = phoneNum;}
}