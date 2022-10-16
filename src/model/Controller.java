package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Controller {

    Heap priorityPatients = new Heap();
    ArrayList <PatientNode> patientsOfHospital = new ArrayList<>();
    Stack <PatientStackNode> undoStack= new Stack<>();
    HashTable <PatientNode> hash = new HashTable<>();

    public Controller(){
        ArrayList<Patient> patients = ReadJson();

        if(patients!=null){
            for(Patient p:patients){
                try{
                    priorityPatients.HeapInsert(new PatientNode<Patient>(p.getPriority(), p, p.getId()));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //TimedOut

    public String timedOut() {

        if(priorityPatients.IsEmpty() != true){
            try {
                PatientNode pn = priorityPatients.HeapExtractMax();
                InsertPatientsToCheckOut(pn);
                return "Name: " + ((Patient) pn.getPatient()).getName() + " id: " + ((Patient) pn.getPatient()).getId();
            } catch (Exception e) {}
        }

        return"";
    }

    //Json methods

    public void WriteJson(ArrayList<Patient> toSave){

        Gson gson = new Gson();

        String json = gson.toJson(toSave);

        System.out.print(json);

        try {
            FileOutputStream fos = new FileOutputStream(new File("dataBase\\patients.txt"));
            fos.write( json.getBytes(StandardCharsets.UTF_8) );
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Patient> ReadJson() {
        try {
            File file = new File("dataBase\\patients.txt");
            System.out.println("\n\nExiste: "+file.exists() + file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line;
            if((line=reader.readLine())!=null){
                json= line;
            }
            fis.close();
            System.out.println(json);

            Gson gson = new Gson();
            Patient[] patienstFromJson = gson.fromJson(json, Patient[].class);

            ArrayList<Patient> people = new ArrayList<>();
            for(Patient p : patienstFromJson){
                people.add(p);
                System.out.println(p.getPriority() + " " + p.getCauseOfAdmission());
            }

            return people;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Patient methods

    public String checkOutPatient(int key){

        String out="";
        PatientNode pn= hash.chainedHashDelete(key);

        if(pn !=null){
            out= "Check out was successful";
            addToStack(new PatientStackNode(pn, 2));
        }
        return out ;
    }

    public void checkInPatient(String name, Calendar date, String causeOfAdmission, int state, int aggravation, int id ){

        StateEnum statePatient = StateEnum.MILD;
        AggravationEnum aggravationPatient = AggravationEnum.NONE;
        StatusPatientEnum statusPatient = StatusPatientEnum.PRIORITY;
        int priority= state;

        //Array of states
        StateEnum [] states = {StateEnum.MILD, StateEnum.INTERMEDIATE, StateEnum.GRAVE};
        statePatient= states[state-1];


        //Array of aggravations
        AggravationEnum [] aggravations = {AggravationEnum.OLD_AGE, AggravationEnum.PREGNANT, AggravationEnum.CHILD, AggravationEnum.NONE};
        aggravationPatient = aggravations[aggravation-1];


        if(aggravation == 1 || aggravation==3){
            priority+=1;
        }
        else if (aggravation == 2 ){
            priority+=2;
        }

        Patient patient= new Patient(name, date, causeOfAdmission, priority, statePatient, aggravationPatient, statusPatient, id);

        PatientNode <Patient> patientNode= new PatientNode<>(priority, patient, id);


        try {
            priorityPatients.HeapInsert(patientNode);
            addToStack(new PatientStackNode(patientNode, 1));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String undoAction(){
        String out="There is not an action to undo";
        PatientStackNode patient;

        if(undoStack.empty() == false){
            try {
                patient = undoStack.pop();

                if(patient.getAction()==1){

                    priorityPatients.DeleteExact(patient.getPatient().getPriority(),patient.getPatient().getKey(), 0);
                    out=patient.getPatient().getNamePatient() + "'s Check in was undone";
                } else if (patient.getAction()==2){

                    hash.chainedHashInsert(patient.getPatient());
                    out= patient.getPatient().getNamePatient() + "'s check out was undone";
                }


            }catch (Exception e) {
                System.out.println("This check in cannot be undone");
            }
        }
        return out;
    }

    public void InsertPatientsToCheckOut(PatientNode patient){
        hash.chainedHashInsert(patient);
    }

    public void addToStack(PatientStackNode patient){
        try {
            undoStack.push(patient);
        }catch (Exception e){ }
    }

}


