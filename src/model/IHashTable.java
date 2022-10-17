package model;

public interface IHashTable<T> {

    public void chainedHashInsert(PatientNode node);

    public PatientNode chainedHashSearch(int key);

    public PatientNode chainedHashDelete(int key);

    public int hashFunction(int key);

}
