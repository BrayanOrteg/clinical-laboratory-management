package test;
import junit.framework.TestCase;
import model.AgravationEnum;
import model.CauseOfAdmissionEnum;
import model.Heap;
import model.Patient;

import java.util.Calendar;

public class HeapTest extends TestCase {

    private Heap heapPatients;
    private Calendar date= Calendar.getInstance();

    public void setupStage1(){
        heapPatients=new Heap(10);


    }



    public void setupStage2(){
        heapPatients= new Heap(4);
        date.set(1999, 5, 20);
        Patient patient1= new Patient("Paolo", date, 2, CauseOfAdmissionEnum.GENERAL_MALAISE, AgravationEnum.NONE, 11000204);
        date.set(2001, 3, 21);
        Patient patient2= new Patient("Juana", date, 1, CauseOfAdmissionEnum.GUN, AgravationEnum.PREGNANT, 112453023);
        heapPatients.HeapInsert(patient1);
    }

    public void testHeapSearchObject_InsertPatient (){
        setupStage1();
        date.set(2002, 7, 23);
        Patient patientTest= new Patient("Roberta", date, 2, CauseOfAdmissionEnum.GUN, AgravationEnum.PREGNANT, 112453023);
        date.set(2004, 7, 23);
        Patient patientTest2= new Patient("Constanza", date, 1, CauseOfAdmissionEnum.GUN, AgravationEnum.NONE, 202453025);


        heapPatients.HeapInsert(patientTest);
        heapPatients.HeapInsert(patientTest2);

        try {
            assertEquals(heapPatients.SearchObject(2,112453023, 0),0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public  void testHeapExtracMax (){

        setupStage2();

        try {
            heapPatients.HeapExtractMax();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals(heapPatients.getHeapSize(),3);
    
    }

    
    public  void testHeapIsEmpty (){

        setupStage1();

        assertTrue(heapPatients.IsEmpty());
    
    }

    public  void testHeapIsNotEmpty (){

        setupStage2();

        assertFalse(heapPatients.IsEmpty());

    }



    
}
