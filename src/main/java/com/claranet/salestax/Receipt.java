package com.claranet.salestax;

import com.claranet.salestax.entity.Item;
import java.util.List;

public class Receipt {

    public String generate(List<Item> items) {
        StringBuilder receipt = new StringBuilder();
        items.forEach(item -> receipt.append(itemLines(item)));
        receipt.append(taxes(items));
        receipt.append(totals(items));
        return receipt.toString();
    }

    private String itemLines(Item item) {
        return String.format("%d %s: %.2f\n", item.getPieces(), item.getDescription(), item.calculateTotal());
    }

    private String taxes(List<Item> items) {
        return String.format("Sales Taxes: %.2f\n", sumSalesTaxes(items));
    }

    private String totals(List<Item> items) {
        return String.format("Total: %.2f\n", sumTotal(items));
    }

    private double sumSalesTaxes(List<Item> items) {
        return items.stream().mapToDouble(Item::calculateTax).sum();
    }

    private double sumTotal(List<Item> items) {
        return items.stream().mapToDouble(Item::calculateTotal).sum();
    }
}
