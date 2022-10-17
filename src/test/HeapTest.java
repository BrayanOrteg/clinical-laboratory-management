package test;
import junit.framework.TestCase;
import model.*;

import java.util.Calendar;



public class HeapTest extends TestCase {

    private Heap heapPatients;
    private Calendar date= Calendar.getInstance();


    public void setupStage1() {
        heapPatients = new Heap();

    }



    public void setupStage2(){
        //Creamos dos objetos de tipo Patient Node y los insertamos
        heapPatients= new Heap();

        date.set(1999, 5, 20);
        Patient patient1= new Patient("Paolo", date,"HOLA",2, AggravationEnum.OLD_AGE, StatusPatientEnum.PRIORITY_HEMATOLOGY,11000204);

        PatientNode patientNode1 = new PatientNode<Patient>(2, patient1, 11000204);

        date.set(2001, 3, 21);
        Patient patient2= new Patient("Juana", date, "HOLA",1, AggravationEnum.PREGNANT, StatusPatientEnum.TO_CHECKOUT,112453023);

        PatientNode patientNode2 = new PatientNode<Patient>(1, patient2, 112453023);

        try {
            heapPatients.HeapInsert(patientNode1);
            heapPatients.HeapInsert(patientNode2);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

    }

    //Test del método insert y del método search

    public void testHeapSearchObject_InsertPatient (){
        setupStage1();

        //Añadimos dos objetos patient node a un heap vacio, el segundo añadido es mayor que el primero por lo
        //la heap debería re organizarce poniendo al dato de prioridad 3 arriba del de prioridad dos, por lo que
        //al buscarlo debería estar en la posición cero del ArrayList

        date.set(2002, 7, 23);
        Patient patientTest1 = new Patient("Roberta", date, "HOLA",2, AggravationEnum.CHILD, StatusPatientEnum.TO_CHECKOUT,112453023);
        PatientNode patientTestNode1 = new PatientNode(2, patientTest1, 112453023);
        date.set(2004, 7, 23);
        Patient patientTest2= new Patient("Constanza", date, "HOLA",3, AggravationEnum.NONE, StatusPatientEnum.PRIORITY_HEMATOLOGY,202453025);
        PatientNode patientTestNode2 = new PatientNode(3, patientTest2, 202453025);

        try {
            heapPatients.HeapInsert(patientTestNode1);
            heapPatients.HeapInsert(patientTestNode2);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }


        try {
            assertEquals(heapPatients.SearchObject(3,202453025, 0),0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    //Insert Exception
    public void testInsertNullObject(){
        setupStage1();

        PatientNode pa= null;

        try {
            heapPatients.HeapInsert(pa);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

        assertTrue(heapPatients.IsEmpty());



    }

    //Insert value Exception
    public void testInsertNull_Value(){
        setupStage1();

        date.set(2004, 7, 23);
        Patient patientTest2= new Patient("Constanza", date, "HOLA",3, AggravationEnum.NONE, StatusPatientEnum.TO_CHECKOUT, 202453025);
        PatientNode patientTestNode = new PatientNode(0, patientTest2, 202453025);

        try {
            heapPatients.HeapInsert(patientTestNode);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

        assertTrue(heapPatients.IsEmpty());



    }

    //Excepción del método seacrh

    public void testHeapSearchObject_Exception(){
        setupStage1();
        try {
            assertEquals(heapPatients.SearchObject(3,202453025, 0),0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Test del método extract max

    public  void testHeapExtracMax (){
        //Llamos al stage 2 donde hay dos objetos, y eliminamos 1, por lo que el tamaño actual del heap es 1
        setupStage2();

        try {
            heapPatients.HeapExtractMax();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals(heapPatients.getHeapSize(),1);
    
    }

    //Excepcion del metodo extract

    public  void testHeapExtracMaxException (){
        //Llamos al stage 1 donde no hay y extraemos, por lo que debería soltar una excepcion
        setupStage1();

        try {
            heapPatients.HeapExtractMax();
            assertEquals(heapPatients.getHeapSize(),1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    //Test IsEmpty
    
    public  void testHeapIsEmpty (){

        setupStage1();

        assertTrue(heapPatients.IsEmpty());
    
    }

    //Test isNotEmpty

    public  void testHeapIsNotEmpty () {

        setupStage2();

        assertFalse(heapPatients.IsEmpty());

    }

    //Test Maximun

    public void testMaximun(){
        setupStage2();

        try{

            heapPatients.Maximun();
;            assertEquals(heapPatients.getHeapSize(), 2);

        }catch (Exception e){

            System.out.println(e.getMessage());
        }
    }

    //Maximun Exception

    public void testMaximunException(){
        setupStage1();

        Patient patientTest2= new Patient("Constanza", date, "HOLA",3, AggravationEnum.NONE, StatusPatientEnum.TO_CHECKOUT,202453025);
        PatientNode patientTestNode2 = new PatientNode(3, patientTest2, 202453025);



        try{

            assertEquals(heapPatients.Maximun(), patientTest2);

        }catch (Exception e){

            System.out.println(e.getMessage());

        }

    }

    //Test Increase Key exception

    public void testIncreaseKeyException(){
        setupStage1();

        try {
            assertFalse(heapPatients.IncreaseKey(1,1123,0));
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

    }

    public void testIncreaseKey(){
        setupStage2();

        try {
            assertTrue(heapPatients.IncreaseKey(1, 112453023, 4));
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

        try {
            assertEquals(heapPatients.SearchObject(2,11000204,0),1);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    
}
