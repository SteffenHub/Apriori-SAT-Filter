package args;

import java.util.Arrays;

public class ArgsInput {

    private double minSupport;
    private double minConfidence;
    private int depth;

    public ArgsInput(String[] args) throws ReadArgsException {
        this.minSupport = Double.NaN;
        this.minConfidence = Double.NaN;
        this.depth = -1;
        this.readArgs(args);
    }

    private void readArgs(String[] args) throws ReadArgsException {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--minSupport")){
                this.minSupport = Double.parseDouble(args[i+1]);
            }
            if (arg.equals("--minConfidence")){
                this.minConfidence = Double.parseDouble(args[i+1]);
            }
            if (arg.equals("--depth")){
                this.depth = Integer.parseInt(args[i+1]);
            }
        }
        if (Double.isNaN(this.minSupport) || Double.isNaN(this.minConfidence) || depth == -1){
            throw new ReadArgsException(Arrays.toString(args));
        }
    }

    public double getMinSupport() {
        return minSupport;
    }

    public double getMinConfidence() {
        return minConfidence;
    }

    public int getDepth() {
        return depth;
    }
}