package apriori;

import args.ArgsInput;
import item.Item;
import item.ItemSet;
import order.DifferentOrderSIzeException;
import order.Orders;
import order.WrongIndexForItemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConclusionBuilder {

    private final HashMap<ItemSet, Double> allPossibleCombinations;
    private final Orders orders;
    private final ArgsInput argsInput;

    public ConclusionBuilder(HashMap<ItemSet, Double> allPossibleCombinations, Orders orders, ArgsInput argsInput) {
        this.allPossibleCombinations = allPossibleCombinations;
        this.orders = orders;
        this.argsInput = argsInput;
    }

    public Conclusion[] run() throws WrongIndexForItemException, DifferentOrderSIzeException {
        List<Conclusion> allPassedConclusions = new ArrayList<>();
        int count = 0;
        int maxCount = this.allPossibleCombinations.keySet().size();
        for (ItemSet itemSet : this.allPossibleCombinations.keySet()) {
            ++count;
            System.out.println("Build Conclusions " + count + "/" + maxCount + " for: " + itemSet);
            for (Conclusion conclusion : this.getPossibleConclusions(itemSet,this.allPossibleCombinations.get(itemSet))){
                conclusion.setConfidence(conclusion.getSupport()/this.orders.getSupport(conclusion.getCondition()));
                if (conclusion.getConfidence() >= this.argsInput.getMinConfidence()){
                    allPassedConclusions.add(conclusion);
                    System.out.println("Found Conclusion which fulfills Confidence: " + conclusion);
                }
            }
        }
        return allPassedConclusions.toArray(new Conclusion[0]);
    }

    public Conclusion[] getPossibleConclusions(ItemSet itemSet, double support) {
        List<Conclusion> allConclusions = new ArrayList<>();
        int lengthOfItemSet = itemSet.getItemArray().length;
        ItemSet[] allSubSets = this.getAllSubSets(itemSet);
        for (ItemSet subSet : allSubSets) {
            for (ItemSet subSetCompare: allSubSets) {
                boolean exists = false;
                for (Item item : subSet.getItemArray()) {
                    if (subSetCompare.contains(item)){
                        exists = true;
                        break;
                    }
                }
                if (!exists && subSet.getItemArray().length + subSetCompare.getItemArray().length == lengthOfItemSet){
                    allConclusions.add(new Conclusion(subSet, subSetCompare, support));
                }
            }
        }
        return allConclusions.toArray(new Conclusion[0]);
    }

    public ItemSet[] getAllSubSets(ItemSet itemSet) {
        ArrayList<ItemSet> subsets = new ArrayList<>();
        int n = itemSet.getItemArray().length;

        // Create all 2^n possible binary numbers of length n
        for (int i = 1; i < (1 << n); i++) {
            ArrayList<Item> subsetItems = new ArrayList<>();

            // Check every bit of the binary number
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    subsetItems.add(itemSet.getItemArray()[j]);
                }
            }

            // Add the current subset to the list if it's not empty and doesn't contain the entire ItemSet
            if (!subsetItems.isEmpty() && subsetItems.size() < n) {
                ItemSet subset = new ItemSet(subsetItems.toArray(new Item[0]));
                subsets.add(subset);
            }
        }
        return subsets.toArray(new ItemSet[0]);
    }
}