package apriori;

import item.ItemSet;

/**
 * This class represents a Conclusion with a condition and its consequence.
 */
public class Conclusion {

    /**
     * The condition of this Conclusion.
     */
    private final ItemSet condition;

    /**
     * The consequence if this Conclusion.
     */
    private final ItemSet consequence;

    /**
     * The support of all Items in condition and consequence.
     */
    private final double support;

    /**
     * The confidence of this Conclusion.
     */
    private double confidence;

    /**
     * Constructor for a Conclusion.
     *
     * @param condition   The condition of the Conclusion.
     * @param consequence The consequence of the Conclusion.
     * @param support     The support of all Items in condition and consequence.
     */
    public Conclusion(ItemSet condition, ItemSet consequence, double support) {
        this.condition = condition;
        this.consequence = consequence;
        this.support = support;
        this.confidence = Double.NaN;
    }

    /**
     * Setter for the confidence.
     *
     * @param confidence The confidence of this Conclusion.
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    /**
     * Getter for the support.
     *
     * @return The support of all Items in condition and consequence.
     */
    public double getSupport() {
        return this.support;
    }

    /**
     * Getter for the condition ItemSet.
     *
     * @return The condition ItemSet.
     */
    public ItemSet getCondition() {
        return this.condition;
    }

    /**
     * Getter for the confidence.
     *
     * @return the confidence of this Conclusion.
     */
    public double getConfidence() {
        return this.confidence;
    }

    /**
     * Forms the Information of this Conclusion to a String.
     * Using all private Variables: condition, consequence, support and confidence.
     *
     * @return The Information of this Conclusion as a String.
     */
    @Override
    public String toString() {
        String output = "[";
        for (int i = 0; i < this.condition.getItemArray().length; i++) {
            output += this.condition.getItemArray()[i].getItemNumber();
            if (i != this.condition.getItemArray().length - 1) {
                output += ",";
            }
        }
        output += " -> ";
        for (int i = 0; i < this.consequence.getItemArray().length; i++) {
            output += this.consequence.getItemArray()[i].getItemNumber();
            if (i != this.consequence.getItemArray().length - 1) {
                output += ",";
            }
        }
        output += "] ";
        output += "support: " + this.support + " ";
        output += "confidence: " + this.confidence;
        return output;
    }
}