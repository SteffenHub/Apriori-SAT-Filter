package args;

public class ReadArgsException extends Exception{
    public ReadArgsException(String args){
        super("Not enough parameter passed: " + args + "\n" + "Example: --order-file data/cnfBuilder100VarsVariance983340976.txt --order-file data/randomCarBuilder_result_10000_freq_result_cnfBuilder100VarsVariance983340976_10Decimal.txt --minSupport 0.8 --minConfidence 0.8 --max-depth 2 --use-sat-solver true");
    }
}