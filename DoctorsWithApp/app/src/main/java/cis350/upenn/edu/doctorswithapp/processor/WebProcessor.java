package cis350.upenn.edu.doctorswithapp.processor;

import java.net.URL;

import cis350.upenn.edu.doctorswithapp.data.DoctorReader;
import cis350.upenn.edu.doctorswithapp.data.DoctorWebTask;
import cis350.upenn.edu.doctorswithapp.data.MedicationReader;
import cis350.upenn.edu.doctorswithapp.data.MedicationWebTask;
import cis350.upenn.edu.doctorswithapp.data.PatientReader;
import cis350.upenn.edu.doctorswithapp.data.PatientWebTask;
import cis350.upenn.edu.doctorswithapp.data.WebDoctorReader;
import cis350.upenn.edu.doctorswithapp.data.WebMedicationReader;
import cis350.upenn.edu.doctorswithapp.data.WebPatientReader;

public class WebProcessor extends Processor {
    public WebProcessor(){
        super();
    }

    protected DoctorReader createDoctorReader(){ return new WebDoctorReader(); }

    protected PatientReader createPatientReader(){
        return new WebPatientReader();
    }

    protected MedicationReader createMedicationReader() {
        return new WebMedicationReader();
    }
}
