package model;


public class HashTable<T> implements IHashTable<T> {

    private int size = 1001;
    private PatientNode [] array=new PatientNode [size];

    public HashTable() {

    }

    public void chainedHashInsert(int k, PatientNode node) {

        int code = hashFunction(k);
        if(array[code]==null){
            array[code]=node;
        }
        else{
            boolean ver=false;
            PatienNode pointer;

            while(ver==false){

                pointer=array[code];

                if(pointer.get)
            }




        }

    }


    public void chainedHashSearch(int key){

    }

    public void chainedHashDelete(int key){

    }

    public int hashFunction(int key){
        return key % size;
    }
}

