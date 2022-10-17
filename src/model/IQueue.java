package model;

public interface IQueue <T> {


    public void add(T element);

    public T peak() throws  Exception;

    public T pop() throws Exception;

    public boolean isEmpty();
}
