package apriori;

import order.DifferentOrderSIzeException;
import order.Order;
import order.Orders;
import args.ArgsInput;
import item.Item;
import item.ItemSet;
import order.WrongIndexForItemException;
import org.sat4j.specs.TimeoutException;
import satSolver.SatSolver;
import satSolver.SolverUsages;
import txtImportExport.TxtReaderWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * The Apriori class is responsible for running the Apriori algorithm for mining frequent itemSets from given orders.
 */
public class MApriori {

    /**
     * This variable is an instance of the SatSolver class, which provides the core functionality of the Sat4J Solver.
     * It is used to solve satisfiability problems and check different conditions using the SAT solver.
     */
    private final SatSolver satSolver;
    /**
     * The Orders class is designed to manage a collection of all Order objects.
     * This includes calculating their 'support value'.
     */
    private final Orders orders;
    /**
     * The argsInput represents all input data given when starting the program.
     */
    private final ArgsInput argsInput;

    /**
     * The Apriori class is responsible for running the Apriori algorithm for mining frequent itemSets from given orders.
     *
     * @param satSolver The SatSolver object used for solving SAT problems.
     * @param orders The Orders object containing the transaction data.
     * @param argsInput The ArgsInput object containing the input arguments.
     */
    public MApriori(SatSolver satSolver, Orders orders, ArgsInput argsInput) {
        this.satSolver = satSolver;
        this.orders = orders;
        this.argsInput = argsInput;
    }

    /**
     * This method runs the Apriori algorithm for mining frequent itemSets from given orders.
     * It iteratively generates new combinations of items and checks their support to determine frequent itemSets.
     *
     * @return A HashMap containing the frequent itemSets as keys and their support as values.
     * @throws TimeoutException if a timeout occurs during the SatSolver execution.
     * @throws DifferentOrderSIzeException if there are different order sizes in the orders.
     * @throws WrongIndexForItemException if an invalid index is provided for an item.
     */
    public HashMap<ItemSet, Double> run() throws TimeoutException, DifferentOrderSIzeException, WrongIndexForItemException, IOException {

        // This is the final result variable it will be filled during the process
        HashMap<ItemSet, Double> result = new HashMap<>();

        // A list of all Items in the Orders
        ItemSet[] itemSets = this.createItemSets(this.orders.getOrders()[0].howManyItems());

        // The items, which are still considered and fulfill the minSupport
        // At the first iteration here are all items
        // Still Possible Items updated again at the end of the for loop
        Set<ItemSet> stillPossibleItems = new HashSet<>(Arrays.asList(itemSets));


        // Here are the combinations that pass all filters in this iteration
        // After the first iteration this will be all Items that fulfill the minSupport and pass the SAT check
        // After the second iteration this will be all combinations of two Items that fulfill the minSupport and the Sat check together
        // ...
        Set<ItemSet> currentSet = new HashSet<>();

        List<String> statusLog = new ArrayList<>();
        statusLog.add("depth, " +
                "checked combinations, " +
                "survived itemSets, " +
                "survived items, " +
                "searched for support, " +
                "skipped because checked already checked in other combination, " +
                "skipped because already checked in previous iterations, " +
                "skipped because you can't choose the combination, " +
                "skipped because you have to choose the combination, " +
                "calculation time in sec");

        int depth = 1;
        // break the while loop if the max depth from args input reached or all possible combinations checked
        while (depth <= this.argsInput.getDepth() && !stillPossibleItems.isEmpty()) {
            Instant start = Instant.now();
            int searchedForSupportTimes = 0;
            int skippedBecauseCheckedOtherwise = 0;
            int skippedBecauseAlreadyChecked = 0;
            int skippedBecauseCantChoose = 0;
            int skippedBecauseYouHaveToChoose = 0;

            // The items that will be considered in this run and builds the cross product with the 'currentSet' from previous iteration
            ItemSet[] stillPossibleItemArray = stillPossibleItems.toArray(new ItemSet[0]);

            // All combinations from the previous calculation that build the cross product with the 'stillPossibleItems' from previous iteration
            List<ItemSet> keyList = new ArrayList<>(currentSet);

            if (depth ==1){
                keyList = new ArrayList<>(stillPossibleItems);
                stillPossibleItemArray = new ItemSet[]{new ItemSet(new Item[]{new Item(-1)})};
            }

            // Clear the currentSet to fill it with the new Combinations in this iteration
            currentSet = new HashSet<>();

            //go through all previous combinations
            int counter = 0;
            for (ItemSet itemSet : keyList) { // e.g. itemSet = {1,2,3}
                //add all possible Items to this one
                for (ItemSet item : stillPossibleItemArray) { // e.g. item = {7}
                    ++counter;
                    System.out.println("Depth: " + depth + "/" + this.argsInput.getDepth() + " : " + "combinations to check: " + counter + "/" + stillPossibleItemArray.length * keyList.size());

                    // Union the itemSet with the new Item
                    ItemSet union;
                    if (depth == 1){
                        union = itemSet;
                    }else {
                        union = itemSet.union(item); // e.g. {1,2,3} UNION {7} = {1,2,3,7}
                    }
                    // Check if this configuration was already considered reversed e.g. {1} UNION {2} = {1,2} = {2} UNION {1}.
                    // or {16,67} UNION {80} = {16,67,80} = {67,80} UNION {16}
                    if (currentSet.contains(union)) {
                        ++skippedBecauseCheckedOtherwise;
                        continue;
                    }

                    // If an Item is added to the combination, which is already checked in the previous iteration
                    // e.g. [item1, item2] and item2 added
                    if (union.getItemArray().length < depth) {
                        ++skippedBecauseAlreadyChecked;
                        continue;
                    }

                    // Can you choose this itemSet?
                    // If this itemSet is not selectable e.g. union={1,2} but (1 AND 2) is not possible
                    // So these Items are depend on each other and aren't interesting for the result
                    if (argsInput.getUseSatSolver()) {
                        if (!satSolver.isSatisfiableWithConjunct(union.toIntArray())){
                            ++ skippedBecauseCantChoose;
                            continue;
                        }
                    }

                    // Do you have to choose this?
                    // e.g. union={1,2} but (-1 OR -2)=-(1 AND 2) is not possible, so you have to choose {1,2} together
                    // and this item depend on each other and aren't interesting for the result
                    if (argsInput.getUseSatSolver()) {
                        if (!satSolver.isSatisfiableWithClause(Arrays.stream(union.toIntArray()).map(i -> -i).toArray())) {
                            ++ skippedBecauseYouHaveToChoose;
                            continue;
                        }
                    }

                    // All checks passed so search for the support
                    ++ searchedForSupportTimes;
                    ItemSet newItemSet;
                    if (depth == 1){
                        newItemSet = this.orders.getSupport(itemSet, item,true);
                    }else {
                        newItemSet = this.orders.getSupport(itemSet, item, false);
                    }
                    // If the itemSet(union) fulfills the support we save them
                    if (newItemSet.getSupport() >= this.argsInput.getMinSupport()) {
                        currentSet.add(newItemSet);
                        result.put(newItemSet, newItemSet.getSupport());
                        print(newItemSet, newItemSet.getSupport());
                    }
                }
            }

            //update stillPossibleItems
            Set<ItemSet> newStillPossibleItems = new HashSet<>();
            for (ItemSet prSet : currentSet) {
                for (Item item: prSet.getItemArray()){
                    for (ItemSet itemSet : stillPossibleItems){
                        if (itemSet.getItemArray().length != 1){
                            continue;
                        }
                        if (itemSet.getItemArray()[0].getItemNumber() == item.getItemNumber()){
                            newStillPossibleItems.add(itemSet);
                        }
                    }
                }
            }
            stillPossibleItems = newStillPossibleItems;
            System.out.println("Found new Candidates in this iteration: " + currentSet.size());
            System.out.println("Candidates contain " + stillPossibleItems.size() + " different Items");
            System.out.println();
            statusLog.add(depth+", "
                    +counter+", "
                    +currentSet.size()+", "
                    +stillPossibleItems.size()+", "
                    +searchedForSupportTimes+", "
                    +skippedBecauseCheckedOtherwise+", "
                    +skippedBecauseAlreadyChecked+", "
                    +skippedBecauseCantChoose+", "
                    +skippedBecauseYouHaveToChoose+", "
                    + Duration.between(start, Instant.now()).getSeconds());
            ++depth;
        }
        TxtReaderWriter.writeListOfStrings("status_log_"
                + argsInput.getRuleFileWithoutPath() + "_"
                + argsInput.getOderFileWithoutPath() + "_"
                + argsInput.getMinSupport() + "_"
                + argsInput.getMinConfidence() + "_"
                + argsInput.getDepth() + "_"
                + argsInput.getUseSatSolver() + "_"
                + argsInput.getUseProcedure()
                + ".txt", statusLog);
        return result;
    }

    /**
     * Creates an Array of Items if index starting by Item1, Item2, ...
     *
     * @param howManyItems How many Items exists
     * @return An Array of 'howManyItems' as Array of Items
     */
    private ItemSet[] createItemSets(int howManyItems) {
        ItemSet[] itemSets = new ItemSet[howManyItems];
        for (int i = 0; i < itemSets.length; i++) {
            itemSets[i] = new ItemSet(new Item[]{new Item(i+1)});
        }
        return itemSets;
    }

    /**
     * Prints an ItemSet and his support to the console
     *
     * @param itemSet The ItemSet
     * @param support The Support of this ItemSet
     */
    private void print(ItemSet itemSet, double support) {
        System.out.print("Found ItemSet which fulfills the min Support: ");
        int[] itemArray = itemSet.toIntArray();
        for (int i = 0; i < itemArray.length; i++) {
            System.out.print(itemSet.getItemArray()[i].getItemNumber());
            if (i + 1 < itemArray.length) {
                System.out.print(" AND ");
            }
        }
        System.out.print(": ");
        System.out.println(support);
    }
}