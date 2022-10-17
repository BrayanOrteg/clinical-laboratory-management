package model;

public interface IPriorityQueue {

    public boolean Insert(PatientNode toAdd) throws Exception;

    public PatientNode ExtractMax() throws Exception;

    public PatientNode Maximun() throws Exception;

    public boolean IncreaseKey(int oldPriority, long id, int newPriority) throws Exception; //Falta Test

}
