package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

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


    //Priority Increase GENERAL - HEMATOLOGY

    public void IncreseGeneral(){
        for(int i=0; i<priorityGeneral.getHeapSize(); i++){
            PatientNode<Patient> p = priorityGeneral.getByIndex(i);

            try{
                priorityGeneral.IncreaseKey(p.getPriority(), p.getPatient().getId(), p.getPriority()+1);
            }catch (Exception e){}
        }
    }

    public void IncreseHematology(){
        for(int i=0; i<priorityHematology.getHeapSize(); i++){
            PatientNode<Patient> p = priorityHematology.getByIndex(i);

            try{
                priorityHematology.IncreaseKey(p.getPriority(), p.getPatient().getId(), p.getPriority()+1);
            }catch (Exception e){}
        }
    }

    public void DecreaseGeneral(){
        for(int i=0; i<priorityGeneral.getHeapSize(); i++){
            PatientNode<Patient> p = priorityGeneral.getByIndex(i);

            try{
                priorityGeneral.IncreaseKey(p.getPriority(), p.getPatient().getId(), p.getPriority()-1);
            }catch (Exception e){}
        }
    }

    public void DecreaseHematology(){
        for(int i=0; i<priorityHematology.getHeapSize(); i++){
            PatientNode<Patient> p = priorityHematology.getByIndex(i);

            try{
                priorityHematology.IncreaseKey(p.getPriority(), p.getPatient().getId(), p.getPriority()-1);
            }catch (Exception e){}
        }
    }


    //TimedOut

    public String timedOutGeneral() {

        if(!priorityGeneral.IsEmpty()){
            try {
                PatientNode pn1 = priorityGeneral.ExtractMax();
                checkOut.add(pn1);
                ((Patient)pn1.getPatient()).setStatusPatient(StatusPatientEnum.TO_CHECKOUT_MACHINE);
                addToStack(new PatientStackNode(pn1,2));

                return "Name: " + ((Patient) pn1.getPatient()).getName() + " id: " + ((Patient) pn1.getPatient()).getId();
            } catch (Exception e) {}
        }
        return"";
    }

    public String timedOutHematology() {

        if(!priorityHematology.IsEmpty()){
            try {
                PatientNode pn2 = priorityHematology.ExtractMax();
                checkOut.add(pn2);
                ((Patient)pn2.getPatient()).setStatusPatient(StatusPatientEnum.TO_CHECKOUT_MACHINE);
                addToStack(new PatientStackNode(pn2,1));

                return "Name: " + ((Patient) pn2.getPatient()).getName() + " id: " + ((Patient) pn2.getPatient()).getId();
            } catch (Exception e) {}
        }
        return"";
    }

    //Json methods

    public void WriteJson(){

        Gson gson = new Gson();

        for(Patient p:allPatients){
            p.setStatusPatient(StatusPatientEnum.OUT_OF_HOSPITAL);
        }

        String json = gson.toJson(allPatients);

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

            if(patienstFromJson!=null)sent.addAll(List.of(patienstFromJson));

            return sent;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Patient methods

    public String checkOutPatient(long key){

        String out="Patient is not available for check out";
        PatientNode pn= hash.chainedHashSearch(key);

        if(pn !=null && (((Patient)pn.getPatient()).getStatusPatient().equals(StatusPatientEnum.TO_CHECKOUT)||((Patient)pn.getPatient()).getStatusPatient().equals(StatusPatientEnum.TO_CHECKOUT_MACHINE))){
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
    public int patientStatusCheck(long key){

        if (hash.chainedHashSearch(key)==null){
            return 1;
        }else if(((Patient)hash.chainedHashSearch(key).getPatient()).getStatusPatient()!=StatusPatientEnum.OUT_OF_HOSPITAL){
            return 2;
        }
        return 3;
    }

    public void checkInPrePatient(long id, int unit,int aggravation){

        PatientNode patientNode= hash.chainedHashSearch(id);

        int priority= 1;

        StatusPatientEnum statusPatient = StatusPatientEnum.PRIORITY_GENERAL;

        if(aggravation == 1 || aggravation==3){
            priority+=1;
        }
        else if (aggravation == 2 ){
            priority+=2;
        }

        patientNode.setPriority(priority);

        try {
            if(unit==1){

                ((Patient)patientNode.getPatient()).setStatusPatient(StatusPatientEnum.PRIORITY_HEMATOLOGY);
                priorityHematology.Insert(patientNode);

            }else{
                ((Patient)patientNode.getPatient()).setStatusPatient(StatusPatientEnum.PRIORITY_GENERAL);
                priorityGeneral.Insert(patientNode);
            }
        }catch (Exception e){}

        addToStack(new PatientStackNode(patientNode,unit));
    }


    public void checkInPatient(String name, Calendar date, String causeOfAdmission, int aggravation, long id , int unit){

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
                priorityHematology.Insert(patientNode);

            }else if(unit == 2){ priorityGeneral.Insert(patientNode); }


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
                out="This check in cannot be undone";
                patient = undoStack.pop();
                StatusPatientEnum status=((Patient)patient.getPatient().getPatient()).getStatusPatient();

                if(status.equals(StatusPatientEnum.PRIORITY_HEMATOLOGY)){

                    priorityHematology.DeleteExact(patient.getPatient().getPriority(),patient.getPatient().getKey(), 0);
                    ((Patient)patient.getPatient().getPatient()).setStatusPatient(StatusPatientEnum.OUT_OF_HOSPITAL);
                    out=patient.getPatient().getNamePatient() + "'s Check in was undone";

                } else if (status.equals(StatusPatientEnum.PRIORITY_GENERAL)) {

                    priorityGeneral.DeleteExact(patient.getPatient().getPriority(),patient.getPatient().getKey(), 0);
                    ((Patient)patient.getPatient().getPatient()).setStatusPatient(StatusPatientEnum.OUT_OF_HOSPITAL);
                    out=patient.getPatient().getNamePatient() + "'s Check in was undone";

                }else if (status.equals(StatusPatientEnum.OUT_OF_HOSPITAL)){
                    checkOut.add(patient.getPatient());
                    ((Patient)patient.getPatient().getPatient()).setStatusPatient(StatusPatientEnum.TO_CHECKOUT);
                    out= patient.getPatient().getNamePatient() + "'s check out was undone";
                } else if (status.equals(StatusPatientEnum.TO_CHECKOUT)) {
                    if(patient.getUnit()==1){
                        DecreaseHematology();
                        priorityHematology.Insert(patient.getPatient());
                        ((Patient)patient.getPatient().getPatient()).setStatusPatient(StatusPatientEnum.PRIORITY_HEMATOLOGY);
                        checkOut.remove(patient.getPatient());
                        out= patient.getPatient().getNamePatient() + " has returned to the waiting room";
                    }else if(patient.getUnit()==2){
                        DecreaseGeneral();
                        priorityGeneral.Insert(patient.getPatient());
                        ((Patient)patient.getPatient().getPatient()).setStatusPatient(StatusPatientEnum.PRIORITY_GENERAL);
                        checkOut.remove(patient.getPatient());
                        out= patient.getPatient().getNamePatient() + " has returned to the waiting room";
                    }
                }else{
                    out=patient.getPatient().getNamePatient() + "'automatic attention can not be undone";
                    if(patient.getUnit()==1){
                        DecreaseHematology();
                    } else if (patient.getUnit()==2) {
                        DecreaseGeneral();
                    }
                }


            }catch (Exception e) {
                System.out.println("automatic attention can not be undone");
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
                tempGeneral.Insert(priorityGeneral.getArray().get(i));
            }catch (Exception e){}
        }

        for(int i =0; i < priorityHematology.getHeapSize();i++){
            try {
                tempHematology.Insert(priorityHematology.getArray().get(i));
            }catch (Exception e){}
        }

        tempHematology.BuildHeap();
        tempGeneral.BuildHeap();


        int general= priorityGeneral.getHeapSize();
        int hematology= priorityHematology.getHeapSize();

        String out="PATIENTS IN THE CLINIC";

        out+="\n Patients in Hematology: (" + hematology +")";

        for(int i=0; i<hematology; i++){
            try {
                PatientNode aux=tempHematology.ExtractMax();
                out += "\n  ("+ (i+1)+") "+aux.getNamePatient() + " with id: " +((Patient)aux.getPatient()).getId() ;
            }catch (Exception e){ }
        }

        out+="\n Patients in General attention: (" + general +")" ;

        for(int i=0; i<general; i++){
            try {
                PatientNode aux=tempHematology.ExtractMax();
                out += "\n  (" + (i + 1) + ")" +aux.getNamePatient() + " with id: " +((Patient)aux.getPatient()).getId() ;
            }catch (Exception e){ }
        }

        out+="\n Patients available for check out: (" + checkOut.size() +")";

        for(PatientNode p: checkOut){

            out+="\n  "+ ((Patient)p.getPatient()).getName() + " with id: " + ((Patient)p.getPatient()).getId();
        }

        tempGeneral= priorityGeneral;
        tempHematology= priorityHematology;

        return out;
    }

    public void manualAttention(int unit){

        PatientNode patient =null;

        try {
            if (unit == 1) {

                patient=priorityHematology.ExtractMax();
                ((Patient) patient.getPatient()).setStatusPatient(StatusPatientEnum.TO_CHECKOUT);
                checkOut.add(patient);
                addToStack(new PatientStackNode(patient,unit));
                System.out.println("The HEMATOLOGY row has move, the patient with " + ((Patient) patient.getPatient()).getName()+ " and id: "+((Patient) patient.getPatient()).getId() + " has been attended" +
                        "\nDon't forget to do the manual checkout");

            } else {
                patient=priorityGeneral.ExtractMax();
                ((Patient) patient.getPatient()).setStatusPatient(StatusPatientEnum.TO_CHECKOUT);
                checkOut.add(patient);
                addToStack(new PatientStackNode(patient,unit));
                System.out.println("The GENERAL row has move, the patient with " + ((Patient) patient.getPatient()).getName()+ " and id: "+((Patient) patient.getPatient()).getId() + " has been attended" +
                        "\nDon't forget to do the manual checkout");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}


