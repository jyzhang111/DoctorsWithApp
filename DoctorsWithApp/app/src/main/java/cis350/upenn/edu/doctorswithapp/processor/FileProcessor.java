package cis350.upenn.edu.doctorswithapp.processor;

import cis350.upenn.edu.doctorswithapp.data.FileDoctorReader;
import cis350.upenn.edu.doctorswithapp.data.DoctorReader;
import cis350.upenn.edu.doctorswithapp.data.FilePatientReader;
import cis350.upenn.edu.doctorswithapp.data.PatientReader;
import cis350.upenn.edu.doctorswithapp.data.FileMedicationReader;
import cis350.upenn.edu.doctorswithapp.data.MedicationReader;

public class FileProcessor extends Processor {
    public FileProcessor(){
        super();
    }

    protected DoctorReader createDoctorReader(){
        return new FileDoctorReader();
    }

    protected PatientReader createPatientReader(){
        return new FilePatientReader();
    }

    protected MedicationReader createMedicationReader() {
        return new FileMedicationReader();
    }
}
