package item;

import java.util.ArrayList;
import java.util.List;

public class Item {

    /**
     * The unique ItemNumber which matches the variable Number in the SatSolver
     */
    private final int itemNumber;

    private final int[] iAmInThisOrders;

    public Item(int itemNumber, boolean[][] OrdersBool) {
        this.itemNumber = itemNumber;
        List<Integer> isHere = new ArrayList<>();
        for (int i = 0; i < OrdersBool.length; i++) {
            if (OrdersBool[i][this.itemNumber - 1]) {
                isHere.add(i);
            }
        }
        this.iAmInThisOrders = isHere.stream().mapToInt(Integer::intValue).toArray();
    }

    public int getItemNumber() {
        return this.itemNumber;
    }

    public int[] getInWhichOrders() {
        return this.iAmInThisOrders;
    }

}