package model;

public class PatientNode {
    private int priority;
    private Patient patient;

    private Patient nextPatient;
    private Patient previousPatient;

    public PatientNode(int priority, Patient patient) {
        this.priority = priority;
        this.patient = patient;
        nextPatient=null;
        previousPatient=null;
    }
}
