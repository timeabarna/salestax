package com.claranet.salestax;

import com.claranet.salestax.entity.Item;
import com.claranet.salestax.strategy.PercentBasedTaxStrategy;
import com.claranet.salestax.strategy.TaxFreeStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {
    private static final String RECEIPT = "2 tax free item1: 24,98\n" +
                                          "1 item with 10% tax: 16,49\n" +
                                          "1 tax free item2: 0,85\n" +
                                          "Sales Taxes: 1,50\n" +
                                          "Total: 42,32\n";

    private List<Item> basket;
    private Receipt receipt;

    @BeforeEach
    void setUp() {
        basket = createItemList();
        receipt = new Receipt();
    }

    @Test
    public void whenGenerated_correctReceiptShouldBeGenerated() {
        assertEquals(RECEIPT, receipt.generate(basket));
    }

    private List<Item> createItemList() {
        Item taxFreeItem1 = new Item(2, "tax free item1", false, 12.49);
        taxFreeItem1.setTaxStrategy(new TaxFreeStrategy());

        Item itemWithTenPercentTax = new Item(1, "item with 10% tax", false, 14.99);
        itemWithTenPercentTax.setTaxStrategy(new PercentBasedTaxStrategy(10));

        Item taxFreeItem2 = new Item(1, "tax free item2", false, 0.85);
        taxFreeItem2.setTaxStrategy(new TaxFreeStrategy());

        return Arrays.asList(taxFreeItem1, itemWithTenPercentTax, taxFreeItem2);
    }
}