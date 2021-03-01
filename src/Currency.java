package Tema2;

import java.io.Serializable;
import java.util.Objects;

public class Currency implements Serializable {
    private String name;
    private String symbol;
    private double parityToEur;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getParityToEur() {
        return parityToEur;
    }

    public void setParityToEur(double parityToEur) {
        this.parityToEur = parityToEur;
    }

    public void updateParity(double parityToEur) {
        this.setParityToEur(parityToEur);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return Objects.equals(getName(), currency.getName()) && Objects.equals(getSymbol(), currency.getSymbol());
    }
}
