package Tema2;

import java.io.*;
import java.util.ArrayList;
import org.apache.commons.csv.*;

public class Store implements Serializable {
    private static Store instance = null;
    private String name;
    private Currency current;
    private final ArrayList<Product> products;
    private final ArrayList<Manufacturer> manufacturers;
    private final ArrayList<Currency> currencies;
    private final ArrayList<Discount> discounts;

    private Store() {
        this.products = new ArrayList<>();
        this.manufacturers = new ArrayList<>();
        this.currencies = new ArrayList<>();
        this.discounts = new ArrayList<>();
    }
    public static Store getInstance() {
        if(instance == null)
            instance = new Store();
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrent() {
        return current;
    }

    public void setCurrent(Currency currency) {
        this.current = currency;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public ArrayList<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    private float formatPrice(String price) {
        if ("".equals(price))
            return 0f;
        if(price.indexOf('-') != -1) {
            StringBuilder stringBuilder = new StringBuilder(price);
            stringBuilder.delete(price.indexOf('-'), stringBuilder.length());
            price = stringBuilder.toString();
        }
        price = price.replaceAll("[^0-9.]", "");
        return (float) Double.parseDouble(price);
    }

    private int formatQuantity(String quantity) {
        if ("".equals(quantity))
            return 0;
        quantity = quantity.replaceAll("[^0-9.]", "");
        return Integer.parseInt(quantity);
    }

    private Currency findCurrency(String price) {
        for (var currency : currencies)
            if (price.contains(currency.getSymbol()))
                return currency;
        throw new CurrencyNotFoundException();
    }

    public void readCSV(String filename) throws IOException {
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .parse(new FileReader(filename));
        Currency currency = null;
        boolean first = true;
        for (CSVRecord csvRecord : records) {
            if(first) {
                currency = findCurrency(csvRecord.get(3));
                first = false;
            }
            String uniqeId = csvRecord.get(0);
            String productName = csvRecord.get(1);
            String manufacturer_name = csvRecord.get(2);
            if ("".equals(manufacturer_name))
                manufacturer_name = "Not Available";
            float price = formatPrice(csvRecord.get(3));
            if (price == 0f)
                continue;
            int quantity = formatQuantity(csvRecord.get(4));

            Manufacturer manufacturer = new Manufacturer(manufacturer_name);
            manufacturer = addManufacturer(manufacturer);
            manufacturer.setCountProducts(manufacturer.getCountProducts() + 1);

            ProductBuilder builder = new ProductBuilder();
            Product product = builder.withUniqueId(uniqeId)
                    .withName(productName)
                    .withManufacturer(manufacturer)
                    .withPrice(price)
                    .withQuantity(quantity)
                    .build();
            addProduct(product);
        }
        if(!current.equals(currency))
            changePrice(currency.getParityToEur(), current.getParityToEur());
    }

    public void writeCSV(String filename) throws IOException {
        CSVPrinter printer = new CSVPrinter(new FileWriter(filename),
                CSVFormat.DEFAULT);
        printer.printRecord("uniq_id", "product_name", "manufacturer",
                "price", "number_available_in_stock");
        for (Product product : products)
            printer.printRecord(product.getUniqueId(), product.getName(),
                    product.getManufacturer().getName(),
                    current.getSymbol() + product.getPrice(),
                    Integer.toString(product.getQuantity()));
        printer.flush();
    }

    public void saveStore(String filename) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(this);
        out.close();
    }

    public void loadStore(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);
        instance = (Store) in.readObject();
        in.close();
    }

    public void addProduct(Product product) throws ProductNotFoundException,
            DuplicateProductException {
        if(product == null)
            throw new ProductNotFoundException();
        for(Product prod : products) {
            if(prod.equals(product))
                throw new DuplicateProductException();
        }
        products.add(product);
    }

    public Manufacturer addManufacturer(Manufacturer manufacturer) throws
            ManufacturerNotFoundException {
        if(manufacturer == null)
            throw new ManufacturerNotFoundException();
        for(Manufacturer man : manufacturers) {
            if(man.equals(manufacturer))
                return man;
        }
        manufacturers.add(manufacturer);
        return manufacturer;
    }

    public ArrayList<Product> getProductsByManufacturer(Manufacturer manufacturer)
            throws ManufacturerNotFoundException {
        if(manufacturer == null)
            throw new ManufacturerNotFoundException();
        ArrayList<Product> result = new ArrayList<>();
        for(Product product : this.products) {
            if(product.getManufacturer().equals(manufacturer))
                result.add(product);
        }
        return result;
    }

    private void changePrice(double old_parity, double new_parity) {
        for (Product product : this.products)
            product.setPrice(product.getPrice() * old_parity / new_parity);
    }

    public void changeCurrency(Currency currency) throws
            CurrencyNotFoundException {
        if(currency == null)
            throw new CurrencyNotFoundException();
        changePrice(this.getCurrent().getParityToEur(),
                currency.getParityToEur());
        this.setCurrent(currency);
    }

    public void applyDiscount(Discount discount) throws
            DiscountNotFoundException, NegativePriceException {
        if(discount == null)
            throw new DiscountNotFoundException();
        discount.setAsAppliedNow();
        for(Product product : this.products) {
            if(discount.getDiscountType() == DiscountType.FIXED_DISCOUNT &&
            product.getPrice() < discount.getValue())
                throw new NegativePriceException();
            else {
                if(discount.getDiscountType() == DiscountType.FIXED_DISCOUNT)
                    product.setPrice(product.getPrice() - discount.getValue());
                else
                    product.setPrice(product.getPrice() * (1 -
                            discount.getValue() / 100));
            }
        }
    }
}
