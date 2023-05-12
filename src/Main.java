import Order.Orders;
import apriori.Apriori;
import apriori.Conclusion;
import apriori.ConclusionBuilder;
import item.Item;
import item.ItemSet;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;
import satSolver.SatSolver;
import txtImportExport.TxtConverter;
import txtImportExport.TxtReaderWriter;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ContradictionException, TimeoutException {

        boolean saveMoreInterimResults = false;

        List<int[]> regelWerk = TxtConverter.stringListToListOfIntArrays(TxtReaderWriter.getTxtFromSamePath("rules.txt"));
        SatSolver satSolver = new SatSolver(regelWerk.toArray(new int[0][]));

        List<String> readOrders = TxtReaderWriter.getTxtFromSamePath("orders.txt");
        boolean[][] ordersBool = TxtConverter.listOfStringOrdersToBooleanArray(readOrders);

        Orders orders = new Orders(ordersBool);
        Apriori apriori = new Apriori(satSolver, orders, 3, 0.80, saveMoreInterimResults);
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

    }
}