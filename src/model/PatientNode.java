package model;

public class PatientNode {
    private int priority;
    private Patient patient;
    private PatientNode nextPatient;
    private PatientNode previousPatient;

    public PatientNode(int priority, Patient patient) {
        this.priority = priority;
        this.patient = patient;
        nextPatient=null;
        previousPatient=null;
    }

    public void setNextPatient(PatientNode nextPatient) {
        this.nextPatient = nextPatient;
    }

    public void setPreviousPatient(PatientNode previousPatient) {
        this.previousPatient = previousPatient;
    }

    public PatientNode getNextPatient() {
        return nextPatient;
    }

    public PatientNode getPreviousPatient() {
        return previousPatient;
    }
}
