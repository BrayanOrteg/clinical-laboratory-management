package ui;

import java.util.Scanner;

public class Main {

    public Scanner sc= new Scanner(System.in);

    public Main(){
    }

    public static void main(String [] args) {

        Main principal= new Main();
        int option=0;

        do{
            option= principal.showMenu();
            principal.executeOperation(option);

        }while (option!=0);
    }


    public int showMenu() {
        int option=0;

        System.out.println(
                """
                        Select an option
                        (1) patient check in
                        (2) patient check out
                        (0) Salir
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


                break;

            case 2:

                break;

            case 3:


                break;

            case 4:

                break;


            default:
                System.out.println("Error, option no valid");

        }
    }

}
