package args;

import java.util.Arrays;

/**
 * This class represents the input arguments for the program.
 * It stores the minimum support, minimum confidence, depth, useSatSolver, ruleFile, and orderFile.
 * The constructor takes an array of String arguments and sets the values accordingly.
 * It also has methods to get the individual values.
 */
public class ArgsInput {

    /**
     * Private variable that represents the minimum support value.
     * The value is set using the "--minSupport" command line argument.
     * It is used as a threshold to determine which itemSets are considered frequent.
     */
    private double minSupport;

    private double minConfidence;
    /**
     * This variable represents the depth parameter for the program.
     * It is used to specify the maximum depth for the Apriori search.
     * a depth of 2 means the Apriori algorithm will only check itemSet combination of length 2.
     * e.g. [1, 2] which results in 1 -> 2 and 2 -> 1.
     * with a depth of 3 the Apriori algorithm will check all combinations till itemSet of length 3
     * e.g. [1, 2, 3] which results in 1 -> (2 AND 3) and (1 AND 3) -> 2 ...
     * A higher depth value will result in more detailed or comprehensive results, while a lower value provide faster processing.
     * If you want all combinations to check set the depth to a high value e.g. 10000.
     * The Program will break automatically if there are no more combinations left.
     */
    private int depth;
    /**
     * Specifies whether to use the SAT solver for processing.
     * The default value is True, so the use of this parameter is optional.
     * If it is set to true the satSolver will remove uninterested results like combination containing
     * items which you have to choose anyway, or you can't even choose them or combinations which are not
     * selectable like (1 AND 2) -> 3 but 1,2 and 3 aren't selectable together.
     * For this filter it uses a ruleset given as a CNF File.
     */
    private boolean useSatSolver;

    /**
     * The ruleFile variable stores the path of the rule file to be used in the program.
     * The ruleFile contains a rule set given as a CNF
     */
    private String ruleFile;
    /**
     * Represents the path of the orders file containing all Orders
     */
    private String orderFile;

    private boolean useMApriori;

    /**
     * Initializes an instance of ArgsInput with the given arguments.
     *
     * @param args an array of command line arguments
     * @throws ReadArgsException if required arguments are missing
     */
    public ArgsInput(String[] args) throws ReadArgsException {
        this.minSupport = Double.NaN;
        this.minConfidence = Double.NaN;
        this.depth = -1;
        this.ruleFile = "";
        this.orderFile = "";
        this.useSatSolver = true;
        this.useMApriori = false;
        this.readArgs(args);
    }

    /**
     * Reads and processes command line arguments.
     * The read input will be saved in the class variables
     *
     * @param args the array of command line arguments
     * @throws ReadArgsException if required arguments are missing
     */
    private void readArgs(String[] args) throws ReadArgsException {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--use-sat-solver")){
                this.useSatSolver = Boolean.parseBoolean(args[i+1]);
            }
            if (arg.equals("--rule-file")){
                this.ruleFile = args[i+1];
            }
            if (arg.equals("--order-file")){
                this.orderFile = args[i+1];
            }
            if (arg.equals("--minSupport")){
                this.minSupport = Double.parseDouble(args[i+1]);
            }
            if (arg.equals("--minConfidence")){
                this.minConfidence = Double.parseDouble(args[i+1]);
            }
            if (arg.equals("--max-depth")){
                this.depth = Integer.parseInt(args[i+1]);
            }
            if (arg.equals("--use-m-apriori")){
                this.useMApriori = Boolean.parseBoolean(args[i+1]);
            }
        }
        // check if there is a required argument missing
        if (Double.isNaN(this.minSupport) || Double.isNaN(this.minConfidence) || depth == -1 || this.ruleFile.isEmpty() || this.orderFile.isEmpty()){
            throw new ReadArgsException(Arrays.toString(args));
        }
    }

    public boolean getUseMApriori() {
        return useMApriori;
    }

    /**
     * Getter for the minimum support value from args input.
     *
     * @return the minimum support value
     */
    public double getMinSupport() {
        return minSupport;
    }

    /**
     * Getter for the minimum confidence value from args input.
     *
     * @return the minimum confidence value
     */
    public double getMinConfidence() {
        return minConfidence;
    }

    /**
     * Getter for the depth given as args input
     *
     * @return the depth value
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Getter for the ruleFile Path given as args input
     *
     * @return the rule file path
     */
    public String getRuleFile() {
        return ruleFile;
    }

    /**
     * Getter for the rule file name without the path.
     *
     * @return the rule file name without the path
     */
    public String getRuleFileWithoutPath(){
        int lastSlash = this.ruleFile.lastIndexOf('/');
        int lastPoint = this.ruleFile.lastIndexOf('.');
        return this.ruleFile.substring(lastSlash + 1, lastPoint);
    }

    /**
     * Getter for the oderFile Path given as args input
     *
     * @return the order file
     */
    public String getOrderFile() {
        return orderFile;
    }

    /**
     * Getter for the order file name without the path.
     *
     * @return the order file name without the path
     */
    public String getOderFileWithoutPath(){
        int lastSlash = this.orderFile.lastIndexOf('/');
        int lastPoint = this.orderFile.lastIndexOf('.');
        return this.orderFile.substring(lastSlash + 1, lastPoint);
    }

    /**
     * Getter for useSatSolver from args input
     *
     * @return should the satSolver be used
     */
    public boolean getUseSatSolver(){
        return this.useSatSolver;
    }
}