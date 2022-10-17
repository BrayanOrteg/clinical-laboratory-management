package model;

public interface IHashTable<T> {

    public void chainedHashInsert(PatientNode node);

    public PatientNode chainedHashSearch(long key);

    public PatientNode chainedHashDelete(long key);

    public long hashFunction(long key);

}
