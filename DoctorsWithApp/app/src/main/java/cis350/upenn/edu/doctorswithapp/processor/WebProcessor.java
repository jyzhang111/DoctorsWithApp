package cis350.upenn.edu.doctorswithapp.processor;

import java.net.URL;

import cis350.upenn.edu.doctorswithapp.data.DoctorReader;
import cis350.upenn.edu.doctorswithapp.data.DoctorWebTask;
import cis350.upenn.edu.doctorswithapp.data.MedicationReader;
import cis350.upenn.edu.doctorswithapp.data.MedicationWebTask;
import cis350.upenn.edu.doctorswithapp.data.PatientReader;
import cis350.upenn.edu.doctorswithapp.data.PatientWebTask;

public class WebProcessor extends Processor {
    public WebProcessor(){
        super();
    }

    protected DoctorReader createDoctorReader(){ return new DoctorWebTask(); }

    protected PatientReader createPatientReader(){
        return new PatientWebTask();
    }

    protected MedicationReader createMedicationReader() {
        return new MedicationWebTask();
    }
}
