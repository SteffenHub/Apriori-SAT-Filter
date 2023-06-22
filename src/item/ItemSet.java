package item;

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
     * Constructor for an ItemSet.
     * the given Array will be stored sorted.
     * @param itemArray the ItemSet-Array.
     */
    public ItemSet(Item[] itemArray) {
        this.itemArray = itemArray;
        this.sortItemArray(this.itemArray);
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

    public boolean contains(Item item){
        for (Item itemInArray: this.getItemArray()) {
            if (item.getItemNumber() == itemInArray.getItemNumber()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.getItemArray().length; i++) {
            output.append(this.getItemArray()[i].getItemNumber());
            if (i != this.getItemArray().length -1){
                output.append(" ");
            }
        }
        return output.toString();
    }
}