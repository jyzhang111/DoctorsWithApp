package cis350.upenn.edu.doctorswithapp.processor;

import cis350.upenn.edu.doctorswithapp.data.DoctorReader;
import cis350.upenn.edu.doctorswithapp.data.PatientReader;
import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;
import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;
import java.util.List;
import java.util.Map;

public abstract class Processor {
    private DoctorReader dr;
    private PatientReader pr;
    private Map<String, PatientInfo> patients;
    private List<Doctor> doctors;

    public Processor(){
        dr = createDoctorReader();
        pr = createPatientReader();
        patients = pr.getPatients();
        doctors = dr.getDoctors();
    }

    public boolean usernameTaken(String user){
        return patients.containsKey(user);
    }

    public boolean correctLogin(String user, String password){
        return patients.get(user).getPassword().equals(password);
    }

    public void createNewAccount(String user, String password, String name, int age, String gender, String doctor){
        Doctor doc = new Doctor(doctor);
        PatientInfo temp = new PatientInfo(password, name, age, gender, doc);
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

    protected abstract DoctorReader createDoctorReader();

    protected abstract PatientReader createPatientReader();
}
