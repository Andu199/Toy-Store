package Tema2;

public class NegativePriceException extends RuntimeException{
    public NegativePriceException() {
        super("Negative Price");
    }
}
