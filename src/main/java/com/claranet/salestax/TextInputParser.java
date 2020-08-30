package com.claranet.salestax;

import com.claranet.salestax.entity.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInputParser {
    private Pattern itemPattern = Pattern.compile("^(?<pieces>\\d+)" +
                                                                     "(?<descseparator> )" +
                                                                     "(?<description>.+)" +
                                                                     "(?<priceseparator> at )" +
                                                                     "(?<price>\\d+\\.\\d{2})$",
                                                                     Pattern.MULTILINE);


    public TextInputParser() {
    }

    public TextInputParser(Pattern pattern)  {
        itemPattern = pattern;
    }

    public List<Item> convertStringBasketToItems(String basket) {
        validate(basket);
        return createItemList(basket);
    }

    private void validate(String basket) {
        if (null == basket || basket.isEmpty()) {
            throw new IllegalArgumentException("Basket can not be empty");
        }
    }

    private List<Item> createItemList(String basket) {
        List<Item> items = new ArrayList<>();
        basket = basket.trim();
        final Matcher itemMatcher = itemPattern.matcher(basket);
        while (itemMatcher.find()) {
            items.add(createItem(itemMatcher));
        }
        return items;
    }

    private Item createItem(Matcher itemMatcher) {
        return new Item(
                Integer.parseInt(itemMatcher.group("pieces")),
                itemMatcher.group("description"),
                itemMatcher.group("description").contains("imported"),
                Double.parseDouble(itemMatcher.group("price")));
    }
}
