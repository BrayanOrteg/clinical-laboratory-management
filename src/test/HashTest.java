package test;
import junit.framework.TestCase;
import model.*;

import java.util.*;

public class HashTest extends TestCase {

    private Calendar date = Calendar.getInstance();
    private HashTable<Patient>table;
    private PatientNode<Patient>patientNode;
    private Patient patient;


    public void setupStage1(){
        table = new HashTable<>();
    }

    public void setupStage2(){

        table = new HashTable<>();
        date.set(1995, 5, 25);
        patient=new Patient("A", date, "HOLA",1, StateEnum.GRAVE, AggravationEnum.PREGNANT, 1);
        patientNode=new PatientNode<>(1,patient,12);
        table.chainedHashInsert(patientNode);
    }

    public void testSearch(){
        setupStage2();
        try {
            assertEquals(table.chainedHashSearch(12), patientNode);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /*
    public void testDelete(){
        setupStage2();
        table.chainedHashDelete(12);
        try {
            assertEquals(table.chainedHashSearch(12), null);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

     */
}