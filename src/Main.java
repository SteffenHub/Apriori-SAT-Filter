// project imports
import apriori.MApriori;
import order.DifferentOrderSIzeException;
import order.Orders;
import apriori.Apriori;
import apriori.Conclusion;
import apriori.ConclusionBuilder;
import args.ArgsInput;
import args.ReadArgsException;
import item.ItemSet;
import order.WrongIndexForItemException;
import txtImportExport.TxtConverter;
import txtImportExport.TxtReaderWriter;
import satSolver.SatSolver;
// external imports
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;
// java imports
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {

    /**
     * The main method of the application.
     * It reads the command line arguments, processes the orders and rules,
     * performs Apriori algorithm to find all possible combinations,
     * generates conclusions based on the combinations,
     * and saves the results in text files.
     *
     * @param args the command line arguments. Examples can be found in ArgsInput class or its Exception class
     * @throws IOException if there is an error reading or writing files
     * @throws ContradictionException if there is a contradiction in the ruleset
     * @throws TimeoutException if the satSolver computation exceeds the time limit
     * @throws ReadArgsException if there are not enough command line arguments
     * @throws DifferentOrderSIzeException if the orders have different sizes
     * @throws WrongIndexForItemException if an item has an invalid index
     */
    public static void main(String[] args) throws IOException, ContradictionException, TimeoutException, ReadArgsException, DifferentOrderSIzeException, WrongIndexForItemException {

        // read args
        ArgsInput argsInput = new ArgsInput(args);

        // start timer
        Instant start = Instant.now();

        // Read the Ruleset and initiate the SatSolver with it
        List<int[]> ruleSet = TxtConverter.stringListToListOfIntArrays(TxtReaderWriter.getTxtFromSamePath(argsInput.getRuleFile()));
        SatSolver satSolver = new SatSolver(ruleSet.toArray(new int[0][]));

        // Read the order file and initiate the Orders object with it
        List<String> readOrders = TxtReaderWriter.getTxtFromSamePath(argsInput.getOrderFile());
        boolean[][] ordersBool = TxtConverter.listOfStringOrdersToBooleanArray(readOrders);
        Orders orders = new Orders(ordersBool);

        // initiate Apriori and start the combination calculation
        MApriori apriori = new MApriori(satSolver, orders, argsInput);
        HashMap<ItemSet, Double> allPossibleCombinationFiltered = apriori.run();

        // Print the needed time
        Instant end = Instant.now();
        Duration interval = Duration.between(start, end);
        System.out.println("time for calculation search all combinations: " + interval.getSeconds() + "sec");

        //save to directory
        List<String> allPossibleCombinationFilteredString = new ArrayList<>();
        for (ItemSet combination: allPossibleCombinationFiltered.keySet()) {
            allPossibleCombinationFilteredString.add(combination + " " + allPossibleCombinationFiltered.get(combination));
        }
        TxtReaderWriter.writeListOfStrings("allPossibleCombination_Apriori_"
                        + argsInput.getRuleFileWithoutPath() + "_"
                        + argsInput.getOderFileWithoutPath() + "_"
                        + argsInput.getMinSupport() + "_"
                        + argsInput.getMinConfidence() + "_"
                        + argsInput.getDepth() + "_"
                        + argsInput.getUseSatSolver()
                        + ".txt", allPossibleCombinationFilteredString);

        // generate the conclusions with all the possible combinations found in Apriori process
        Conclusion[] allFilteredConclusions = new ConclusionBuilder(allPossibleCombinationFiltered, orders, argsInput).run();

        //save to directory
        List<String> allConclusionsString = new ArrayList<>();
        for (Conclusion conclusion : allFilteredConclusions) {
            allConclusionsString.add(conclusion.toString());
        }
        TxtReaderWriter.writeListOfStrings("result_apriori_"
                        + argsInput.getRuleFileWithoutPath() + "_"
                        + argsInput.getOderFileWithoutPath() + "_"
                        + argsInput.getMinSupport() + "_"
                        + argsInput.getMinConfidence() + "_"
                        + argsInput.getDepth() + "_"
                        + argsInput.getUseSatSolver()
                        + ".txt", allConclusionsString);

        // Print the needed time
        end = Instant.now();
        interval = Duration.between(start, end);
        System.out.println("time for calculation: " + interval.getSeconds() + "sec");
    }
}