package order;

import item.Item;
import java.util.Objects;

/**
 * Represents an order in the system.
 */
public class Order {

    /**
     * Represents a boolean array that indicates whether an item is present in orders.
     * Each index in the array corresponds to an order number, starting from 1.
     * The value at each index represents the presence of the item in that order.
     */
    private final boolean[] itemsBool;
    /**
     * Represents the order number of an order in the system.
     * It is a private final variable and cannot be changed once set.
     */
    private final int orderNumber;

    /**
     * Creates an Order object with the given items and order number.
     *
     * @param itemsBool An array of booleans representing whether each item is included in the order.
     *        The index of each item in the array corresponds to its item number.
     *        A value of true at a specific index indicates that the item is included in the order,
     *        while a value of false indicates that the item is not included.
     * @param orderNumber The order number of the order.
     */
    public Order(boolean[] itemsBool, int orderNumber){
        this.itemsBool = itemsBool;
        this.orderNumber = orderNumber;
    }

    /**
     * Returns whether the given item is included in the order.
     *
     * @param item The item to check.
     * @return True if the item is included in the order, false otherwise.
     */
    public boolean isIn(Item item) throws WrongIndexForItemException {
        if (item.getItemNumber() < 1 || this.itemsBool.length < item.getItemNumber()-1) {
            throw new WrongIndexForItemException(item.getItemNumber());
        }
        return this.itemsBool[item.getItemNumber()-1];
    }

    /**
     * This method returns the order number associated with an Order object.
     *
     * @return The order number as an integer.
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Returns the number of items in the order.
     *
     * @return The number of items in the order.
     */
    public int howManyItems(){
        return this.itemsBool.length;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) return false;
        Order o = (Order) other;
        return this.getOrderNumber() == o.getOrderNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderNumber());
    }
}