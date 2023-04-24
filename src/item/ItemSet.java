package item;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ItemSet {
    private final Item[] itemArray;

    public ItemSet(Item[] itemArray) {
        this.itemArray = itemArray;
        this.sortItemArray(this.itemArray);
    }

    public Item[] toItemArray() {
        return itemArray;
    }

    public int[] toIntArray() {
        int[] intArray = new int[this.itemArray.length];
        for (int i = 0; i < this.itemArray.length; i++) {
            intArray[i] = this.itemArray[i].getItemNumber();
        }
        return intArray;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) return false;
        ItemSet o = (ItemSet) other;

        if (o.toItemArray().length != this.itemArray.length) return false;
        for (int i = 0; i < this.itemArray.length; i++) {
            if (this.itemArray[i] != o.toItemArray()[i]) return false;
        }
        return true;
    }

    public void sortItemArray(Item[] itemArray) {
        // Sort the array using a custom comparator
        Arrays.sort(itemArray, new ItemComparator());
    }

    public ItemSet union(ItemSet itemSet) {
        Set<Item> set = new HashSet<>();

        Collections.addAll(set, this.toItemArray());
        Collections.addAll(set, itemSet.toItemArray());

        return new ItemSet(set.toArray(new Item[0]));
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(itemArray);
    }
}