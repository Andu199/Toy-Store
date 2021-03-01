package Tema2;

public class ManufacturerNotFoundException extends RuntimeException {
    public ManufacturerNotFoundException() {
        super("Manufacturer Not Found");
    }
}
