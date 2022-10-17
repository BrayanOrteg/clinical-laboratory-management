package model;

public class PatientStackNode {

    private PatientNode patient;


    private int unit;

    // unit = 0 to check Out
    // unit = 1 to priority Hematology
    // unit = 2 to priority General

    public PatientStackNode(PatientNode patient, int unit) {
        this.patient = patient;
        this.unit = unit;
    }

    public PatientNode getPatient() { return patient; }

    public int getUnit() { return unit; }
}
