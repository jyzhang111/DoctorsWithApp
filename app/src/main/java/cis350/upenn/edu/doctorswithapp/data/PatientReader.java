package cis350.upenn.edu.doctorswithapp.data;

import cis350.upenn.edu.doctorswithapp.shared_classes.PatientInfo;
import java.util.Map;

public interface PatientReader {
    public Map<String, PatientInfo> getPatients();

    public void put(String user, PatientInfo pi);
    // edits existing info if patient user already there, adds new patient if not
}
