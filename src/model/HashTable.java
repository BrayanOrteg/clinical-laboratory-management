package model;

public class HashTable<T> implements IHashTable<PatientNode<T>> {

    private final int size = 1001;
    private PatientNode [] array=new PatientNode [size];

    public HashTable() {

    }

    public void chainedHashInsert(PatientNode node) {

        int code = hashFunction(node.getKey());
        if(array[code]==null){
            array[code]=node;
        }
        else{
            boolean ver=false;
            PatientNode pointer=array[code];

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


    public PatientNode chainedHashSearch(int key) throws Exception {

        int code = hashFunction(key);
        boolean var = false;
        PatientNode pointer=array[code];

        if(pointer==null){
            throw new Exception("The patient is not registered or has not been attended");
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

    public PatientNode chainedHashDelete(int key) {

        boolean var = false;
        PatientNode pointer=null;

        try {
            pointer = chainedHashSearch(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(pointer!=null) {

            int code= hashFunction(pointer.getKey());

            if (pointer.getPreviousPatient() == null && pointer.getNextPatient() == null){
                array[code]=null;
            }else if (pointer.getPreviousPatient() == null && pointer.getNextPatient() != null){
                array[code]=pointer.getNextPatient();
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

    public int hashFunction(int key){
        return key % size;
    }
}

