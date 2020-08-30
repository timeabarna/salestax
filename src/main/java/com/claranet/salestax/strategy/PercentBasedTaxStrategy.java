package com.claranet.salestax.strategy;

import java.math.BigDecimal;

public class PercentBasedTaxStrategy implements TaxStrategy {
    private static final int IMPORT_TAX = 5;
    private final int taxPercentage;
    private double calculatedTax = -1;

    public PercentBasedTaxStrategy(int taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @Override
    public double calculateTax(int pieces, double price, boolean imported) {
        if (imported) {
            calculatedTax = pieces * (round(price / 100 * (taxPercentage + IMPORT_TAX)));
        }
        else {
            calculatedTax = pieces * (round(price / 100 * taxPercentage));
        }
        return calculatedTax;
    }

    @Override
    public double calculateTotal(int pieces, double price, boolean imported) {
        if (calculatedTax < 0) {
            return pieces * price + calculateTax(pieces, price, imported);
        }
        return pieces * price + calculatedTax;
    }

    private double round(double amount) {
        amount = roundUpToTwoDigits(amount);
        int lastDigit = getLastDigit(amount);
        return amount + roundToNearest05(lastDigit);
    }

    private double roundUpToTwoDigits(double amount) {
        BigDecimal number = new BigDecimal(Double.toString(amount));
        return number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private int getLastDigit(double amount) {
        return (int) (amount * 100) % 10;
    }

    private double roundToNearest05(int lastDigit) {
        double diff = 5 - lastDigit;
        if (diff == 0 || diff == 5) {
            return 0;
        }
        else if (diff < 0) {
            return (5 + diff) / 100.0;
        }
        else {
            return diff / 100.0;
        }
    }
}
