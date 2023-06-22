package item;

/**
 * Class to Store one Item, which is represented by an integer
 */
public class Item {

    /**
     * The unique ItemNumber which matches the variable Number in the SatSolver(the lowest number is 1)
     */
    private final int itemNumber;

    /**
     * The Orders where this item is selected
     */
    private boolean[] inWhichOrders;

    private int inHowManyOrders;

    /**
     * Constructor for an Item.
     * iAmInThisOrders is set to null and will be assigned by Orders
     *
     * @param itemNumber The unique ItemNumber which matches the variable Number in the SatSolver(the lowest number is 1)
     */
    public Item(int itemNumber) {
        this.itemNumber = itemNumber;
        this.inWhichOrders = null;
        this.inHowManyOrders = -1;
    }

    /**
     * getter for the unique Item Number
     *
     * @return this Item Number
     */
    public int getItemNumber() {
        return this.itemNumber;
    }

    /**
     * getter for the Orders where this Item is selected
     *
     * @return the Orders where this Item is selected. Null if you haven't searched for them yet
     */
    public boolean[] getInWhichOrders() {
        return this.inWhichOrders;
    }

    /**
     * setter for the Orders where this Item is selected
     *
     * @param orders the Orders where this Item is selected
     */
    public void setInWhichOrders(boolean[] orders){
        this.inWhichOrders = orders;
        this.inHowManyOrders = 0;
        for (boolean order : orders)
            if (order)
                ++this.inHowManyOrders;
    }

    public int getInHowManyOrders() {
        return inHowManyOrders;
    }
}