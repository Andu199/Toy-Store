package Tema2;

public class CurrencyNotFoundException extends RuntimeException{
    public CurrencyNotFoundException() {
        super("Currency Not Found");
    }
}
