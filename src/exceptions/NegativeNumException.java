package exceptions;

public class NegativeNumException extends Exception {
    public NegativeNumException(){
        super("No se pueden usar números negativos");
    }
}
