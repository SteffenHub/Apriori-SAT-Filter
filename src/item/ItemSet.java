package item;

import Order.Orders;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for an Item Set which is represented by an Item-Array.
 */
public class ItemSet {

    /**
     * The Item-Arrays which represents an Item Set.
     */
    private final Item[] itemArray;

    /**
     * In which Orders all Items are selected.
     */
    private Orders inWhichOrders;

    /**
     * The previous ItemSet, so this ItemSet is build with the previous ItemSet and the newItem.
     */
    private final ItemSet previousItemSet;

    /**
     * the new Item, so this ItemSet is build with the previous ItemSet and the newItem.
     */
    private final Item newItem;

    /**
     * Constructor for an ItemSet.
     * the given Array will be stored sorted.
     * @param itemArray the ItemSet-Array.
     */
    public ItemSet(Item[] itemArray) {
        this.itemArray = itemArray;
        this.sortItemArray(this.itemArray);
        this.inWhichOrders = null;
        this.previousItemSet = null;
        this.newItem = null;
    }

    /**
     * A Constructor to use previousItemSet and newItem.
     * This ItemSet is build with the previous ItemSet and the newItem.
     *
     * @param previousItemSet The previous ItemSet, so this ItemSet is build with the previous ItemSet and the newItem.
     * @param newItem the new Item, so this ItemSet is build with the previous ItemSet and the newItem.
     */
    public ItemSet(ItemSet previousItemSet, Item newItem) {
        this.previousItemSet = previousItemSet;
        this.newItem = newItem;

        //create new itemArray by adding the new Item to the previous itemSet
        this.itemArray = new Item[previousItemSet.getItemArray().length + 1];
        System.arraycopy(previousItemSet.getItemArray(), 0, this.itemArray, 0, previousItemSet.getItemArray().length);
        this.itemArray[previousItemSet.getItemArray().length] = newItem;

        this.sortItemArray(this.itemArray);
        this.inWhichOrders = null;
    }

    /**
     * getter for in which Orders all Items are selected.
     *
     * @return In which Orders all Items are selected
     */
    public Orders getInWhichOrders(){
        return this.inWhichOrders;
    }

    /**
     * setter for in which Orders all Items are selected
     * @param inWhichOrders In which Orders all Items are selected
     */
    public void setInWhichOrders(Orders inWhichOrders){
        this.inWhichOrders = inWhichOrders;
    }

    /**
     * getter for the Item-Array.
     *
     * @return the Item-Array which represents the ItemSet.
     */
    public Item[] getItemArray() {
        return itemArray;
    }

    /**
     * Forms the Item-Array to an Integer-Array by using the Item-Number.
     *
     * @return the Item-Arrays as an Integer-Array where each Integer represents the Item-Number.
     */
    public int[] toIntArray() {
        int[] intArray = new int[this.itemArray.length];
        for (int i = 0; i < this.itemArray.length; i++) {
            intArray[i] = this.itemArray[i].getItemNumber();
        }
        return intArray;
    }

    /**
     * default equals Method. This is used in Apriori in a Set to check if an ItemSet is already in a Set of ItemSets.
     *
     * @param other the other ItemSet.
     * @return whether this ItemSet and the given ItemSet have stored the same Items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) return false;
        ItemSet o = (ItemSet) other;

        if (o.getItemArray().length != this.itemArray.length) return false;
        for (int i = 0; i < this.itemArray.length; i++) {
            if (this.itemArray[i] != o.getItemArray()[i]) return false;
        }
        return true;
    }

    /**
     * Sorts the Item-Array by the Item-Numbers using a default Item Comparator.
     *
     * @param itemArray the Item-Array which should be sorted.
     */
    public void sortItemArray(Item[] itemArray) {
        // Sort the array using a custom comparator
        Arrays.sort(itemArray, new ItemComparator());
    }

    /**
     * Forms the union set of this and the given ItemSet.
     * The result provides a new ItemSet with all Items in both ItemSets with no duplicates.
     *
     * @param itemSet With which ItemSet should a union be formed
     * @return the union set of this and the given ItemSet
     */
    public ItemSet union(ItemSet itemSet) {
        Set<Item> set = new HashSet<>();

        Collections.addAll(set, this.getItemArray());
        Collections.addAll(set, itemSet.getItemArray());

        return new ItemSet(set.toArray(new Item[0]));
    }

    /**
     * default hashCode by using the Item-Array.
     * This is used together with the default equals Method
     *
     * @return the hashcode of the stored Item-Array
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(itemArray);
    }

    public ItemSet getPreviousItemSet() {
        return previousItemSet;
    }

    public Item getNewItem() {
        return newItem;
    }
}