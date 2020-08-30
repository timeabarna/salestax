package com.claranet.salestax.strategy;

public interface TaxStrategy {
    double calculateTax(int pieces, double price, boolean imported);
    double calculateTotal(int pieces, double price, boolean imported);
}
