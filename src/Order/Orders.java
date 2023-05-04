package Order;

import item.Item;
import item.ItemSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Orders {

    private final Order[] orders;

    public Orders(boolean[][] ordersBool) {
        this.orders = new Order[ordersBool.length];
        for (int i = 0; i < ordersBool.length; i++) {
            orders[i] = new Order(ordersBool[i], i+1);
        }
    }

    public Orders(Order[] orders){
        this.orders = orders;
    }

    public double getSupport(ItemSet itemSet) {

        if (itemSet.getItemArray().length == 0) return 0.0;

        //check if all inWhichOrders is correct else search them
        for (Item item : itemSet.getItemArray()) {
            if (item.getInWhichOrders() == null){
                item.setInWhichOrders(this.getWhichOrders(item));
            }
        }

        Orders intersection = itemSet.getItemArray()[0].getInWhichOrders();
        for (int i = 1; i < itemSet.getItemArray().length; i++) {
            intersection = intersection.intersection(itemSet.getItemArray()[i].getInWhichOrders());
        }
        return (double) intersection.getOrders().length / this.orders.length;
    }

    public Orders getWhichOrders(Item item){
        List<Order> isHere = new ArrayList<>();
        for (Order order : this.orders) {

            if (order.isIn(item)){
                isHere.add(order);
            }
        }
        return new Orders(isHere.toArray(new Order[0]));
    }

    /**
     * Forms the intersection of two orders.
     *
     * @param orders the other orders
     * @return the intersection of this and the other orders
     */
    public Orders intersection(Orders orders) {
        Set<Order> result = new HashSet<>();
        int i = 0;
        int j = 0;
        while (i < this.orders.length && j < orders.getOrders().length) {
            if (this.orders[i].getOrderNumber() < orders.getOrders()[j].getOrderNumber()) {
                i++;
            } else if (this.orders[i].getOrderNumber() > orders.getOrders()[j].getOrderNumber()) {
                j++;
            } else {
                result.add(this.orders[i]);
                i++;
                j++;
            }
        }
        return new Orders(result.toArray(new Order[0]));
    }

    public Order[] getOrders(){
        return this.orders;
    }
}
