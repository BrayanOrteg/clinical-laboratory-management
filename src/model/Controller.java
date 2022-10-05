package model;

public class Controller {

    Patient [] Hashpatients;
    Patient [] Priotiypatients;

    public Controller() {
    }

    public Patient RegisterEntry(){

        return Priotiypatients[0];
    }

    public Patient RegisterEgress(){

        return  Hashpatients[0];

    }

}
