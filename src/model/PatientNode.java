package model;

public class PatientNode {
    private int priority;
    private Patient patient;

    public PatientNode(int priority, Patient patient) {
        this.priority = priority;
        this.patient = patient;
    }
}
