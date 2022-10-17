package model;

public class HashTable<T> implements IHashTable<PatientNode<T>> {

    private final int size = 1001;
    private PatientNode [] array=new PatientNode [size];

    public HashTable() {

    }

    public void chainedHashInsert(PatientNode node) {

        long code = hashFunction(node.getKey());
        if(array[Math.toIntExact(code)]==null){
            array[Math.toIntExact(code)]=node;
        }
        else{
            boolean ver=false;
            PatientNode pointer=array[Math.toIntExact(code)];

            while(ver==false){

                if(pointer.getNextPatient()==null){
                    pointer.setNextPatient(node);
                    ver=true;
                }
                else{
                    pointer=pointer.getNextPatient();
                }
            }
        }
    }


    public PatientNode chainedHashSearch(long key) {

        long code = hashFunction(key);
        boolean var = false;
        PatientNode pointer=array[Math.toIntExact(code)];

        if(pointer==null){
            return null;
        }

        while (var == false) {
            if (pointer.getKey() == key) {
                var=true;
            }else{
                if(pointer.getNextPatient()==null){
                    return null;
                }
                else{
                    pointer=pointer.getNextPatient();
                }
            }
        }
        return pointer;
    }

    public PatientNode chainedHashDelete(long key) {

        boolean var = false;
        PatientNode pointer=null;
        pointer = chainedHashSearch(key);


        if(pointer!=null) {

            long code= hashFunction(pointer.getKey());

            if (pointer.getPreviousPatient() == null && pointer.getNextPatient() == null){
                array[Math.toIntExact(code)]=null;
            }else if (pointer.getPreviousPatient() == null && pointer.getNextPatient() != null){
                array[Math.toIntExact(code)]=pointer.getNextPatient();
                pointer.getNextPatient().setPreviousPatient(null);
            }else if (pointer.getPreviousPatient() != null && pointer.getNextPatient() == null){
                pointer.getPreviousPatient().setNextPatient(null);
            }else if (pointer.getPreviousPatient() != null && pointer.getNextPatient() != null){
                pointer.getPreviousPatient().setNextPatient(pointer.getNextPatient());
                pointer.getNextPatient().setPreviousPatient(pointer.getPreviousPatient());
            }
        }
        return pointer;
    }

    public long hashFunction( long key){
        return key % size;
    }
}

