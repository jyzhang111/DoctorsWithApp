package cis350.upenn.edu.doctorswithapp.processor;

import cis350.upenn.edu.doctorswithapp.data.FileDoctorReader;
import cis350.upenn.edu.doctorswithapp.data.DoctorReader;
import cis350.upenn.edu.doctorswithapp.data.FilePatientReader;
import cis350.upenn.edu.doctorswithapp.data.PatientReader;

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
}
