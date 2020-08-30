package com.claranet.salestax.strategy;

import com.claranet.salestax.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TaxStrategyConfiguratorTest {
    private static final double DELTA = 0.001;
    private static final double ZERO_TAX = 0.00;
    private static final  double TEN_PERCENT_TAX = 1.90;

    private List<Item> basket;
    private TaxStrategyConfigurator strategyConfigurator;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        basket = createItemList();
        strategyConfigurator = new TaxStrategyConfigurator();
    }

    @Test
    void whenItemIsTaxFree_TaxFreeStrategyShouldBeConfigured() {
        strategyConfigurator.configure(basket);
        assertEquals(ZERO_TAX, basket.get(0).calculateTax(), DELTA);
    }

    @Test
    void whenItemHasTenPercentTax_PercentBasedStrategyShouldBeConfiguredWithTenPercentTax() {
        strategyConfigurator.configure(basket);
        assertEquals(TEN_PERCENT_TAX, basket.get(1).calculateTax(), DELTA);
    }

    private List<Item> createItemList() {
        Item taxFreeItem = new Item(1, "tax free book item", false, 5.00);
        Item itemWithTenPercentTax = new Item(1, "item with 10% tax", false, 18.99);
        return Arrays.asList(taxFreeItem, itemWithTenPercentTax);
    }
}