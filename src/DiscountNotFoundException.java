package Tema2;

public class DiscountNotFoundException extends RuntimeException{
    public DiscountNotFoundException() {
        super("Discount Not Found");
    }
}
