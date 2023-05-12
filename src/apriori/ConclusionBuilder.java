package apriori;

import item.Item;
import item.ItemSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConclusionBuilder {

    private final HashMap<ItemSet, Double> allPossibleCombinations;

    public ConclusionBuilder(HashMap<ItemSet, Double> allPossibleCombinations) {
        this.allPossibleCombinations = allPossibleCombinations;
    }

    public Conclusion[] run() {
        List<Conclusion> allConclusions = new ArrayList<>();
        for (ItemSet itemSet : this.allPossibleCombinations.keySet()) {
            allConclusions.addAll(Arrays.asList(this.getPossibleConclusions(itemSet, this.allPossibleCombinations.get(itemSet))));
        }
        return allConclusions.toArray(new Conclusion[0]);
    }

    public Conclusion[] getPossibleConclusions(ItemSet itemSet, double support) {
        List<Conclusion> allConclusions = new ArrayList<>();
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
                if (!exists){
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