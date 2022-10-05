package test;
import junit.framework.TestCase;
import model.*;
import model.Stack;

import java.util.*;

public class StackTest extends TestCase {

    private Stack<Patient> stack;
    private Calendar date= Calendar.getInstance();

    public void setupStage1(){
        stack= new Stack();

    }
    public void setupStage2(){
        stack= new Stack();
        date.set(1995, 5, 25);
        stack.push(new Patient("A",date,1, CauseOfAdmissionEnum.GUN, AgravationEnum.CHILDS,1));
    }
    public void setupStage3(){
        stack= new Stack();
        date.set(1995, 5, 25);
        stack.push(new Patient("A",date,1, CauseOfAdmissionEnum.GUN, AgravationEnum.CHILDS,1));
        stack.push(new Patient("B",date,2, CauseOfAdmissionEnum.HEART_DISEASES, AgravationEnum.PREGNANT,2));
    }

    public void testEmpty_True(){
        setupStage1();
        assertTrue(stack.empty());
    }
    public void testPush_Empty_False (){
        setupStage1();
        stack.push(new Patient("A",date,1, CauseOfAdmissionEnum.GUN, AgravationEnum.CHILDS,1));
        assertFalse(stack.empty());
    }
    public void testPop_Empty_True (){
        setupStage2();
        stack.pop();
        assertTrue(stack.empty());
    }
    public void testPop_Empty_False (){
        setupStage3();
        stack.pop();
        assertFalse(stack.empty());
    }
    public void testPeak_Push (){
        setupStage3();
        Patient patientTest= new Patient("A",date,1, CauseOfAdmissionEnum.GUN, AgravationEnum.CHILDS,1);
        stack.push(patientTest);
        assertEquals(stack.peak(),patientTest);
    }






    






    
}
