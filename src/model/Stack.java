package model;

import java.util.ArrayList;

public class Stack <T> implements  IStack <T>{



    private ArrayList <T> array= new ArrayList<>();

    public boolean empty(){

        if(array.isEmpty()){
            return true;
        }
        return false;
    }

    public T peak(){
        return array.get(-1);
    }

    public T pop(){
        return array.remove(-1);
    }

    public void push(T newElement){
        array.add(newElement);
    }
}
