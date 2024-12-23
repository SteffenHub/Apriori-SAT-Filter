package args;

public class ReadArgsException extends Exception{
    public ReadArgsException(String args){
        super("Not enough parameter passed: " + args + "\n" + "Example: --rule-file data/input_data/rule_files/cnfBuilder100VarsVariance9995700077.txt --order-file data/input_data/sales/randomCarBuilder_result_500000_freq_result_cnfBuilder100VarsVariance9995700077_100Decimal.txt --minSupport 0.6 --minConfidence 0.6 --max-depth 200 --use-sat-solver true --use-procedure MApriori");
    }
}