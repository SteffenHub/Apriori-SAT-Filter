package Order;

import item.Item;

public class Order {

    private final boolean[] itemsBool;
    private final int orderNumber;

    public Order(boolean[] itemsBool, int orderNumber){
        this.itemsBool = itemsBool;
        this.orderNumber = orderNumber;
    }

    public boolean isIn(Item item){
        return this.itemsBool[item.getItemNumber()-1];
    }

    public int getOrderNumber() {
        return orderNumber;
    }
}