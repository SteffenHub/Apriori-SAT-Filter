package order;

import item.Item;
import item.ItemSet;

public class Orders {

    private final Order[] orders;

    public Orders(boolean[][] ordersBool) {
        this.orders = new Order[ordersBool.length];
        for (int i = 0; i < ordersBool.length; i++) {
            orders[i] = new Order(ordersBool[i], i + 1);
        }
    }

    public double getSupport(ItemSet itemSet) {

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
    public boolean[] intersection(boolean[] orders1, boolean[] orders2) {
        boolean[] result = new boolean[orders1.length];
        for (int i = 0; i < orders1.length; i++)
            if (orders1[i] && orders2[i])
                result[i] = true;
        return result;
    }

    public Order[] getOrders() {
        return this.orders;
    }
}