package apriori;

import item.Item;
import item.ItemSet;

public class Conclusion {

    private final ItemSet condition;
    private final ItemSet consequence;
    private final double support;
    private double confidence;

    public Conclusion(ItemSet condition, ItemSet consequence, double support){
        this.condition = condition;
        this.consequence = consequence;
        this.support = support;
    }

    public void setConfidence(double confidence){
        this.confidence = confidence;
    }

    public double getSupport(){
        return this.support;
    }

    public ItemSet getCondition(){
        return this.condition;
    }

    public double getConfidence(){
        return this.confidence;
    }

    @Override
    public String toString(){
        String output = "[";
        for (int i = 0; i < this.condition.getItemArray().length; i++) {
            output += this.condition.getItemArray()[i].getItemNumber();
            if (i != this.condition.getItemArray().length-1){
                output += ",";
            }
        }
        output += " -> ";
        for (int i = 0; i < this.consequence.getItemArray().length; i++) {
            output += this.consequence.getItemArray()[i].getItemNumber();
            if (i != this.consequence.getItemArray().length-1){
                output += ",";
            }
        }
        output += "] ";
        output += "support: " + this.support + " ";
        output += "confidence: " + this.confidence;
        return output;
    }

}