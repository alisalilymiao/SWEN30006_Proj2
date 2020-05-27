package game;

public class QuantityAnomalyException extends Exception
{
    public QuantityAnomalyException(){
        super("The total number of people playing the game should be four");
    }
}
