package com.claranet.salestax.entity;

import com.claranet.salestax.strategy.TaxStrategy;
import java.util.Objects;

public class Item {
    private final int pieces;
    private final String description;
    private final boolean imported;
    private final double price;
    private TaxStrategy taxStrategy;

    public Item(int pieces, String description, boolean imported, double price) {
        this.pieces = pieces;
        this.description = description;
        this.imported = imported;
        this.price = price;
    }

    public int getPieces() {
        return pieces;
    }

    public String getDescription() {
        return description;
    }

    public void setTaxStrategy(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    public double calculateTax() {
        return taxStrategy.calculateTax(pieces, price, imported);
    }

    public double calculateTotal() {
        return taxStrategy.calculateTotal(pieces, price, imported);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return pieces == item.pieces &&
                imported == item.imported &&
                Double.compare(item.price, price) == 0 &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces, description, imported, price);
    }
}
