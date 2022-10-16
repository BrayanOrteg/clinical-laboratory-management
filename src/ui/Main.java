package ui;

import java.util.*;

import model.Controller;


public class Main {

    public Scanner sc= new Scanner(System.in);
    public Controller control= new Controller();
    public Main(){
    }

    public static void main(String [] args) {

        Main main= new Main();

        int option=0;

        do{
            option= main.showMenu();
            main.executeOperation(option);

        }while (option!=0);
    }


    public int showMenu() {
        int option=0;

        System.out.println(
                """
                        Select an option
                        (1) Patient check in
                        (2) Patient check out
                        (3) Undo last action
                        (0) Exit
                """);

        option= sc.nextInt();
        sc.nextLine();
        return option;
    }


    public void executeOperation(int operation) {



        switch(operation) {
            case 0:
                System.out.println("Bye!");
                break;

            case 1:

                patientCheckIn();

                break;
                
            case 2:

                break;

            case 3:


                break;

            case 4:

                break;


            default:
                System.out.println("Invalid option");
        }
    }

    public void patientCheckIn(){

        String name;
        int id;
        String birth;
        String admissionReason;
        int state, aggravation;
        int [] arrayDate = new int[3];
        Calendar date= Calendar.getInstance();

        System.out.println("*PATIENT CHECK IN* \nType patient's name");
        name= sc.nextLine();

        System.out.println("\nType patient's id");
        id= sc.nextInt();

        System.out.println("\nType patient's date of birth in format (year/month/day)\nExample (1990/11/10)");

        arrayDate= Arrays.stream(sc.nextLine().split("/")).mapToInt(Integer::parseInt).toArray();
        date.set(arrayDate[0],arrayDate[1],arrayDate[2]);

        System.out.println("\nType the admission Reason");
        admissionReason= sc.nextLine();

        System.out.println(
                """
                        Type patient´s condition
        
                        (1) Mild
                        (2) Intermediate
                        (3) Serious
                """);
        state= sc.nextInt();

        System.out.println(
                """
                        Type patient´s aggravations
        
                        (1) Old age
                        (2) Pregnant
                        (3) Child
                        (4) None
                 
                """);
        aggravation= sc.nextInt();

        control.checkInPatient(name,date, admissionReason,state,aggravation,id);

    }

    public void patientCheckOut(){

       System.out.println("Insert patient's id");
       control.checkOutPatient(sc.nextInt());
    }

    public void undoConfirmation(){

        System.out.println(
                        """
                            Are you sure you want to undo the last action
                            
                            (1) Yes
                            (2) No
                        """);
        if(sc.nextInt()==1){
            control.undoAction();
            System.out.println();
        }
    }

}
