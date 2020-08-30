package com.claranet.salestax.strategy;

import com.claranet.salestax.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaxFreeStrategyTest {
    private static final double DELTA = 0.001;
    private static final double TAX_FREE_ITEM_TAX = 0.00;
    private static final double IMPORTED_TAX_FREE_ITEM_TAX = 0.50;
    private static final double TAX_FREE_ITEM_TOTAL = 1.70;
    private static final double IMPORTED_TAX_FREE_ITEM_TOTAL = 10.50;

    private Item taxFreeItem;
    private Item importedTaxFreeItem;

    @BeforeEach
    void setUp() {
        taxFreeItem = new Item(2, "tax free item", false, 0.85);
        taxFreeItem.setTaxStrategy(new TaxFreeStrategy());

        importedTaxFreeItem = new Item(1, "imported tax free item", true, 10.00);
        importedTaxFreeItem.setTaxStrategy(new TaxFreeStrategy());
    }

    @Test
    public void whenNonImportedItemCalculated_taxShouldBeZero() {
        assertEquals(TAX_FREE_ITEM_TAX, taxFreeItem.calculateTax(), DELTA);
    }

    @Test
    public void whenImportedItemCalculated_taxShouldBeFivePercent() {
        assertEquals(IMPORTED_TAX_FREE_ITEM_TAX, importedTaxFreeItem.calculateTax(), DELTA);
    }

    @Test
    public void whenNonImportedItemCalculated_totalShouldEqualToTotal() {
        assertEquals(TAX_FREE_ITEM_TOTAL, taxFreeItem.calculateTotal(), DELTA);
    }

    @Test
    public void whenImportedItemCalculated_totalShouldIncludeTax() {
        assertEquals(IMPORTED_TAX_FREE_ITEM_TOTAL, importedTaxFreeItem.calculateTotal(), DELTA);
    }
}