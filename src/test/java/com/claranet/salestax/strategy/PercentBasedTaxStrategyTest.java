package com.claranet.salestax.strategy;

import com.claranet.salestax.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PercentBasedTaxStrategyTest {

    private static final double DELTA = 0.001;
    private static final double ITEM_WITH_TEN_PERCENT_TAX = 0.10;
    private static final double IMPORTED_ITEM_WITH_TEN_PERCENT_TAX = 1.50;
    private static final double ITEM_WITH_TEN_PERCENT_TOTAL = 1.1;
    private static final double IMPORTED_ITEM_WITH_TEN_PERCENT_TOTAL = 11.50;

    private Item itemWithTenPercentTax;
    private Item importedItemWithTenPercentTax;

    @BeforeEach
    void setUp() {
        itemWithTenPercentTax = new Item(1, "item with 10% tax", false, 1.00);
        itemWithTenPercentTax.setTaxStrategy(new PercentBasedTaxStrategy(10));

        importedItemWithTenPercentTax = new Item(1, "imported item with 10% tax", true, 10.00);
        importedItemWithTenPercentTax.setTaxStrategy(new PercentBasedTaxStrategy(10));
    }

    @Test
    public void whenNonImportedItemCalculated_taxShouldBeZero() {
        assertEquals(ITEM_WITH_TEN_PERCENT_TAX, itemWithTenPercentTax.calculateTax(), DELTA);
    }

    @Test
    public void whenImportedItemCalculated_taxShouldBeFivePercent() {
        assertEquals(IMPORTED_ITEM_WITH_TEN_PERCENT_TAX, importedItemWithTenPercentTax.calculateTax(), DELTA);
    }

    @Test
    public void whenNonImportedItemCalculated_totalShouldEqualToTotal() {
        assertEquals(ITEM_WITH_TEN_PERCENT_TOTAL, itemWithTenPercentTax.calculateTotal(), DELTA);
    }

    @Test
    public void whenImportedItemCalculated_totalShouldIncludeTax() {
        assertEquals(IMPORTED_ITEM_WITH_TEN_PERCENT_TOTAL, importedItemWithTenPercentTax.calculateTotal(), DELTA);
    }
}