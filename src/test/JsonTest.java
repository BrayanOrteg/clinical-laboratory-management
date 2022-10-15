package test;
import junit.framework.TestCase;
import model.*;

import java.util.ArrayList;
import java.util.Calendar;



public class JsonTest extends TestCase {

    private Controller example = new Controller();
    private ArrayList<Patient> save = new ArrayList<>();
    private Calendar date= Calendar.getInstance();


    public void setupStage2(){
        //Creamos dos objetos de tipo Patient Node y los insertamos

        date.set(1999, 5, 20);
        Patient patient1= new Patient("A", date,"Fiebre",2, StateEnum.INTERMEDIATE, AggravationEnum.OLD_AGE, 11000204);


        date.set(2001, 3, 21);
        Patient patient2= new Patient("B", date, "Fractura",1, StateEnum.MILD, AggravationEnum.PREGNANT, 112453023);


        date.set(2004, 7, 31);
        Patient patient3 = new Patient("C",date,"Disparo",1, StateEnum.GRAVE, AggravationEnum.CHILD,1);


        date.set(2009, 11, 21);
        Patient patient4 = new Patient("C",date,"Enanismo",2, StateEnum.INTERMEDIATE, AggravationEnum.NONE,2);

        save.add(patient1);
        save.add(patient2);
        save.add(patient3);
        save.add(patient4);

    }

    public void testSaveData(){
        setupStage2();

        try {
            example.WriteJson(save);
            example.ReadJson();
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

    }

    

    
}