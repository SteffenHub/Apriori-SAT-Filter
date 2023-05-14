import Order.Orders;
import apriori.Apriori;
import apriori.Conclusion;
import apriori.ConclusionBuilder;
import apriori.ConfidenceBuilder;
import item.Item;
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

    public static void main(String[] args) throws IOException, ContradictionException, TimeoutException {

        boolean saveMoreInterimResults = false;
        double minSupport = Double.NaN;
        double minConfidence = Double.NaN;
        int depth = -1;

        //read args
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--minSupport")){
                minSupport = Double.parseDouble(args[i+1]);
            }
            if (arg.equals("--minConfidence")){
                minConfidence = Double.parseDouble(args[i+1]);
            }
            if (arg.equals("--caching")){
                saveMoreInterimResults = args[i + 1].equals("true");
            }
            if (arg.equals("--depth")){
                depth = Integer.parseInt(args[i+1]);
            }
        }
        if (Double.isNaN(minSupport) || Double.isNaN(minConfidence) || depth == -1){
            System.out.println("Not enough parameters passed");
            return;
        }
        Instant start = Instant.now();

        List<int[]> regelWerk = TxtConverter.stringListToListOfIntArrays(TxtReaderWriter.getTxtFromSamePath("rules.txt"));
        SatSolver satSolver = new SatSolver(regelWerk.toArray(new int[0][]));

        List<String> readOrders = TxtReaderWriter.getTxtFromSamePath("orders.txt");
        boolean[][] ordersBool = TxtConverter.listOfStringOrdersToBooleanArray(readOrders);

        Orders orders = new Orders(ordersBool);
        Apriori apriori = new Apriori(satSolver, orders, depth, minSupport, saveMoreInterimResults);
        HashMap<ItemSet, Double> allPossibleKombinationFiltered = apriori.run();


        //save to directory
        List<String> allPossibleKombinationFilteredString = new ArrayList<>();
        for (ItemSet kombi: allPossibleKombinationFiltered.keySet()) {
            StringBuilder line = new StringBuilder();
            for (Item prDrin : kombi.getItemArray()) {
                line.append(prDrin.getItemNumber()).append(" ");
            }
            line.append(allPossibleKombinationFiltered.get(kombi));
            allPossibleKombinationFilteredString.add(line.toString());
        }
        TxtReaderWriter.writeListOfStrings("allPossibleKombinationFiltered.txt", allPossibleKombinationFilteredString);

        Conclusion[] allConclusions = new ConclusionBuilder(allPossibleKombinationFiltered).run();
        allConclusions = new ConfidenceBuilder(allConclusions, orders, minConfidence, saveMoreInterimResults).run();

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