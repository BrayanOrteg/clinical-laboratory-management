package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;

public class Controller {

    Heap priorityPatients;
    ArrayList <PatientNode> patientsOfHospital = new ArrayList<>();
    Stack <PatientNode> undoStack;

    HashTable <PatientNode> hash;

    public Controller() {
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

    public void ReadJson() {
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Patient methods

    public String checkOutPatient(int key){

        String out="Error";

        if(hash.chainedHashDelete(key)!=null){
            out= "Check out was successful";
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
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String undoAction(){
        String out="There is not an action to undo";
        PatientNode patient;

        if(!undoStack.empty()){
            try {
                patient = undoStack.pop();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return out;
    }

}
