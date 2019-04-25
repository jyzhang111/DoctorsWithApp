package cis350.upenn.edu.doctorswithapp.data;

import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import cis350.upenn.edu.doctorswithapp.shared_classes.MedicationInfo;

public class WebMedicationReader implements MedicationReader {
    public Map<String, TreeSet<MedicationInfo>> getMedications() {

        Map<String, TreeSet<MedicationInfo>> medicationMap = new HashMap<>();

        String largeMedicationString;

        try {
            URL url = new URL("http://10.0.2.2:3000/apiViewMedications");
            MedicationWebTask task = new MedicationWebTask();
            task.execute(url);
            largeMedicationString = task.get();
            Log.v("med string",largeMedicationString);
        }
        catch (Exception e) {
            Log.v("med string error", e.toString());
            return null;
        }

        if(largeMedicationString.equals("")) return medicationMap;

        Scanner in = null;
        try{
            in = new Scanner(largeMedicationString);
            in.useDelimiter("\t|\n");
            while(in.hasNext()){
                String name = in.next();
                Log.v("name", name);

                String patientName = in.next();
                Log.v("patientName", patientName);

                String sideEffect = in.next();
                Log.v("sideEffect", sideEffect);

                List<String> effect = new ArrayList<String>();

                String[] effectComponents = sideEffect.split(", ");
                for (int i = 0; i < effectComponents.length; i++) {
                    effect.add(effectComponents[i]);
                }
                String countStr = in.next();
                Log.v("countStr", countStr);

                int count = Integer.parseInt(countStr);

                String timeToTake = in.next();
                Log.v("timeToTake", timeToTake);

                List<String> schedule = new ArrayList<String>();
                String[] scheduleComponents = timeToTake.split(", ");
                for (int i = 0; i < scheduleComponents.length; i++) {
                    schedule.add(scheduleComponents[i]);
                }
                String timePerDayStr = in.next();
                Log.v("timePerDayStr", timePerDayStr);


                int timePerDay = Integer.parseInt(timePerDayStr);
                String reason = in.next();
                Log.v("reason", reason);

                List<String> usage = new ArrayList<String>();

                String[] usageComponents = reason.split(", ");
                for (int i = 0; i < usageComponents.length; i++) {
                    usage.add(usageComponents[i]);
                }
                String color = in.next();
                Log.v("color", color);


                String isPastPillStr = in.next();
                Log.v("isPastPillStr", isPastPillStr);

                Boolean isPastPill = Boolean.parseBoolean(isPastPillStr);

//                boolean isPastPill = false;
//                if (isPastPillStr.equals("true")) {
//                    isPastPill = true;
//                }
                MedicationInfo medication = new MedicationInfo(name, patientName, count, timePerDay, schedule, effect,
                        usage, isPastPill, color);

                if (!medicationMap.containsKey(patientName)) {
                    TreeSet<MedicationInfo> medSet = new TreeSet<MedicationInfo>();
                    medSet.add(medication);
                    medicationMap.put(patientName, medSet);
                } else {
                    TreeSet<MedicationInfo> originalSet = medicationMap.get(patientName);
                    originalSet.add(medication);
                }

            }
        }
        catch(Exception e){
            throw new IllegalStateException(e);
        }
        finally{
            in.close();
        }

        return medicationMap;

    }

    public void put(String name, MedicationInfo med){}
}
