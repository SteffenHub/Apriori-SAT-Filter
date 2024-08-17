package apriori;

import order.Orders;
import args.ArgsInput;
import item.Item;
import item.ItemSet;
import org.sat4j.specs.TimeoutException;
import satSolver.SatSolver;
import satSolver.SolverUsages;

import java.util.*;

public class Apriori {

    private final SatSolver satSolver;
    private final Orders orders;
    private final ArgsInput argsInput;

    public Apriori(SatSolver satSolver, Orders orders, ArgsInput argsInput) {
        this.satSolver = satSolver;
        this.orders = orders;
        this.argsInput = argsInput;
    }

    public HashMap<ItemSet, Double> run() throws TimeoutException {

        HashMap<ItemSet, Double> result = new HashMap<>();

        Item[] items = this.createItems(this.orders.getOrders()[0].howManyItems());

        //The items, which are still considered and fulfill the minSupport
        Set<Item> stillPossibleItems = new HashSet<>(Arrays.asList(items));

        //remove items that already have determined truth value
        if (argsInput.getUseSatSolver()) {
            for (int determinedVar : SolverUsages.getDeterminedVars(satSolver)) {
                stillPossibleItems.remove(items[Math.abs(determinedVar) - 1]);
            }
        }
        //Here are the combinations that pass all filters
        HashMap<ItemSet, Double> currentSet = new HashMap<>();
        // Add empty itemSet for the first depth iteration
        currentSet.put(new ItemSet(new Item[]{}), 0.0);

        int depth = 1;
        while (depth <= this.argsInput.getDepth() && !stillPossibleItems.isEmpty()) {
            //The items that will be considered in this run
            Item[] stillPossibleItemArray = stillPossibleItems.toArray(new Item[0]);

            //all configurations from the previous calculation
            List<ItemSet> keyList = new ArrayList<>(currentSet.keySet());

            currentSet = new HashMap<>();

            //go through all previous configurations
            int counter = 0;
            for (ItemSet itemSet : keyList) {
                //add all possible Items to this one
                for (Item item : stillPossibleItemArray) {
                    ++counter;
                    System.out.println("Depth: " + depth + "/" + this.argsInput.getDepth() + " : " + "combinations to check: " + counter + "/" + stillPossibleItemArray.length * keyList.size());

                    ItemSet union = itemSet.union(new ItemSet(new Item[]{item}));

                    //check if this configuration was already reversed e.g. [0,1] and [1,0].
                    if (currentSet.containsKey(union)) {
                        continue;
                    }

                    // if an Item is added to the configuration, which is already in this configuration e.g. [item1, item2] and item2 added
                    if (union.getItemArray().length < depth) {
                        continue;
                    }

                    // If this itemSet is not selectable
                    if (argsInput.getUseSatSolver()) {
                        if (!satSolver.isSatisfiableWithConjunct(union.toIntArray())){
                            continue;
                        }
                    }

                    if (depth >= 2 && argsInput.getUseSatSolver()) {
                        // Do you have to choose this?
                        if (!satSolver.isSatisfiableWithClause(Arrays.stream(union.toIntArray()).map(i -> -i).toArray())) {
                            continue;
                        }
                    }

                    double support = this.orders.getSupport(union);

                    if (support >= this.argsInput.getMinSupport()) {
                        currentSet.put(union, support);
                        result.put(union, support);
                        print(union, support);
                    }
                }
            }
            //update stillPossibleItems
            List<ItemSet> newConfigurationsFromThisCalculation = new ArrayList<>(currentSet.keySet());
            stillPossibleItems = new HashSet<>();
            for (ItemSet prSet : newConfigurationsFromThisCalculation) {
                stillPossibleItems.addAll(Arrays.asList(prSet.getItemArray()));
            }
            ++depth;
            System.out.println("Found new Candidates in this iteration: " + currentSet.size());
            System.out.println("Candidates contain " + stillPossibleItems.size() + " different Items");
            System.out.println();
        }

        return result;
    }

    private Item[] createItems(int howManyItems) {
        Item[] items = new Item[howManyItems];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(i + 1);
        }
        return items;
    }

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