package com.claranet.salestax.strategy;

import com.claranet.salestax.entity.Item;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class TaxStrategyConfigurator {
    private final List<String> taxFreeItems;

    public TaxStrategyConfigurator() throws IOException, URISyntaxException {
        this.taxFreeItems = init();
    }

    private List<String> init() throws URISyntaxException, IOException {
        return Files.lines(Paths.get(getClass()
                .getClassLoader()
                .getResource("tax_configuration.cfg")
                .toURI()))
                .collect(toList());
    }

    public void configure(List<Item> items) {
        items.forEach(item -> {
            if (isTaxFree(item)) {
                item.setTaxStrategy(new TaxFreeStrategy());
            }
            else {
                item.setTaxStrategy(new PercentBasedTaxStrategy(10));
            }
        });
    }

    private boolean isTaxFree(Item item) {
        return taxFreeItems.stream().anyMatch(taxFreeItem -> item.getDescription().contains(taxFreeItem));
    }
}
