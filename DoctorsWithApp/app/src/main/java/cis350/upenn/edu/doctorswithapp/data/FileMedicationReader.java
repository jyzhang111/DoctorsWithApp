package cis350.upenn.edu.doctorswithapp.data;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.TreeMap;

import cis350.upenn.edu.doctorswithapp.MainActivity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;

public class FileMedicationReader implements MedicationReader{
    public FileMedicationReader() {
        File path = MainActivity.context.getFilesDir();
        File file = new File(path, "medications.txt");

        try {
            file.createNewFile();
            PrintWriter out = new PrintWriter(new FileOutputStream(file, false));
            out.print("Ok\tTest Pill\t5\t[1,9:00]\t3\t[Headache]\t[Good for testing!]\tfalse\tTest Pill 2\t5\t[1,9:00]\t3\t[Headache]\t[Good for testing!]\ttrue\tdone");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("blah", "couldn't create file in FileMedicationReader.");
        }
    }

    public Map<String, TreeSet<MedicationInfo>> getMedications() {
        //I change medications type to the following way, one paitent should correspond to many medcationInfo which I store in
        //a TreeMap sorted by time. The most recent one supposes to be the current medication
        Map<String, TreeSet<MedicationInfo>> medications = new TreeMap<String, TreeSet<MedicationInfo>>();
        File path = MainActivity.context.getFilesDir();
        File file = new File(path, "medications.txt");

        Scanner in = null;
        try {
            in = new Scanner(file);
            in.useDelimiter("\t|\n");
            while (in.hasNext()) {
                TreeSet<MedicationInfo> patientMedicationInfo = new TreeSet<MedicationInfo>();

                String patientName = in.next();
                String medicineName = in.next();
                String strDosage = in.next();
                int dosage = Integer.parseInt(strDosage);

                Map<String, String> schedule = new HashMap<String, String>();
                String scheduleStr = in.next();
                scheduleStr = scheduleStr.substring(1, scheduleStr.length()-1);
                String []scheduleArray = scheduleStr.split(";");
                for(String s : scheduleArray) {
                    schedule.put(s.split(",")[0], s.split(",")[1]);
                }

                String strNumPerDay = in.next();

                int numPerDay = Integer.parseInt(strNumPerDay);

                List<String> sideEffects = new ArrayList<String>();
                String sideEff = in.next();
                sideEff = sideEff.substring(1, sideEff.length()-1);
                String []sideEffArray = sideEff.split(",");
                for(String s : sideEffArray) {
                    sideEffects.add(s);
                }

                List<String> usages = new ArrayList<String>();
                String strNumUsage = in.next();
                strNumUsage = strNumUsage.substring(1, strNumUsage.length()-1);
                String []usageArray = strNumUsage.split(",");
                for(String s : usageArray) {
                    usages.add(s);
                }

                String CurrentPill = in.next();
                boolean isCurrentPill = Boolean.parseBoolean(CurrentPill);
                // key = medicationTime, value = medicationInfo
                patientMedicationInfo.add(new MedicationInfo(medicineName, dosage, numPerDay, schedule, sideEffects, usages, isCurrentPill));

                String endPoint = "done";
                String next = null;
                //if next is not endPoint, keep reading medications

                //if in.hasNext, then I'm pretty sure in.next is not null
                while( (in.hasNext()) ){
                    medicineName = in.next();

                    if(medicineName.equals(endPoint)){
                        break;
                    }

                    strDosage = in.next();
                    dosage = Integer.parseInt(strDosage);

                    schedule = new HashMap<String, String>();
                    scheduleStr = in.next();
                    scheduleStr = scheduleStr.substring(1, scheduleStr.length()-1);
                    scheduleArray = scheduleStr.split(";");
                    for(String s : scheduleArray) {
                        schedule.put(s.split(",")[0], s.split(",")[1]);
                    }

                    strNumPerDay = in.next();

                    numPerDay = Integer.parseInt(strNumPerDay);

                    sideEffects = new ArrayList<String>();
                    sideEff = in.next();

                    sideEff = sideEff.substring(1, sideEff.length()-1);
                    sideEffArray = sideEff.split(",");
                    for(String s : sideEffArray) {
                        sideEffects.add(s);
                    }
                    strNumUsage = in.next();
                    usages = new ArrayList<String>();

                    strNumUsage = strNumUsage.substring(1, strNumUsage.length()-1);
                    usageArray = strNumUsage.split(",");
                    for(String s : usageArray) {
                        usages.add(s);
                    }
                    CurrentPill = in.next();
                    isCurrentPill = Boolean.parseBoolean(CurrentPill);
                    patientMedicationInfo.add(new MedicationInfo(medicineName, dosage, numPerDay, schedule, sideEffects, usages, isCurrentPill));
                }

                // adding patientMedication into medications
                medications.put(patientName, patientMedicationInfo);
            }
        } catch (Exception e) {
            Log.v("here", e.getMessage());
        } finally {
            in.close();
        }
        return medications;
    }

    public void put(String patientName, MedicationInfo med) {
        Map<String, TreeSet<MedicationInfo>>  medications = getMedications();
        medications.get(patientName);

        medications.get(patientName).add(med);

        File path = MainActivity.context.getFilesDir();
        File file = new File("medications.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.v("filestuff", "could not create file in put");
            }
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(file, false));
            // this for loop is to write all patients' mediInfo to file
            for(Map.Entry<String, TreeSet<MedicationInfo>> eachPatient : medications.entrySet()){
                writer.println(patientName + ": ");
                //looping each medication for one patient
                for(MedicationInfo entry: eachPatient.getValue()){

                    Map<String, String> scheduleMap = entry.getSchedule();
                    String docStringOne = "[";
                    int counterOne = 0;
                    for (String schedule : scheduleMap.keySet()) {
                        if (counterOne != scheduleMap.keySet().size() - 1) {
                            docStringOne = docStringOne + schedule + "," + scheduleMap.get(schedule) + ";";
                        } else {
                            docStringOne = docStringOne + schedule + "," + scheduleMap.get(schedule);
                        }
                        counterOne++;
                    }
                    docStringOne = docStringOne + "]";

                    List<String> sideEffectsList = entry.getSideEffectList();

                    String docStringTwo = "[";
                    int counterTwo = 0;
                    for (String sideEffect : sideEffectsList) {
                        if (counterTwo != sideEffectsList.size() - 1) {
                            docStringTwo = docStringTwo + sideEffect + ", ";
                        } else {
                            docStringTwo = docStringTwo + sideEffect;
                        }
                        counterTwo++;
                    }
                    docStringTwo = docStringTwo + "]";


                    List<String> usagesList = entry.getUsages();

                    String docStringThree = "[";
                    int counterThree = 0;
                    for (String usage : usagesList) {
                        if (counterThree != sideEffectsList.size() - 1) {
                            docStringThree = docStringThree + usage + ", ";
                        } else {
                            docStringThree = docStringThree + usage;
                        }
                        counterThree++;
                    }
                    docStringThree = docStringThree + "]";


                    writer.println(patientName + "\t" + entry.getName() + "\t" + entry.getDosage() + "\t"
                            + docStringOne + "\t" + entry.getNumPerDay() + "\t"
                            + docStringTwo + "\t" + docStringThree + "\t" + entry.getPillStatus());
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}