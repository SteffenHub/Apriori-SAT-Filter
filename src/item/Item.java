package item;

import Order.Orders;

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
    private Orders iAmInThisOrders;

    /**
     * Constructor for an Item.
     * iAmInThisOrders is set to null and will be assigned by Orders
     *
     * @param itemNumber The unique ItemNumber which matches the variable Number in the SatSolver(the lowest number is 1)
     */
    public Item(int itemNumber) {
        this.itemNumber = itemNumber;
        this.iAmInThisOrders = null;
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
    public Orders getInWhichOrders() {
        return this.iAmInThisOrders;
    }

    /**
     * setter for the Orders where this Item is selected
     *
     * @param orders the Orders where this Item is selected
     */
    public void setInWhichOrders(Orders orders){
        this.iAmInThisOrders = orders;
    }
}