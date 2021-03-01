package Tema2;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Facade {
    public void listCurrencies() {
        Store store = Store.getInstance();
        for (var currency : store.getCurrencies())
            System.out.println(currency.getName() + " " + currency.getParityToEur());
    }

    public void getStoreCurrency() {
        Store store = Store.getInstance();
        System.out.println(store.getCurrent().getName());
    }

    public void addCurrency(String input) {
        Store store = Store.getInstance();
        if(input == null)
            throw new CurrencyNotFoundException();
        String[] list = input.split(" ");
        Currency currency = new Currency();
        currency.setName(list[1]);
        currency.setSymbol(list[2]);
        currency.setParityToEur(Double.parseDouble(list[3]));
        store.getCurrencies().add(currency);
    }

    public void loadCSV(String filename) {
        Store store = Store.getInstance();
        try {
            store.readCSV(filename);
        } catch(IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void saveCSV(String filename) {
        Store store = Store.getInstance();
        try {
            store.writeCSV(filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void setStoreCurrency(String name) throws CurrencyNotFoundException {
        Store store = Store.getInstance();
        if(name == null)
            throw new CurrencyNotFoundException();
        for (var currency : store.getCurrencies())
            if (currency.getName().equals(name)) {
                if(store.getCurrent() != null)
                    store.changeCurrency(currency);
                store.setCurrent(currency);
                return;
            }
        throw new CurrencyNotFoundException();
    }

    public void updateParity(String input) throws CurrencyNotFoundException {
        Store store = Store.getInstance();
        String[] list = input.split(" ");
        for (var currency : store.getCurrencies())
            if (currency.getName().equals(list[1])) {
                currency.updateParity(Double.parseDouble(list[2]));
                return;
            }
        throw new CurrencyNotFoundException();
    }
    
    private void printProduct(Product product) {
        Store store = Store.getInstance();
        System.out.println(product.getUniqueId() + "," + product.getName()
                + "," + product.getManufacturer().getName() + "," +
                store.getCurrent().getSymbol() +
                new DecimalFormat("0.00").format(product.getPrice()) + "," +
                product.getQuantity());
    }

    public void listProducts() {
        Store store = Store.getInstance();
        for (var product : store.getProducts())
            printProduct(product);
    }

    public void showProduct(String uniqueID) throws ProductNotFoundException {
        Store store = Store.getInstance();
        for (var product : store.getProducts())
            if(product.getUniqueId().equals(uniqueID)) {
                printProduct(product);
                return;
            }
        throw new ProductNotFoundException();
    }

    public void listManufacturers() {
        Store store = Store.getInstance();
        for(var manufacturers : store.getManufacturers())
            System.out.println(manufacturers.getName());
    }

    public void listProductsByManufacturer(String input) throws ManufacturerNotFoundException {
        Store store = Store.getInstance();
        String[] list = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < list.length; ++i)
            sb.append(list[i]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        String name = sb.toString();
        Manufacturer manufacturer = new Manufacturer(name);
        ArrayList<Product> products =
                store.getProductsByManufacturer(manufacturer);
        if (products.size() == 0)
            throw new ManufacturerNotFoundException();
        for (Product product : products)
            printProduct(product);
    }

    public void listDiscounts() {
        Store store = Store.getInstance();
        for (var discount : store.getDiscounts())
            System.out.println(discount.getDiscountType() + " " +
                    discount.getValue() + " " +
                    discount.getName() + " " + discount.getLastDateApplied());
    }

    public void addDiscount (String input) {
        String[] list = input.split(" ");
        Discount discount = new Discount();
        if("PERCENTAGE".equals(list[1]))
            discount.setDiscountType(DiscountType.PERCENTAGE_DISCOUNT);
        else
            discount.setDiscountType(DiscountType.FIXED_DISCOUNT);
        discount.setValue(Double.parseDouble(list[2]));
        StringBuilder name = new StringBuilder(list[3]);
        for (int index = 4; index < list.length; ++index)
            name.append(" ").append(list[index]);
        discount.setName(name.toString());
        Store store = Store.getInstance();
        store.getDiscounts().add(discount);
    }

    public void applyDiscount(String input) throws DiscountNotFoundException {
        Store store = Store.getInstance();
        String[] list = input.split(" ");
        Discount discount = new Discount();
        discount.setValue(Double.parseDouble(list[2]));
        if("PERCENTAGE".equals(list[1]))
            discount.setDiscountType(DiscountType.PERCENTAGE_DISCOUNT);
        else
            discount.setDiscountType(DiscountType.FIXED_DISCOUNT);
        for(var disc : store.getDiscounts())
            if(disc.equals(discount)) {
                store.applyDiscount(disc);
                return;
            }
        throw new DiscountNotFoundException();
    }

    public void calculateTotal(String input) throws ProductNotFoundException {
        String[] list = input.split(" ");
        Store store = Store.getInstance();
        float sum = 0;
        boolean found;
        for (int i = 1; i < list.length; ++i) {
            found = false;
            for (var product : store.getProducts())
                if (product.getUniqueId().equals(list[i])) {
                    sum += product.getPrice();
                    found = true;
                    break;
                }
            if(!found)
                throw new ProductNotFoundException();
        }
        System.out.println(store.getCurrent().getSymbol() +
                new DecimalFormat("0.00").format(sum));
    }

    public void saveStore() {
        Store store = Store.getInstance();
        try {
            store.saveStore("store.ser");
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }

    public void loadStore() {
        Store store = Store.getInstance();
        try {
            store.loadStore("store.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }
}
