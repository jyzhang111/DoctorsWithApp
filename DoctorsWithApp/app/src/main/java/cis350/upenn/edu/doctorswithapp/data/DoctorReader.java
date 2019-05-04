package cis350.upenn.edu.doctorswithapp.data;

import cis350.upenn.edu.doctorswithapp.shared_classes.Doctor;
import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;

import java.util.ArrayList;
import java.util.List;

public interface DoctorReader {
    public List<Doctor> getDoctors();
    public void put(String name, ArrayList<String> message, String patientName);
}
