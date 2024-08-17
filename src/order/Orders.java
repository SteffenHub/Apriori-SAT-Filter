package order;

import item.Item;
import item.ItemSet;

/**
 * The Orders class is designed to manage a collection of all Order objects.
 * This includes calculating their 'support value'.
 */
public class Orders {

    /**
     * Represents an array of Order objects.
     */
    private final Order[] orders;

    /**
     * Represents a collection of orders.
     *
     * @param ordersBool The read orders
     */
    public Orders(boolean[][] ordersBool) {
        this.orders = new Order[ordersBool.length];
        for (int i = 0; i < ordersBool.length; i++) {
            orders[i] = new Order(ordersBool[i], i + 1);
        }
    }

    /**
     * Returns the support value for the given item set.
     * The support value is the ratio of the number of orders that contain all the items in the item set
     * to the total number of orders in the system.
     * The support value is calculated by saving the orders in which a variable is as a boolean array in the Item class.
     * For example for a total of 5 orders: item1 is in [true, true, false, true, false] => support for item1 is 3/5
     * if the itemSet has a length of 2 than the intersection of true values are build.
     * if item2 is in [true, false, false, true, true] than both item1 and item2 are in the true intersection of item1 and item2:
     * [true, false, false, true, false] => support for item1 and item2 is 2/5
     *
     * @param itemSet The item set for which to calculate the support value.
     * @return The support value for the item set as a double.
     * @throws DifferentOrderSIzeException If the orders have different sizes.
     */
    public double getSupport(ItemSet itemSet) throws DifferentOrderSIzeException {

        if (itemSet.getItemArray().length == 0) return 0.0;

        //check if all inWhichOrders is correct else search them
        for (Item item : itemSet.getItemArray()) {
            if (item.getInWhichOrders() == null) {
                item.setInWhichOrders(this.getWhichOrders(item));
            }
        }

        boolean[] intersection = itemSet.getItemArray()[0].getInWhichOrders();
        int smallestOrderArrayIn = 0;
        for (int i = 0; i < itemSet.getItemArray().length; i++) {
            if (itemSet.getItemArray()[i].getInHowManyOrders() < itemSet.getItemArray()[smallestOrderArrayIn].getInHowManyOrders()) {
                intersection = itemSet.getItemArray()[i].getInWhichOrders();
                smallestOrderArrayIn = i;
            }
        }
        for (int i = 0; i < itemSet.getItemArray().length; i++) {
            if (i == smallestOrderArrayIn) continue;
            intersection = this.intersection(intersection, itemSet.getItemArray()[i].getInWhichOrders());
        }

        int countInHowManyOrders = 0;
        for (boolean order : intersection)
            if (order)
                ++countInHowManyOrders;
        return (double) countInHowManyOrders / this.orders.length;
    }


    /**
     * Returns a boolean array indicating which orders contain the given item.
     *
     * @param item The item to check.
     * @return A boolean array where each element represents whether an order contains the item.
     */
    public boolean[] getWhichOrders(Item item) {
        boolean[] isHereBool = new boolean[this.orders.length];
        for (Order order : this.orders) {
            if (order.isIn(item)) {
                isHereBool[order.getOrderNumber() - 1] = true;
            }
        }
        return isHereBool;
    }


    /**
     * Computes the intersection of two boolean arrays.
     * <p>
     * The intersection is computed by comparing the elements at the same index
     * in each array, and setting the result at that index to true if and only if
     * the elements in both arrays are true. If the element in either array is false,
     * the corresponding element in the result array will be false.
     * <p>
     * Note: This method assumes that both input arrays have the same length.
     *
     * @param orders1 the first boolean array
     * @param orders2 the second boolean array
     * @return a new boolean array representing the intersection of the input arrays
     */
    public boolean[] intersection(boolean[] orders1, boolean[] orders2) throws DifferentOrderSIzeException {
        if (orders1.length != orders2.length){
            throw new DifferentOrderSIzeException(orders1.length + " != " + orders2.length);
        }
        boolean[] result = new boolean[orders1.length];
        for (int i = 0; i < orders1.length; i++)
            if (orders1[i] && orders2[i])
                result[i] = true;
        return result;
    }

    /**
     * Getter for all orders
     *
     * @return The array of orders.
     */
    public Order[] getOrders() {
        return this.orders;
    }
}