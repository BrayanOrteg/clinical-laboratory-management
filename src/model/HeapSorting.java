package model;

public interface HeapSorting <T extends Comparable<T>>{

    public void BuildHeap();

    public void Heapify(int parent);

    public int HeapHasSpace();

    public boolean HeapInsert(T toAdd);

    public T HeapExtractMax() throws Exception;

    public int SearchObject(int key, int id, int position) throws Exception;

    public boolean IsEmpty();

    public boolean IncreaseKey(int oldKey, int id, int newKey) throws Exception;

}
