import order.Orders;
import apriori.Apriori;
import apriori.Conclusion;
import apriori.ConclusionBuilder;
import apriori.ConfidenceBuilder;
import args.ArgsInput;
import args.ReadArgsException;
import item.ItemSet;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;
import satSolver.SatSolver;
import txtImportExport.TxtConverter;
import txtImportExport.TxtReaderWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ContradictionException, TimeoutException, ReadArgsException {

        //read args
        ArgsInput argsInput = new ArgsInput(args);

        Instant start = Instant.now();

        List<int[]> regelWerk = TxtConverter.stringListToListOfIntArrays(TxtReaderWriter.getTxtFromSamePath(argsInput.getRuleFile()));
        SatSolver satSolver = new SatSolver(regelWerk.toArray(new int[0][]));

        List<String> readOrders = TxtReaderWriter.getTxtFromSamePath(argsInput.getOrderFile());
        boolean[][] ordersBool = TxtConverter.listOfStringOrdersToBooleanArray(readOrders);

        Orders orders = new Orders(ordersBool);
        Apriori apriori = new Apriori(satSolver, orders, argsInput);
        HashMap<ItemSet, Double> allPossibleKombinationFiltered = apriori.run();


        //save to directory
        List<String> allPossibleKombinationFilteredString = new ArrayList<>();
        for (ItemSet kombi: allPossibleKombinationFiltered.keySet()) {
            allPossibleKombinationFilteredString.add(kombi + " " + allPossibleKombinationFiltered.get(kombi));
        }
        TxtReaderWriter.writeListOfStrings("allPossibleKombinationFiltered.txt", allPossibleKombinationFilteredString);

        Conclusion[] allConclusions = new ConclusionBuilder(allPossibleKombinationFiltered).run();
        allConclusions = new ConfidenceBuilder(allConclusions, orders, argsInput).run();

        //save to directory
        List<String> allConclusionsString = new ArrayList<>();
        for (Conclusion conclusion : allConclusions) {
            allConclusionsString.add(conclusion.toString());
        }
        TxtReaderWriter.writeListOfStrings("result.txt", allConclusionsString);

        Instant end = Instant.now();
        Duration interval = Duration.between(start, end);
        System.out.println("time for calculation: " + interval.getSeconds() + "sec");
    }
}