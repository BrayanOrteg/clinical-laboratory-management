package ui;

import java.util.*;
import java.util.concurrent.*;

import model.Controller;

public class Main {

    
    public Scanner sc= new Scanner(System.in);

    public Controller control = new Controller();

    public static Runnable taskGeneral, taskHematology;

    public static ScheduledExecutorService tasker = new ScheduledThreadPoolExecutor(1);

    public static ScheduledFuture executor;

    public Main(){}

    public String timedOutGeneral(){
        return control.timedOutGeneral();
    }
    public String timedOutHematology(){
        return control.timedOutHematology();
    }


    public static void main(String [] args) {

        Main principal= new Main();

        taskGeneral = new Runnable() {
            @Override
            public void run() {
                String out = principal.timedOutGeneral();
                if(!out.equals("")) {
                    System.out.println("The GENERAL row has move, the patient with " + out + " has been attended" +
                            "\nDon't forget to do the manual checkout");
                    principal.increaseGeneral();
                }
            }
        };

        taskHematology = new Runnable() {
            @Override
            public void run() {
                String out = principal.timedOutHematology();
                if(!out.equals("")) {
                    System.out.println("The HEMATOLOGIC row has move, the patient with " + out + " has been attended" +
                            "\nDon't forget to do the manual checkout");
                    principal.increaseHematology();
                }
            }
        };



        boolean paused = true;
        int option=0;

        do{
            option= principal.showMenu();

            if(!paused){
                System.out.println("Timer Reiniciado por atencion manual");
                executor.cancel(true);
                paused = true;
            }


            principal.executeOperation(option);

            if(paused){
                System.out.println("Timer Iniciado");
                executor=tasker.scheduleAtFixedRate(taskGeneral, 60,60, TimeUnit.SECONDS);
                executor=tasker.scheduleAtFixedRate(taskHematology, 60,60, TimeUnit.SECONDS);
                paused = false;
            }


        }while (option!=0);

            executor.cancel(true);
            System.exit(0);

    }

    public void increaseGeneral(){
        control.IncreseGeneral();
    }

    public void increaseHematology(){
        control.IncreseHematology();
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
                        (5) Let in patient
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
                try{
                    preCheckIn();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
                
            case 2:
                try {
                    patientCheckOut();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

                break;

            case 3:
                try {
                    undoConfirmation();
                }catch (Exception e){
                    System.out.println(e.getMessage());

                }

                break;

            case 4:
                System.out.println(control.printPatients());
                break;

            case 5:
                try {
                    nextTurn();
                }catch (Exception e){
                    System.out.println(e.getMessage());

                }

                break;

            default:
                System.out.println("Invalid option");
        }
    }

    public void preCheckIn() throws Exception{

        long id;
        int unit;

        System.out.println("Insert the id of the patient");

        //Try catch for the id
        try{
            id= sc.nextLong();
        }catch (InputMismatchException e) {
            sc.nextLine();
            throw new Exception("Please type an integer");
        }

        //Exception if the id is <=0
        if(id<=0) throw new Exception("Please type a positive number");

        int search =  searchPatient(id);

        if(search==2){
            System.out.println("this patient is already in the laboratory");

        }else{
            System.out.println("Which unit will the patient be admitted to?\n(1)Hematology\n(2)General purpose");


            //Try catch if the unit is not a number
            do {
                try {
                    unit = sc.nextInt();
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    throw new Exception("Please type an integer");
                }

                if((unit<1 || unit>2)) System.out.println("Invalid option, insert a number between 1 and 2");

            }while (unit<1 ||unit>2);
            sc.nextLine();


            if(search == 3){
                int aggravation;

                //Do while aggravation
                do{
                    System.out.println(
                            """
                                    Type patient´s aggravations
                    
                                    (1) Old age
                                    (2) Pregnant
                                    (3) Child
                                    (4) None
                             
                            """);

                    try {
                        aggravation= sc.nextInt();
                    }catch (InputMismatchException e) {
                        sc.nextLine();
                        throw new Exception("Please type an integer");
                    }


                    if(aggravation>4 || aggravation<1) System.out.println("Insert a valid option");

                }while (aggravation>4 || aggravation<1);



                sc.nextLine();
                System.out.println("\nType the admission Reason");
                String admissionReason= sc.nextLine();

                control.checkInPrePatient(id,unit,aggravation);
            }else{

                //Try catch the method patientCheckIn
                try {
                    patientCheckIn(id,unit);
                }catch (Exception e){throw e;}


            }
        }
    }

    public int searchPatient(long id){
        int result = control.patientStatusCheck(id);
        return result;
    }



    public void patientCheckIn(long id, int unit) throws Exception {

        String name;
        String birth;
        String admissionReason;
        int aggravation;
        int [] arrayDate = new int[3];
        Calendar date= Calendar.getInstance();


        System.out.println("*PATIENT CHECK IN* \nType patient's name");
        name= sc.nextLine();

        if(name=="") throw new Exception("Name format is invalid");

        System.out.println("\nType patient's date of birth in format (year/month/day)\nExample (1990/11/10)");
        birth=sc.nextLine();

        try {
            arrayDate= Arrays.stream(birth.split("/")).mapToInt(Integer::parseInt).toArray();
            date.set(arrayDate[0],arrayDate[1],arrayDate[2]);
        }catch (Exception e){
            throw new Exception("Invalid date format");
        }

        System.out.println("\nType the admission Reason");
        admissionReason= sc.nextLine();

        if(admissionReason=="") throw new Exception("Name format is invalid");

        do{
            System.out.println(
                    """
                            Type patient´s aggravations
            
                            (1) Old age
                            (2) Pregnant
                            (3) Child
                            (4) None
                     
                    """);
            try {
                aggravation= sc.nextInt();
            }catch (InputMismatchException e) {
                sc.nextLine();
                throw new Exception("Please type an integer");
            }

            if(aggravation>4 || aggravation<1) System.out.println("Insert a valid option");

        }while (aggravation>4 || aggravation<1);

        sc.nextLine();
        control.checkInPatient(name,date, admissionReason,aggravation,id , unit);
    }



    public void patientCheckOut() throws Exception{
       System.out.println("Insert patient's id");
        long id;
        try{
            id= sc.nextLong();
        }catch (InputMismatchException e) {
            sc.nextLine();
            throw new Exception("Please type an integer");
        }

        //Exception if the id is <=0
        if(id<=0) throw new Exception("Please type a positive number");

       System.out.println(control.checkOutPatient(id));
    }

    public void nextTurn() throws Exception{
        int unit;
        System.out.println("Type unit's number\n(1)Hematology\n(2)General purpose");

        do {
            try {
                unit = Integer.parseInt(sc.nextLine());
            } catch (InputMismatchException e) {
                sc.nextLine();
                throw new Exception("Please type an integer");
            }

            if((unit<1 || unit>2)) System.out.println("Invalid option, insert a number between 1 and 2");

        }while (unit<1 || unit>2);


        control.manualAttention(unit);
    }



    public void undoConfirmation()throws Exception{
        System.out.println(
                        """
                            Are you sure you want to undo the last action
                            
                            (1) Yes
                            (2) No
                        """);

        int option;
        try {
            option=sc.nextInt();
        }catch (InputMismatchException e){
            sc.nextLine();
            throw new Exception("Please type an integer");
        }

        if(option<=1)option=1;
        else option=2;

        if(option==1){
            System.out.println(control.undoAction());
        }
    }
}
