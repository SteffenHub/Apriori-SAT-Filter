package order;

/**
 * An exception that is thrown when an item has a wrong index.
 */
public class WrongIndexForItemException extends Exception{
    /**
     * An exception that is thrown when an item has an invalid index.
     */
    public WrongIndexForItemException(int itemNumber){
        super("Item have a wrong Index. Check if the item numbers starts with 0(it should start with 1)" +
                " or The Index is greater than the order sizes. Also possible is a order which" +
                " has not enough items declared. Failure noticed by item: " + itemNumber);
    }
}
