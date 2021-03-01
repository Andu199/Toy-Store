package Tema2;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Discount implements Serializable {
    private String name;
    private DiscountType discountType;
    private double value;
    private LocalDateTime lastDateApplied = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getLastDateApplied() {
        return lastDateApplied;
    }

    public void setLastDateApplied(LocalDateTime lastDateApplied) {
        this.lastDateApplied = lastDateApplied;
    }

    public void setAsAppliedNow() {
        this.setLastDateApplied(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount)) return false;
        Discount discount = (Discount) o;
        return Double.compare(discount.getValue(), getValue()) == 0 && getDiscountType() == discount.getDiscountType();
    }
}
