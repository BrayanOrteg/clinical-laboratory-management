package model;

public class PatientNode <T>{
    private int priority, unit;
    private long key;
    private T patient;
    private PatientNode nextPatient;
    private PatientNode previousPatient;

    public PatientNode(int priority, T patient, long key) {
        this.priority = priority;
        this.patient = patient;
        this.key=key;
        this.unit=unit;
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

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public T getPatient() { return patient; }

    public void setPatient(T patient) { this.patient = patient; }

    public long getKey() { return key; }

    public void setKey(long key) { this.key = key; }


    public String getNamePatient(){
       return ((Patient)patient).getName();
    }
}
