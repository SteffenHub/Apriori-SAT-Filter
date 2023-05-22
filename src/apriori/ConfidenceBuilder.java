package apriori;

import Order.Orders;

import java.util.ArrayList;
import java.util.List;

public class ConfidenceBuilder {

    private final Conclusion[] allConclusions;
    private final Orders orders;
    private final boolean saveMoreInterimResults;
    private final double minConfidence;

    public ConfidenceBuilder(Conclusion[] allConclusions, Orders orders, double minConfidence, boolean saveMoreInterimResults){
        this.allConclusions = allConclusions;
        this.orders = orders;
        this.saveMoreInterimResults = saveMoreInterimResults;
        this.minConfidence = minConfidence;
    }

    public Conclusion[] run(){
        int count = 0;
        int maxCount = this.allConclusions.length;
        for (Conclusion conclusion : this.allConclusions) {
            ++count;
            System.out.println("Find Confidence " + count + "/" + maxCount);
            conclusion.setConfidence(conclusion.getSupport()/this.orders.getSupport(conclusion.getCondition(),this.saveMoreInterimResults));
        }
        List<Conclusion> conclusionsFilteredByMinConf = new ArrayList<>();
        for (Conclusion conclusion : this.allConclusions) {
            if (conclusion.getConfidence() >= this.minConfidence){
                conclusionsFilteredByMinConf.add(conclusion);
            }
        }
        return conclusionsFilteredByMinConf.toArray(new Conclusion[0]);
    }
}