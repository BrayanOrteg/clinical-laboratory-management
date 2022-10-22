package test;
import junit.framework.TestCase;
import model.*;
import model.Stack;

import java.util.*;

public class StackTest extends TestCase {

    private Stack <Patient> stack;
    private Calendar date= Calendar.getInstance();

    public void setupStage1(){
        stack= new Stack();

    }
    public void setupStage2(){
        stack= new Stack();
        date.set(1995, 5, 25);

        try {
            stack.push(new Patient("A",date,"HOLA",1,AggravationEnum.CHILD,StatusPatientEnum.TO_CHECKOUT,1));

        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }
    public void setupStage3(){
        stack= new Stack();
        date.set(1995, 5, 25);

        try {
            stack.push(new Patient("A",date,"HOLA",1,  AggravationEnum.CHILD,StatusPatientEnum.TO_CHECKOUT,1));
            stack.push(new Patient("B",date,"HOLA",2, AggravationEnum.NONE,StatusPatientEnum.TO_CHECKOUT,2));

        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    public void testEmpty_True(){
        setupStage1();
        assertTrue(stack.empty());
    }

    //Test of push
    public void testPush_Empty_False (){
        setupStage1();
        try {
            stack.push(new Patient("A",date,"HOLA",1, AggravationEnum.OLD_AGE,StatusPatientEnum.TO_CHECKOUT,1));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertFalse(stack.empty());

    }

    public void  testPush_ChangeSize(){
        setupStage2();

        try{
            stack.push(new Patient("A",date,"HOLA",1, AggravationEnum.NONE,StatusPatientEnum.TO_CHECKOUT,1));

        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }
        assertEquals(stack.getSize(), 2);
    }

    public void  testPush_TheSameElement(){
        setupStage1();

        Patient patientTest1= new Patient("A",date,"HOLA",1,  AggravationEnum.NONE,StatusPatientEnum.TO_CHECKOUT,1);

        try{
            stack.push(patientTest1);
            stack.push(patientTest1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(stack.getSize(),2);
    }


    //Test Pop
    public void testPop_Empty_True (){
        setupStage2();
        try {
            stack.pop();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertTrue(stack.empty());
    }
    public void testPop_Empty_False (){
        setupStage3();
        try {
            stack.pop();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertFalse(stack.empty());
    }
    public void testPopError(){
        setupStage1();

        try {
            stack.pop();
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        assertTrue(stack.empty());

    }

    //Test peak
    public void testPeak_Push (){
        setupStage3();
        Patient patientTest= new Patient("A",date,"HOLA",1,  AggravationEnum.NONE,StatusPatientEnum.TO_CHECKOUT,1);
        try{
            stack.push(patientTest);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        try {
            assertEquals(stack.peak(),patientTest);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertFalse(stack.empty());
        assertEquals(stack.getSize(),3);

    }

    public void testPeak_Null(){
        setupStage1();

        try {
            assertEquals(stack.peak(),-1);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

        assertTrue(stack.empty());
        assertEquals(stack.getSize(), 0);

    }

    public void testPeak_AfterPop(){
        setupStage1();

        Patient patientTest1= new Patient("A",date,"HOLA",1,  AggravationEnum.NONE,StatusPatientEnum.TO_CHECKOUT,1);
        Patient patientTest2= new Patient("B",date,"HOLlA",2,  AggravationEnum.NONE,StatusPatientEnum.TO_CHECKOUT,3);
        try{
            stack.push(patientTest1);
            stack.push(patientTest2);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        try {
            stack.pop();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            assertEquals(stack.peak(),patientTest1);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

        assertEquals(stack.getSize(),1);
        assertFalse(stack.empty());


    }


}
