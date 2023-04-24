package Order;

import item.ItemSet;

import java.util.HashSet;
import java.util.Set;

public class Orders {

    private final int ordersSize;

    public Orders(int ordersSize) {
        this.ordersSize = ordersSize;
    }

    public double getSupport(ItemSet itemSet) {
        int[] itemSetArray = itemSet.toIntArray();
        if (itemSetArray.length == 0) {
            return 0.0;
        }
        int[] intersection = itemSet.toItemArray()[0].getInWhichOrders();
        for (int i = 1; i < itemSetArray.length; i++) {
            intersection = this.intersection(intersection, itemSet.toItemArray()[i].getInWhichOrders());
        }
        return (double) intersection.length / this.ordersSize;
    }

    /**
     * Forms the intersection of two int arrays.
     *
     * @param array1 first int array
     * @param array2 second int array
     * @return the intersection of the two arrays
     */
    public int[] intersection(int[] array1, int[] array2) {
        Set<Integer> result = new HashSet<>();
        int i = 0;
        int j = 0;
        while (i < array1.length && j < array2.length) {
            if (array1[i] < array2[j]) {
                i++;
            } else if (array1[i] > array2[j]) {
                j++;
            } else {
                result.add(array1[i]);
                i++;
                j++;
            }
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
