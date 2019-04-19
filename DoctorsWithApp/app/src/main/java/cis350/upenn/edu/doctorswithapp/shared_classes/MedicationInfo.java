package cis350.upenn.edu.doctorswithapp.shared_classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MedicationInfo implements Comparable<MedicationInfo>{
    private String name;
    private String patientName;
    private int dosage;
    private List<String> schedule;
    private int numPerDay;
    private List<String> sideEffects;
    private List<String> usages;
    private Boolean isCurrentPill;
    private String color;

    // Fake data
//    public MedicationInfo(){
//        this.name = "Test Pill";
//        this.dosage = 5;
//        List<String> schedule = new ArrayList<String>();
//        schedule.put("1", "9:00");
//        this.schedule = schedule;
//        this.numPerDay = 3;
//        List<String> sideEffects = new ArrayList<String>();
//        sideEffects.add("Headache");
//        this.sideEffects = sideEffects;
//        List<String> usages = new ArrayList<String>();
//        usages.add("Good for testing!");
//        this.usages = usages;
//        this.isCurrentPill = true;
//    }
    public MedicationInfo(String name, String patientName, int dosage, int numPerDay,
                          List<String> schedule, List<String> sideEffects, List<String> usages, Boolean isCurrentPill, String color) {
        this.name = name;
        this.patientName = patientName;
        this.dosage = dosage;
        this.numPerDay = numPerDay;
        this.schedule = schedule;
        this.sideEffects = sideEffects;
        this.usages = usages;
        this.isCurrentPill = isCurrentPill;
        this.color = color;
    }

    public String getName() {
        return name;
    }
    public void changeName(String newName) {
        name = newName;
    }
    public int getDosage() {
        return dosage;
    }
    public void changeDosage(int newDosage) {
        dosage = newDosage;
    }
    public void addScehdule(String time) {
        schedule.add(time);
        numPerDay++;
    }
    public String getPatientName() {
        return patientName;
    }
    public int getNumPerDay() {
        return numPerDay;
    }
    public List<String> getSchedule() {
        return schedule;
    }
    public void removeSchedule(String time) {
        schedule.remove(time);
    }
    public void addSideEffect(String sideEffect) {
        sideEffects.add(sideEffect);
    }
    public List<String> getSideEffectList() {
        return sideEffects;
    }
    public void removeSideEffect(String currSideEffect) {
        sideEffects.remove(currSideEffect);
    }
    public void addUsage(String newUsage) {
        usages.add(newUsage);
    }
    public void removeUSage(String currUsage) {
        usages.remove(currUsage);
    }
    public List<String> getUsages(){
        return usages;
    }
    public void setPillStatus() {
        if(isCurrentPill) {
            this.isCurrentPill = false;
        }
        this.isCurrentPill = true;
    }
    public boolean getPillStatus() {
        return isCurrentPill;
    }
    public String getColor() {
        return color;
    }

    @Override
    public int compareTo(MedicationInfo o) {
        return -1;
    }
}
