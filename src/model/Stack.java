package model;

import java.util.ArrayList;

public class Stack <T> implements  IStack <T>{

    public Stack(){}
    private ArrayList <T> array= new ArrayList<>();

    public boolean empty(){return array.isEmpty();}

    public T peak() throws Exception{
        if(array.isEmpty()==true){ throw new Exception("The stack is empty");}
        return array.get(array.size()-1);
    }

    public T pop()throws Exception{

        if(array.isEmpty()==true){ throw new Exception("The stack is empty");}
        return array.remove(array.size()-1);
    }

    public void push(T newElement) throws Exception{

        if(((Patient)newElement).getPriority()<0){ throw new Exception("The priority is negative");}

        array.add(newElement);
    }

    public int getSize(){ return array.size(); }
}
