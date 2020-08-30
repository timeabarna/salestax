package com.claranet.salestax.strategy;

public class TaxFreeStrategy extends PercentBasedTaxStrategy implements TaxStrategy {
    public TaxFreeStrategy() {
        super(0);
    }
}
