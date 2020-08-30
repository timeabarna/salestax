package com.claranet.salestax;

import com.claranet.salestax.entity.Item;
import com.claranet.salestax.strategy.TaxStrategyConfigurator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class SalesTaxMain {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String NON_IMPORTED_BASKET = "2 book at 12.49\n" +
                                     "1 music CD at 14.99\n" +
                                     "1 chocolate bar at 0.85";
        String IMPORTED_BASKET = "1 imported box of chocolates at 10.00\n" +
                                 "1 imported bottle of perfume at 47.50";
        String MIXED_BASKET = "1 imported bottle of perfume at 27.99\n" +
                              "1 bottle of perfume at 18.99\n" +
                              "1 packet of headache pills at 9.75\n" +
                              "3 box of imported chocolates at 11.25";

        TextInputParser parser = new TextInputParser();
        TaxStrategyConfigurator configurator = new TaxStrategyConfigurator();
        Receipt receipt = new Receipt();

        System.out.println(generateReceiptFor(NON_IMPORTED_BASKET, parser, configurator, receipt));
        System.out.println(generateReceiptFor(IMPORTED_BASKET, parser, configurator, receipt));
        System.out.println(generateReceiptFor(MIXED_BASKET, parser, configurator, receipt));

    }

    private static String generateReceiptFor(String basket, TextInputParser parser,
                                             TaxStrategyConfigurator configurator, Receipt receipt) {
        List<Item> items = parser.convertStringBasketToItems(basket);
        configurator.configure(items);
        return receipt.generate(items);
    }
}
