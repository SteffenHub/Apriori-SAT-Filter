package item;

import java.util.Comparator;

/**
 * The Item Comparator for Items.
 * This class is used to sort an Item Array.
 */
class ItemComparator implements Comparator<Item> {

    /**
     * compares two items by using its itemNumber
     *
     * @param item1 the first object to be compared.
     * @param item2 the second object to be compared.
     * @return negative integer if item2 > item1 or a positive integer if item1 > item2 and 0 if item1 == item2
     */
    @Override
    public int compare(Item item1, Item item2) {
        return item1.getItemNumber() - item2.getItemNumber();
    }
}