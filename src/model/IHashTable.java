package model;

public interface IHashTable<T> {

    public void chainedHashInsert(int k, PatientNode node);

    public void chainedHashSearch(int key);

    public void chainedHashDelete(int key);

    public int hashFunction(int key);

}
