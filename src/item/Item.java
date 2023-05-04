package item;

import Order.Orders;

public class Item {

    /**
     * The unique ItemNumber which matches the variable Number in the SatSolver
     */
    private final int itemNumber;

    private Orders iAmInThisOrders;

    public Item(int itemNumber) {
        this.itemNumber = itemNumber;
        this.iAmInThisOrders = null;
    }

    public int getItemNumber() {
        return this.itemNumber;
    }

    public Orders getInWhichOrders() {
        return this.iAmInThisOrders;
    }

    public void setInWhichOrders(Orders orders){
        this.iAmInThisOrders = orders;
    }

}