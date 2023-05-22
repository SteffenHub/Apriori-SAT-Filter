package args;

public class ReadArgsException extends Exception{
    public ReadArgsException(String args){
        super("Not enough parameter passed: " + args + "\n" + "Example: --minSupport 0.8 --minConfidence 0.8 --depth 2 --caching false");
    }
}