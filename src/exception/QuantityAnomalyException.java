package exception;

/**
 * An exception thrown when the number of players over 4
 */
public class QuantityAnomalyException extends Exception {
    public QuantityAnomalyException(){
        super("The total number of people playing the exception should be four");
    }
}
