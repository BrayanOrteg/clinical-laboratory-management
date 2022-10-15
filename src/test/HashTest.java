package test;
import junit.framework.TestCase;
import model.*;

import java.util.*;

public class HashTest extends TestCase {

    private Calendar date = Calendar.getInstance();
    private HashTable<Patient>table;
    private PatientNode<Patient>patientNode;
    private PatientNode<Patient>patientNode2;
    private Patient patient;


    public void setupStage1(){
        table = new HashTable<>();
    }

    public void setupStage2(){

        table = new HashTable<>();
        date.set(1995, 5, 25);
        patient=new Patient("A", date, "HOLA",1, StateEnum.GRAVE, AggravationEnum.PREGNANT, 1);
        patientNode=new PatientNode<>(1,patient,1);
        table.chainedHashInsert(patientNode);
    }
    public void setupStage3(){

        setupStage2();

        patient=new Patient("B", date, "Bye",1, StateEnum.MILD, AggravationEnum.NONE, 1);
        patientNode2=new PatientNode<>(5,patient,2);
        table.chainedHashInsert(patientNode2);
    }
    public void setupStage4(){

        setupStage2();

        patient=new Patient("B", date, "Bye",1, StateEnum.MILD, AggravationEnum.NONE, 1);
        patientNode2=new PatientNode<>(5,patient,1);
        table.chainedHashInsert(patientNode2);
    }

    public void testSearch(){
        setupStage2();
        try {
            assertEquals(table.chainedHashSearch(1), patientNode);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void testSearch2(){
        setupStage2();
        try {
            assertNull(table.chainedHashSearch(2));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void testSearch3(){
        setupStage3();
        try {
            assertEquals(table.chainedHashSearch(2), patientNode2);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void testDelete(){
        setupStage2();
        try {
            assertEquals(table.chainedHashDelete(1), patientNode);
            table.chainedHashSearch(1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void testDelete2(){
        setupStage4();
        try {
            assertEquals(table.chainedHashDelete(1), patientNode);
            assertEquals(table.chainedHashSearch(1), patientNode2);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}