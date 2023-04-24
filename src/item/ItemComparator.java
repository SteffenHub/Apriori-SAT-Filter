package item;


import java.util.Comparator;

class ItemComparator implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.getItemNumber() - item2.getItemNumber();
    }
}