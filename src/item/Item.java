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
     * The Orders where this item is in as boolean representation.
     * If there is a total of 5 Orders and this item is in Order 1, 2 and 5 the boolean Array will look like:
     * [true, true, false, false, true]
     */
    private boolean[] inWhichOrders;

    /**
     * The number of orders in which the item is included.
     * It is calculated by the number of true inside 'this.inWhichOrders'
     * @See Item#setInWhichOrders
     */
    private int inHowManyOrders;

    /**
     * Constructor for an Item.
     * iAmInThisOrders is set to null and will be assigned in the Orders class
     *
     * @param itemNumber The unique ItemNumber which matches the variable Number
     *                  in the SatSolver resp. In the RuleSet(the lowest number is 1)
     */
    public Item(int itemNumber) {
        this.itemNumber = itemNumber;
        this.inWhichOrders = null;
        this.inHowManyOrders = -1;
    }

    /**
     * Getter for the unique Item Number
     *
     * @return This Item Number
     */
    public int getItemNumber() {
        return this.itemNumber;
    }

    /**
     * Getter for the Orders where this Item is selected.
     * Represented as e.g.:  [true, true, false, false, true] with a total of 5 Orders
     *
     * @return The Orders where this Item is selected. Null if you haven't searched for them yet
     */
    public boolean[] getInWhichOrders() {
        return this.inWhichOrders;
    }

    /**
     * Setter for the Orders where this Item is in
     *
     * @param orders the Orders where this Item is in
     */
    public void setInWhichOrders(boolean[] orders){
        this.inWhichOrders = orders;
        this.inHowManyOrders = 0;
        for (boolean order : orders)
            if (order)
                ++this.inHowManyOrders;
    }

    /**
     * Returns the number of orders in which the item is included.
     *
     * @return The number of orders in which the item is included.
     */
    public int getInHowManyOrders() {
        return inHowManyOrders;
    }
}