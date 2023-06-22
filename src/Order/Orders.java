package Order;

import item.Item;
import item.ItemSet;

import java.util.ArrayList;
import java.util.List;

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

    public double getSupport(ItemSet itemSet, boolean saveMoreInterimResults) {

        if (itemSet.getItemArray().length == 0) return 0.0;

        //check if all inWhichOrders is correct else search them
        for (Item item : itemSet.getItemArray()) {
            if (item.getInWhichOrders() == null){
                item.setInWhichOrders(this.getWhichOrders(item));
            }
        }

        Orders intersection;
        if (itemSet.getPreviousItemSet() != null && itemSet.getPreviousItemSet().getInWhichOrders() != null && itemSet.getNewItem() != null) {
            if (itemSet.getPreviousItemSet().getInWhichOrders().getOrders().length < itemSet.getNewItem().getInWhichOrders().getOrders().length) {
                intersection = itemSet.getPreviousItemSet().getInWhichOrders().intersection(itemSet.getNewItem().getInWhichOrders());
            }else{
                intersection = itemSet.getNewItem().getInWhichOrders().intersection(itemSet.getPreviousItemSet().getInWhichOrders());
            }
        }else{
            intersection = itemSet.getItemArray()[0].getInWhichOrders();
            int smallestOrderArrayIn = 0;
            for (int i = 0; i < itemSet.getItemArray().length; i++) {
                if (itemSet.getItemArray()[i].getInWhichOrders().getOrders().length < intersection.getOrders().length){
                    intersection = itemSet.getItemArray()[i].getInWhichOrders();
                    smallestOrderArrayIn = i;
                }
            }
            for (int i = 0; i < itemSet.getItemArray().length; i++) {
                if (i == smallestOrderArrayIn) continue;
                intersection = intersection.intersection(itemSet.getItemArray()[i].getInWhichOrders());
            }
        }
        if (saveMoreInterimResults) {
            itemSet.setInWhichOrders(intersection);
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
        boolean[] foundIn = new boolean[this.getOrders().length];
        int countFound = 0;
        int i = 0;
        int j = 0;
        while (i < this.orders.length && j < orders.getOrders().length) {
            if (this.orders[i].getOrderNumber() < orders.getOrders()[j].getOrderNumber()) {
                i++;
            } else if (this.orders[i].getOrderNumber() > orders.getOrders()[j].getOrderNumber()) {
                j++;
            } else {
                foundIn[i] = true;
                ++countFound;
                i++;
                j++;
            }
        }
        Order[] result = new Order[countFound];
        int count = 0;
        for (int k = 0; k < foundIn.length; k++)
            if (foundIn[k])
                result[count++] = this.getOrders()[k];
        return new Orders(result);
    }

    public Order[] getOrders(){
        return this.orders;
    }
}