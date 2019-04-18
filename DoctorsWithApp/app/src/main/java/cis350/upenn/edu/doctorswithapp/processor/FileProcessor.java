package cis350.upenn.edu.doctorswithapp.processor;
import cis350.upenn.edu.doctorswithapp.data.DoctorWebTask;
import java.net.URL;


//import cis350.upenn.edu.doctorswithapp.data.FileDoctorReader;
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
        //return new FileDoctorReader();
            try {
                URL url = new URL("http://10.0.2.2:3000/apiDoctor");
                DoctorWebTask task = new DoctorWebTask();
                task.execute(url);
                //String name = task.get();
                return task;
            }
            catch (Exception e) {
                return null;
            }

    }

    protected PatientReader createPatientReader(){
        return new FilePatientReader();
    }

    protected MedicationReader createMedicationReader() {
        return new FileMedicationReader();
    }
}
