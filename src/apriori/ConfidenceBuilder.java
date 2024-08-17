package apriori;

import order.DifferentOrderSIzeException;
import order.Orders;
import args.ArgsInput;

import java.util.ArrayList;
import java.util.List;

public class ConfidenceBuilder {

    private final Conclusion[] allConclusions;
    private final Orders orders;
    private final ArgsInput argsInput;

    public ConfidenceBuilder(Conclusion[] allConclusions, Orders orders, ArgsInput argsInput){
        this.allConclusions = allConclusions;
        this.orders = orders;
        this.argsInput = argsInput;
    }

    public Conclusion[] run() throws DifferentOrderSIzeException {
        int count = 0;
        int maxCount = this.allConclusions.length;
        for (Conclusion conclusion : this.allConclusions) {
            ++count;
            System.out.println("Find Confidence " + count + "/" + maxCount);
            conclusion.setConfidence(conclusion.getSupport()/this.orders.getSupport(conclusion.getCondition()));
            System.out.println(conclusion);
        }
        List<Conclusion> conclusionsFilteredByMinConf = new ArrayList<>();
        for (Conclusion conclusion : this.allConclusions) {
            if (conclusion.getConfidence() >= this.argsInput.getMinConfidence()){
                conclusionsFilteredByMinConf.add(conclusion);
            }
        }
        return conclusionsFilteredByMinConf.toArray(new Conclusion[0]);
    }
}