package model;

public interface IStack <T> {


    public boolean empty();

    public T peak() throws  Exception;

    public T pop() throws Exception;

    public void push(T element)throws Exception;

}
