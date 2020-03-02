package tech.ippon.domain;

import java.math.BigDecimal;

public class Food {
    public String name;
    public BigDecimal price;

    public Food() {
        
    }

    public Food(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.price + ")";
    }
}
