package Tema2;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.addCurrency("addcurrency EUR EUR 1.0");
        facade.setStoreCurrency("EUR");
        Scanner scanner = new Scanner(System.in);
        String input;
        while(true) {
            input = scanner.nextLine();
            String[] list = input.split(" ");
            try {
                if ("listcurrencies".equals(input))
                    facade.listCurrencies();
                else if ("getstorecurrency".equals(input))
                    facade.getStoreCurrency();
                else if ("addcurrency".equals(list[0]))
                    facade.addCurrency(input);
                else if ("loadcsv".equals(list[0]))
                    facade.loadCSV(list[1]);
                else if ("savecsv".equals(list[0]))
                    facade.saveCSV(list[1]);
                else if ("setstorecurrency".equals(list[0]))
                    facade.setStoreCurrency(list[1]);
                else if ("updateparity".equals(list[0]))
                    facade.updateParity(input);
                else if ("listproducts".equals(input))
                    facade.listProducts();
                else if ("showproduct".equals(list[0]))
                    facade.showProduct(list[1]);
                else if ("listmanufacturers".equals(input))
                    facade.listManufacturers();
                else if ("listproductsbymanufacturarer".equals(list[0]))
                    facade.listProductsByManufacturer(input);
                else if ("listdiscounts".equals(input))
                    facade.listDiscounts();
                else if ("addiscount".equals(list[0]))
                    facade.addDiscount(input);
                else if ("applydiscount".equals(list[0]))
                    facade.applyDiscount(input);
                else if ("calculatetotal".equals(list[0]))
                    facade.calculateTotal(input);
                else if ("savestore".equals(input))
                    facade.saveStore();
                else if ("loadstore".equals(input))
                    facade.loadStore();
                else if("exit".equals(input))
                    break;
                else if ("quit".equals(input))
                    break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
