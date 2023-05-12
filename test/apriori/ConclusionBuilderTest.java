package apriori;

import item.Item;
import item.ItemSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

class ConclusionBuilderTest {

    private ItemSet itemSet;

    private ConclusionBuilder conclusionBuilder;

    @BeforeEach
    void setUp() {
        this.conclusionBuilder = new ConclusionBuilder(new HashMap<>());
        this.itemSet = new ItemSet(
                new Item[]{
                        new Item(1),
                        new Item(2),
                        new Item(3)
                }
        );
    }

    @Test
    void run() {
        HashMap<ItemSet, Double> allConclusionActual = new HashMap<>();
        allConclusionActual.put(this.itemSet, 0.8);
        this.conclusionBuilder = new ConclusionBuilder(allConclusionActual);
        System.out.println(Arrays.toString(this.conclusionBuilder.run()));
    }

    @Test
    void getPossibleConclusions() {
        System.out.println(Arrays.toString(this.conclusionBuilder.getPossibleConclusions(this.itemSet, 0.8)));
    }

    @Test
    void getAllSubSets() {
        ItemSet[] subSetsActual = this.conclusionBuilder.getAllSubSets(this.itemSet);
        System.out.println(Arrays.toString(subSetsActual));
    }
}