package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Controller {

    Heap priorityGeneral = new Heap();
    Heap priorityHematology = new Heap();

    ArrayList <PatientNode> checkOut= new ArrayList<>();

    ArrayList <Patient> allPatients;

    Stack <PatientStackNode> undoStack= new Stack<>();
    HashTable <PatientNode> hash = new HashTable<>();

    public Controller(){
        allPatients = ReadJson();

        if(allPatients!=null){
            for(Patient p:allPatients){
                try{
                    hash.chainedHashInsert(new PatientNode(p.getPriority(), p, p.getId() ));
                }catch (Exception e){

                }
            }
        }else{
            allPatients = new ArrayList<>();
        }
    }

    //TimedOut

    public String timedOutGeneral() {

        if(!priorityGeneral.IsEmpty()){
            try {
                PatientNode pn1 = priorityGeneral.HeapExtractMax();
                checkOut.add(pn1);
                ((Patient)pn1.getPatient()).setStatusPatient(StatusPatientEnum.TO_CHECKOUT);

                return "Name: " + ((Patient) pn1.getPatient()).getName() + " id: " + ((Patient) pn1.getPatient()).getId();
            } catch (Exception e) {}
        }
        return"";
    }

    public String timedOutHematology() {

        if(!priorityHematology.IsEmpty()){
            try {
                PatientNode pn2 = priorityHematology.HeapExtractMax();
                checkOut.add(pn2);
                ((Patient)pn2.getPatient()).setStatusPatient(StatusPatientEnum.TO_CHECKOUT);

                return "Name: " + ((Patient) pn2.getPatient()).getName() + " id: " + ((Patient) pn2.getPatient()).getId();
            } catch (Exception e) {}
        }
        return"";
    }

    //Json methods

    public void WriteJson(){

        Gson gson = new Gson();

        String json = gson.toJson(allPatients);

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
            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line;
            if((line=reader.readLine())!=null){
                json= line;
            }
            fis.close();

            Gson gson = new Gson();
            Patient[] patienstFromJson = gson.fromJson(json, Patient[].class);
            ArrayList<Patient> sent = new ArrayList<>();

            sent.addAll(sent);

            return sent;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Patient methods

    public String checkOutPatient(int key){

        String out="Patient is not available for check out";
        PatientNode pn= hash.chainedHashSearch(key);

        if(pn !=null && ((Patient)pn.getPatient()).getStatusPatient().equals(StatusPatientEnum.TO_CHECKOUT)){
            ((Patient)pn.getPatient()).setStatusPatient(StatusPatientEnum.OUT_OF_HOSPITAL);
            out= "Check out was successful";
            checkOut.remove(pn);
            addToStack(new PatientStackNode(pn, 0));
        }
        return out ;
    }


    //1 si el paciente no está registrado
    //2 si está registrado pero ya entró a la clinica
    //3 si está registrado y no ha entrado a la clinica
    public int patientStatusCheck(int key){

        if (hash.chainedHashSearch(key)==null){
            return 1;
        }else if(((Patient)hash.chainedHashSearch(key).getPatient()).getStatusPatient()!=StatusPatientEnum.OUT_OF_HOSPITAL){
            return 2;
        }
        return 3;
    }

    public void checkInPrePatient(int id, int unit){

        PatientNode patientNode= hash.chainedHashSearch(id);

        try {
            if(unit==1){
                priorityHematology.HeapInsert(patientNode);
            }else{
                priorityGeneral.HeapInsert(patientNode);
            }
        }catch (Exception e){}

        addToStack(new PatientStackNode(patientNode,unit));
    }


    public void checkInPatient(String name, Calendar date, String causeOfAdmission, int aggravation, int id , int unit){

        AggravationEnum aggravationPatient = AggravationEnum.NONE;

        StatusPatientEnum statusPatient = StatusPatientEnum.PRIORITY_GENERAL;

        if(unit==1){
            statusPatient = StatusPatientEnum.PRIORITY_HEMATOLOGY;
        }else{
            statusPatient = StatusPatientEnum.PRIORITY_GENERAL;
        }

        int priority= 1;

        //Array of states


        //Array of aggravations
        AggravationEnum [] aggravations = {AggravationEnum.OLD_AGE, AggravationEnum.PREGNANT, AggravationEnum.CHILD, AggravationEnum.NONE};
        aggravationPatient = aggravations[aggravation-1];


        if(aggravation == 1 || aggravation==3){
            priority+=1;
        }
        else if (aggravation == 2 ){
            priority+=2;
        }

        Patient patient= new Patient(name, date, causeOfAdmission, priority, aggravationPatient, statusPatient, id);


        PatientNode <Patient> patientNode= new PatientNode<>(priority, patient, id);

        hash.chainedHashInsert(patientNode);
        allPatients.add(patient);


        try {

            if (unit==1){
                priorityHematology.HeapInsert(patientNode);

            }else if(unit == 2){ priorityGeneral.HeapInsert(patientNode); }


            hash.chainedHashInsert(patientNode);
            addToStack(new PatientStackNode(patientNode,unit));


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

                if(patient.getUnit()==1){

                    priorityHematology.DeleteExact(patient.getPatient().getPriority(),patient.getPatient().getKey(), 0);
                    ((Patient)patient.getPatient().getPatient()).setStatusPatient(StatusPatientEnum.OUT_OF_HOSPITAL);
                    out=patient.getPatient().getNamePatient() + "'s Check in was undone";

                } else if (patient.getUnit()==2) {

                    priorityGeneral.DeleteExact(patient.getPatient().getPriority(),patient.getPatient().getKey(), 0);
                    ((Patient)patient.getPatient().getPatient()).setStatusPatient(StatusPatientEnum.OUT_OF_HOSPITAL);
                    out=patient.getPatient().getNamePatient() + "'s Check in was undone";

                }else{
                    checkOut.add(patient.getPatient());
                    ((Patient)patient.getPatient().getPatient()).setStatusPatient(StatusPatientEnum.TO_CHECKOUT);
                    out= patient.getPatient().getNamePatient() + "'s check out was undone";
                }


            }catch (Exception e) {
                System.out.println("This check in cannot be undone");
            }
        }
        return out;
    }

    public void addToStack(PatientStackNode patient){
        try {
            undoStack.push(patient);
        }catch (Exception e){ }
    }

    public String printPatients(){

        Heap tempGeneral= new Heap();
        Heap tempHematology= new Heap();

        for(int i =0; i < priorityGeneral.getHeapSize();i++){
            try {
                tempGeneral.HeapInsert(priorityGeneral.getArray().get(i));
            }catch (Exception e){}
        }

        for(int i =0; i < priorityHematology.getHeapSize();i++){
            try {
                tempHematology.HeapInsert(priorityHematology.getArray().get(i));
            }catch (Exception e){}
        }

        tempHematology.BuildHeap();
        tempGeneral.BuildHeap();


        int general= priorityGeneral.getHeapSize();
        int hematology= priorityHematology.getHeapSize();

        String out="Patients in the clinic:";

        out+="\nPatients in Hematology: ";

        for(int i=0; i<hematology; i++){
            try {
                out += "\n("+ (i+1)+") "+tempHematology.HeapExtractMax().getNamePatient();
            }catch (Exception e){ }
        }

        out+="\nPatients in General attention: ";

        for(int i=0; i<general; i++){
            try {
                out += "\n(" + (i + 1) + ")" + tempGeneral.HeapExtractMax().getNamePatient();
            }catch (Exception e){ }
        }

        out+="\nPatients available for check out: ";

        for(PatientNode p: checkOut){

            out+="\n"+ ((Patient)p.getPatient()).getName();
        }

        tempGeneral= priorityGeneral;
        tempHematology= priorityHematology;

        return out;
    }

}


