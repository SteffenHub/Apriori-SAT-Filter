package order;

public class DifferentOrderSIzeException extends Exception{
    public DifferentOrderSIzeException(String args){
        super("Oder's have different sizes. " + args);
    }
}
