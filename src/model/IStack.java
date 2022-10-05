package model;

public interface IStack <T> {


    public boolean empty();

    public T peak();

    public T pop();

    public void push(T element);

}
