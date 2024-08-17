package order;

/**
 * An exception that is thrown when two orders have different sizes.
 */
public class DifferentOrderSIzeException extends Exception{
    /**
     * An exception that is thrown when two orders have different sizes.
     */
    public DifferentOrderSIzeException(String args){
        super("Oder's have different sizes. " + args);
    }
}
