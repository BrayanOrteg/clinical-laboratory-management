package model;

import java.util.ArrayList;

public class Stack <T> implements  IStack <T>{

    public Stack(){}
    private ArrayList <T> array= new ArrayList<>();

    public boolean empty(){return array.isEmpty();}

    public T peak(){
        return array.get(array.size()-1);
    }

    public T pop(){
        return array.remove(array.size()-1);
    }

    public void push(T newElement){
        array.add(newElement);
    }
}
