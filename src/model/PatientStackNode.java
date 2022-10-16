package model;

public class PatientStackNode {

    private PatientNode patient;

    private int action;

    // action = 1 the patient get in to the hospital
    // action = 2 the patient get out the hospital

    public PatientStackNode(PatientNode patient, int action) {
        this.patient = patient;
        this.action = action;
    }


    public int getAction() { return action; }

    public PatientNode getPatient() { return patient; }
}
