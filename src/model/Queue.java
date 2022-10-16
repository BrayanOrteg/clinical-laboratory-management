package model;
import java.util.ArrayList;

public class Queue <T> implements  IQueue <T> {

    private ArrayList <T> array= new ArrayList<>();

    public Queue(){}

    public void add(T element){
        array.add(element);
    }

    public T peak() throws  Exception{
        if(array.isEmpty()==true){ throw new Exception("The Queue is empty");}
        return array.get(0);
    }

    public T pop() throws Exception{
    if(array.isEmpty()==true){ throw new Exception("The Queue is empty");}
        return array.remove(0);
    }

    public boolean isEmpty() {return array.isEmpty();}
}
