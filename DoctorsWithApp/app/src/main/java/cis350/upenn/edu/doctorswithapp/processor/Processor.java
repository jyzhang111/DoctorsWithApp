package cis350.upenn.edu.doctorswithapp.processor;

import android.util.Log;

import cis350.upenn.edu.doctorswithapp.data.DoctorReader;
import cis350.upenn.edu.doctorswithapp.data.PatientReader;
import cis350.upenn.edu.doctorswithapp.data.MedicationReader;
import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;
import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;
import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;
import cis350.upenn.edu.doctorswithapp.data.MedicationWebTask;


import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public abstract class Processor {
    private DoctorReader dr;
    private PatientReader pr;
    private MedicationReader med;
    private Map<String, PatientInfo> patients;
    private List<Doctor> doctors;
    private String doctorName;
//    private Map<String, TreeSet<MedicationInfo>> medications;


    public Processor(){
        dr = createDoctorReader();
        pr = createPatientReader();
        med = createMedicationReader();
        patients = pr.getPatients();
        doctors = dr.getDoctors();
//        medications = med.getMedications();
    }

    public boolean usernameTaken(String user){
        return patients.containsKey(user);
    }

    public boolean correctLogin(String user, String password){
        return patients.get(user).getPassword().equals(password);
    }


    public void createNewAccount(String user, String password, String name, int age, String gender, String doctor,
                                 String insuranceCompany, String insuranceNumber, String allergies, String pastSurgeries, long phoneNum){
        this.doctorName = doctor;
        Doctor doc = new Doctor(doctor, new String[0]);
        PatientInfo temp = new PatientInfo(password, name, age, gender, doc, insuranceCompany, insuranceNumber, allergies, pastSurgeries, phoneNum);
        patients.put(user, temp);
        pr.put(user, temp);
    }


    public boolean isDoc(String name){
        for(Doctor d : doctors){
            if(d.getName().equals(name)){
                return true;
            }
        }

        return false;
    }

    public PatientInfo getPatient(String username) {
        if(patients.containsKey(username)) {
            return patients.get(username);
        } else {
            return null;
        }
    }

    public void setDoctorName(String doctorName){
        this.doctorName = doctorName;
    }

    public String getDoctorName(){
        //Log.v("here", "i am here");
        return this.doctorName;
    }
    public void writeMessage(String message, String data){
        PatientInfo p = getPatient(data);
        List<Doctor> doctors = p.getDoctors();
        for(Doctor d : doctors){
            d.setMessage(message);
            d.printString();
            Log.v("printName", d.getName());
            dr.put(d.getName(), d.getMessage(), p.getName());


        }

    }

    public Map<String, TreeSet<MedicationInfo>> getMedications(){
        return med.getMedications();
    }

    protected abstract DoctorReader createDoctorReader();

    protected abstract PatientReader createPatientReader();

    protected abstract MedicationReader createMedicationReader();
}
