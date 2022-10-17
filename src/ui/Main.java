package ui;

import java.util.*;

import model.Controller;

public class Main {

    public Scanner sc= new Scanner(System.in);

    public Controller control = new Controller();

    public static Timer timerGeneral = new Timer();

    public static Timer timerHematology = new Timer();

    public static TimerTask taskGeneral;

    public static TimerTask taskHematology;

    public Main(){
    }

    public static void main(String [] args) {

        Main principal= new Main();

        taskGeneral = new TimerTask() {
            @Override
            public void run() {
                System.out.println("La task General");
                String out = principal.timedOutGeneral();
                if(!out.equals("")) {
                    System.out.println("La fila GENERAL ha avanzado, el paciente con " + out + " ha sido atendido" +
                            "\nNo olvide hacer el checkout manualmente");

                }
            }
        };

        taskHematology = new TimerTask() {
            @Override
            public void run() {
                System.out.println("La task Hematologica");
                String out = principal.timedOutHematology();
                if(!out.equals("")) {
                    System.out.println("La fila HEMATOLOGICA ha avanzado, el paciente con " + out + " ha sido atendido" +
                            "\nNo olvide hacer el checkout manualmente");

                }
            }
        };

        boolean firstTime = true;
        int option=0;

        do{
            option= principal.showMenu();

            principal.executeOperation(option);

            if(firstTime){
                System.out.println("Timer iniciados");
                timerGeneral.schedule(taskGeneral, 120000-(int)(Math.random()*60000),120000-(int)(Math.random()*60000));
                timerHematology.schedule(taskHematology, 120000-(int)(Math.random()*60000),120000-(int)(Math.random()*60000));
                firstTime=false;
            }

        }while (option!=0);
        timerGeneral.cancel();
        timerHematology.cancel();
    }

    public String timedOutGeneral(){
        return control.timedOutGeneral();
    }

    public String timedOutHematology(){
        return control.timedOutHematology();
    }


    public int showMenu() {
        int option=0;

        System.out.println(
                """
                        
                        Select an option
                        (1) Patient check in
                        (2) Patient check out
                        (3) Undo last action
                        (4) Print patients
                        (0) Exit
                """);

        option= sc.nextInt();
        sc.nextLine();
        return option;
    }


    public void executeOperation(int operation) {



        switch(operation) {
            case 0:
                control.WriteJson();
                System.out.println("Bye!");
                break;

            case 1:

                System.out.println("Insert the id of the patient");
                int id= sc.nextInt();

                int search =  searchPatient(id);

                if(search==2){
                    System.out.println("this patient is already in the laboratory");

                }else{
                    System.out.println("Which unit will the patient be admitted to?\n(1)Hematology\n(2)General purpose");
                    int unit = sc.nextInt();
                    sc.nextLine();
                    if(search == 3){

                        control.checkInPrePatient(id,unit);

                    }else{
                        patientCheckIn(id,unit);
                    }
                }

                break;
                
            case 2:
                patientCheckOut();
                break;

            case 3:
                undoConfirmation();
                break;

            case 4:
                System.out.println(control.printPatients());
                break;

            default:
                System.out.println("Invalid option");
        }
    }


    public int searchPatient(int id){

        int result = control.patientStatusCheck(id);

        return result;
    }

    public void patientCheckIn(int id, int unit){

        String name;
        String birth;
        String admissionReason;
        int aggravation;
        int [] arrayDate = new int[3];
        Calendar date= Calendar.getInstance();


        System.out.println("*PATIENT CHECK IN* \nType patient's name");
        name= sc.nextLine();

        System.out.println("\nType patient's date of birth in format (year/month/day)\nExample (1990/11/10)");
        birth=sc.nextLine();

        arrayDate= Arrays.stream(birth.split("/")).mapToInt(Integer::parseInt).toArray();
        date.set(arrayDate[0],arrayDate[1],arrayDate[2]);

        System.out.println("\nType the admission Reason");
        admissionReason= sc.nextLine();

        System.out.println(
                """
                        Type patient´s aggravations
        
                        (1) Old age
                        (2) Pregnant
                        (3) Child
                        (4) None
                 
                """);
        aggravation= sc.nextInt();

        control.checkInPatient(name,date, admissionReason,aggravation,id , unit);

    }



    public void patientCheckOut(){

       System.out.println("Insert patient's id");
       System.out.println(control.checkOutPatient(sc.nextInt()));
    }

    public void undoConfirmation(){

        System.out.println(
                        """
                            Are you sure you want to undo the last action
                            
                            (1) Yes
                            (2) No
                        """);
        if(sc.nextInt()==1){
            System.out.println(control.undoAction());
        }
    }

}
