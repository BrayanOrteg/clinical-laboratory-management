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
        patient=new Patient("A", date, "HOLA",1, AggravationEnum.PREGNANT, StatusPatientEnum.PRIORITY_GENERAL,1);
        patientNode=new PatientNode<>(1,patient,1);
        table.chainedHashInsert(patientNode);
    }
    public void setupStage3(){

        setupStage2();

        patient=new Patient("B", date, "Bye",1, AggravationEnum.NONE, StatusPatientEnum.TO_CHECKOUT,1);
        patientNode2=new PatientNode<>(5,patient,2);
        table.chainedHashInsert(patientNode2);
    }
    public void setupStage4(){

        setupStage2();

        patient=new Patient("B", date, "Bye",1,  AggravationEnum.NONE, StatusPatientEnum.TO_CHECKOUT, 1);
        patientNode2=new PatientNode<>(5,patient,1);
        table.chainedHashInsert(patientNode2);
    }

    //search
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

    // delete
    public void testDelete3(){
        setupStage2();

           assertNull(table.chainedHashDelete(2));
            table.chainedHashSearch(1);

    }

    public void testDelete2(){
        setupStage2();

        patient=new Patient("B", date, "Bye",1, AggravationEnum.NONE, StatusPatientEnum.TO_CHECKOUT,1);
        patientNode2=new PatientNode<>(5,patient,2);
        table.chainedHashInsert(patientNode2);

        assertEquals(table.chainedHashDelete(2),patientNode2);
        assertNull(table.chainedHashSearch(2));
    }

    public void testDelete1(){
        setupStage2();


        table.chainedHashDelete(1);
        assertNull(table.chainedHashSearch(1));
    }





}


