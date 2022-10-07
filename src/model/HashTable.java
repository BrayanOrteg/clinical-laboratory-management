package model;

public class HashTable<T> implements IHashTable<T> {

    private int size = 1001;
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
            PatientNode pointer;

            while(ver==false){

                pointer=array[code];

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
            throw new Exception("No se encontró el elemento");
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
        return array[code];
    }

    public void chainedHashDelete(int key){
        /*

        if(chainedHashSearch(key).getPreviousPatient()==null && chainedHashSearch(key).getNextPatient()==null){
            array[hashFunction(key)]=null;

        }



        if(chainedHashSearch(key).getPreviousPatient()!=null){
            chainedHashSearch(key).getPreviousPatient().setNextPatient(null);
        }
        else{
            if(chainedHashSearch(key).getNextPatient()!=null){
                array[hashFunction(key)]=chainedHashSearch(key).getNextPatient();
            }
            else{
                array[hashFunction(key)]=null;
            }
        }

         */
    }

    public int hashFunction(int key){
        return key % size;
    }
}

