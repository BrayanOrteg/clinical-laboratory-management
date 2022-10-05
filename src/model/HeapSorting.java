package model;

public interface HeapSorting <T>{

    public T[] BuildHeap(T[] array);

    public T[] Heapify(int parent);

    public boolean HeapInsert(T toAdd);

    public T HeapExtractMax();

    public T IncreaseKey(int index, int key);
}
