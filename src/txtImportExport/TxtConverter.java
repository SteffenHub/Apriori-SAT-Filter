package txtImportExport;

import java.util.ArrayList;
import java.util.List;

public class TxtConverter {

    public static boolean[][] listOfStringOrdersToBooleanArray(List<String> ordersStringList) {
        ordersStringList.removeIf(line -> line.charAt(0) == 'c');
        boolean[][] ordersBool = new boolean[ordersStringList.size()][ordersStringList.get(0).split(",").length];
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
        cnfStr.removeIf(line -> line.charAt(0) == 'c');
        int start = 0;
        // If the first line still contains the cnf description
        if (cnfStr.get(0).charAt(0) == 'p') {
            //then skip this line
            start = 1;
        }
        //go through cnf
        for (int i = start; i < cnfStr.size(); i++) {
            //split line on blank
            String[] aufgeteilt = cnfStr.get(i).split(" ");
            //Reserve memory. Decrement by 1 to ignore the 0 at the end of the line.
            int[] klausel = new int[aufgeteilt.length - 1];
            for (int x = 0; x < klausel.length; x++) {
                klausel[x] = Integer.parseInt(aufgeteilt[x]);
            }
            cnfArr.add(klausel);
        }
        return cnfArr;
    }

}