package model;

import java.lang.*;

public class Heap implements HeapSorting{


    private Patient[] array;

    private int heapSize;

    public Heap(int heapSize){

        array = new Patient [heapSize];

        this.heapSize = heapSize;

    }

    public Heap(int heapSize, Patient[] array){

        this.array = array;

        this.heapSize = heapSize;

        BuildHeap();

    }

    @Override
    public void BuildHeap(){

        for (int i = heapSize / 2 - 1; i >= 0; i--){
            Heapify(i);
        }

    }
    @Override
    public void Heapify(int i){

        int largest = i;

        int l = largest*2+1;

        int r = largest*2+2;

        Patient swap;

        if (l < heapSize && array[l].compareTo(array[largest])>0)
            largest = l;

        // If right child is larger than largest so far
        if (r < heapSize && array[r].compareTo(array[largest])>0)
            largest = r;

        // If largest is not root
        if (largest != i) {
            swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            Heapify(largest);
        }



    }

    @Override
    public int HeapHasSpace() {
        int position = -1;

        for(int i=0; i<heapSize; i++){
            if(array[i]==null){
                position = i;
                return position;
            }
        }
        return position;
    }

    //Revisar
    @Override
    public boolean HeapInsert(T toAdd) {
        int position = HeapHasSpace();
        if(position==-1)return false;
        else{
            array[position] = toAdd;
            BuildHeap();
            return true;
        }

    }


    @Override
    public Patient HeapExtractMax() throws Exception{
        if(array[0]==null) throw new Exception("No hay pacientes en la fila");
        Patient swap;
        swap = array[0];
        array[0] = array[heapSize-1];
        array[heapSize-1] = swap;
        heapSize--;
        BuildHeap();
        return swap;
    }

    //@Override
    public boolean IsEmpty() {
        if(array[0]==null) return true;
        else return false;
    }



    @Override
    public int SearchObject(int key, int id, int position) throws Exception{
        if(array[position]==null) throw new Exception("El paciente no ha sido encontrado");

        if(array[position].getPriority()==key && array[position].getId()==id) return position;

        int l = position*2+1;

        int r = position*2+2;

        if(l < heapSize && array[l].getPriority() >= key) return SearchObject(key, id, l);

        if(r < heapSize && array[r].getPriority() >= key) return SearchObject(key, id, r);

        return heapSize;

    }




    @Override
    public boolean IncreaseKey(int oldKey, int id, int newKey) throws Exception{

        try {
            int position = SearchObject(oldKey, id, 0);
            if(position == heapSize) throw new Exception("El paciente no ha sido encontrado ");
            array[position].setPriority(newKey);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }



    public int getHeapSize(){
        return heapSize;
    }
}
