package model;

public interface HeapSorting {

    public void BuildHeap();

    public void Heapify(int parent);

    public boolean HeapInsert(PatientNode toAdd) throws Exception;

    public PatientNode HeapExtractMax() throws Exception;

    public PatientNode Maximun() throws Exception; //Falta Test

    public int SearchObject(int priority, int id, int position) throws Exception;

    public boolean IsEmpty();

    public boolean IncreaseKey(int oldPriority, int id, int newPriority) throws Exception; //Falta Test

}
