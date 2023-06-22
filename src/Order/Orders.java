package Order;

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
     * Forms the intersection of two orders.
     *
     * @param orders1
     * @param orders2
     * @return the intersection of this and the other orders
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