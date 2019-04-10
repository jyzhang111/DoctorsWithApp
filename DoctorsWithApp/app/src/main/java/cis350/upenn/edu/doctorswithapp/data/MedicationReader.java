package cis350.upenn.edu.doctorswithapp.data;

import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;
import java.util.Map;
import java.util.TreeSet;

public interface MedicationReader {
    public Map<String, TreeSet<MedicationInfo>> getMedications();

    public void put(String name, MedicationInfo med);
}
