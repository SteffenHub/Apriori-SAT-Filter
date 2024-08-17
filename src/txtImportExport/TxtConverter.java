package txtImportExport;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts read text data into different formats.
 */
public class TxtConverter {

    /**
     * Converts a list of string orders to a boolean array.
     * Removes comment lines starting with 'c' from the list.
     * Each character in the order string is converted to a boolean value.
     * '0' is converted to false and '1' is converted to true.
     * Throws an exception if an entry in the order string is not '0' or '1'.
     * Throws an exception if the orders have different sizes.
     *
     * @param ordersStringList the list of string orders
     * @return the boolean array representing the orders
     * @throws Error if an entry in the order string is not '0' or '1'
     * @throws Error if the orders have different sizes
     */
    public static boolean[][] listOfStringOrdersToBooleanArray(List<String> ordersStringList) {
        // remove comment lines starting with c
        ordersStringList.removeIf(line -> line.charAt(0) == 'c');
        boolean[][] ordersBool = new boolean[ordersStringList.size()][ordersStringList.getFirst().length()];
        int firstOrderSize = ordersStringList.getFirst().length();
        for (int i = 0; i < ordersStringList.size(); i++) {
            boolean[] order = new boolean[ordersStringList.get(i).length()];
            for (int j = 0; j < order.length; j++) {
                if (ordersStringList.get(i).charAt(j) == '0') {
                    order[j] = false;
                } else if (ordersStringList.get(i).charAt(j) == '1') {
                    order[j] = true;
                } else {
                    throw new Error("The Orders can't be read. Found an entry that don't match '0' or '1'");
                }
            }
            ordersBool[i] = order;
            if (firstOrderSize != order.length){
                throw new Error("Orders have different sizes: " + order.length + " and " + firstOrderSize);
            }
        }
        return ordersBool;
    }

    /**
     * Transforms a cnf of strings to int arrays.
     * The first line is omitted if the CNF description is there e.g.: "p cnf 234 32"
     * Additionally the last element of each line is omitted to avoid the 0's at the end,
     * e.g.: "-50 3 0" becomes new int[]{-50,3}
     *
     * @param cnfStr the previously read cnf as a list of strings
     * @return the CNF as list of int arrays, without first line and zero in each line
     */
    public static List<int[]> stringListToListOfIntArrays(List<String> cnfStr) {
        ArrayList<int[]> cnfArr = new ArrayList<>();
        //go through cnf
        for (String line : cnfStr) {
            // skip comment lines starting with c and the cnf header starting with p
            if (line.startsWith("c") || line.startsWith("p")) {
                continue;
            }
            //split line on blank
            String[] split = line.split(" ");
            //Reserve memory. Decrement by 1 to ignore the 0 at the end of the line.
            int[] clause = new int[split.length - 1];
            for (int x = 0; x < clause.length; x++) {
                clause[x] = Integer.parseInt(split[x]);
            }
            cnfArr.add(clause);
        }
        return cnfArr;
    }
}