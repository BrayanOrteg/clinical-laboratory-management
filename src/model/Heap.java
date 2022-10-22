package model;

import java.lang.*;

import java.util.*;

public class Heap implements IPriorityQueue{


    private ArrayList<PatientNode> array;

    public Heap(){

        array = new ArrayList<>();

    }

    public Heap(ArrayList<PatientNode> array){

        this.array = array;

        BuildHeap();

    }

    public void BuildHeap(){

        for (int i = array.size() / 2 - 1; i >= 0; i--){
            Heapify(i);
        }

    }

    public void Heapify(int i) {

        int largest = i;

        int l = largest * 2 + 1;

        int r = largest * 2 + 2;

        PatientNode swap;

        if (l < array.size() && (array.get(l).getPriority()) > (array.get(largest).getPriority())) {
            largest = l;
        }
        // If right child is larger than largest so far
        if (r < array.size() && (array.get(r).getPriority()) > (array.get(largest).getPriority())){
        largest = r;
        }
        // If largest is not root
        if (largest != i) {
            swap = array.get(i);
            array.set(i, array.get(largest));
            array.set(largest, swap);
            Heapify(largest);
        }

    }



    //Revisar
    @Override
    public boolean Insert(PatientNode toAdd)throws Exception {
        if(toAdd == null) throw new Exception("No se puede insertar un null");

        if(toAdd.getKey()==0 || toAdd.getPatient()==null || toAdd.getPriority()==0)throw new Exception("Can't insert a Node with id=0 o or priority=0");

        array.add(toAdd);
        BuildHeap();
        return true;

    }

    @Override
    public PatientNode Maximun() throws Exception {
        if(array.isEmpty()) throw new Exception("No patients in the queue");
        else return array.get(0);
    }

    @Override
    public PatientNode ExtractMax() throws Exception{

        if(array.isEmpty()) throw new Exception("There are no patients in de unit");
        PatientNode swap;
        swap = array.get(0);
        array.set(0, array.get(array.size()-1));
        array.set(array.size()-1, swap);
        array.remove(array.size()-1);
        BuildHeap();
        return swap;
    }


    public boolean IsEmpty() {
        return array.isEmpty();
    }



    public int SearchObject(int priority, long id, int position) throws Exception{

        if(array.isEmpty()) throw new Exception("The patient was not found");

        if(array.get(position)==null) throw new Exception("The patient was not found");

        int l = position*2+1;

        int r = position*2+2;

        if(l < array.size() && array.get(l).getPriority() >= priority) return SearchObject(priority, id, l);

        if(r < array.size() && array.get(r).getPriority() >= priority) return SearchObject(priority, id, r);

        return position;

    }

    public void DeleteExact(int priority, long id, int position){
        try{
            int i = SearchObject(priority, id, position);
            array.remove(i);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }




    @Override
    public boolean IncreaseKey(int oldPriority, long id, int newPriority) throws Exception{

        try {
            SearchObject(oldPriority,id,0);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        int position = SearchObject(oldPriority, id, 0);

        if(position == array.size()) throw new Exception("The patient was not found");

        array.get(position).setPriority(newPriority);
        BuildHeap();
        return true;

    }


    public int getHeapSize(){
        return array.size();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public ArrayList<PatientNode> getArray() {
        return array;
    }


    public PatientNode getByIndex(int id){
        return array.get(id);
    }
}
