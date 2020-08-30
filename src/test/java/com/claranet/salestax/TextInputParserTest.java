package com.claranet.salestax;

import com.claranet.salestax.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class TextInputParserTest {
    private static final String BASKET = "1 imported taxed item at 27.99\n" +
                                         "1 taxed item at 18.99\n" +
                                         "1 tax free item at 0.85\n" +
                                         "3 imported tax free item at 11.25";
    private static final String NEGATIVE_PIECES_BASKET = "1 taxed item at 18.99\n" +
                                                         "-5 tax free item at 0.85";
    private static final String NEGATIVE_PRICE_BASKET = "1 taxed item at -14.99\n" +
                                                        "1 tax free item at 0.85";
    private static final String BASKET_WITH_INCORRECT_ITEMS = "taxed item at 14.99\n" +
                                                              "1 tax free item at 0.85\n" +
                                                              "1 taxed item at \n" +
                                                              "1  at 1.88\n" +
                                                              "1 imported tax free item at 15\n" +
                                                              "1 tax free item at 12.858\n" +
                                                              "3.8 imported taxed item at 17.01";
    private static final String BASKET_WITH_DIFFERENT_FORMAT = "1 imported taxed item for 27.99 USD\n" +
                                                               "1 taxed item for 18.99 USD\n" +
                                                               "1 tax free item for 0.85 USD\n" +
                                                               "3 imported tax free item for 11.25 USD";

    private static final Item IMPORTED_TAXED_ITEM = new Item(1, "imported taxed item", true, 27.99);
    private static final Item TAXED_ITEM = new Item(1, "taxed item", false, 18.99);
    private static final Item TAX_FREE_ITEM = new Item(1, "tax free item", false, 0.85);
    private static final Item IMPORTED_TAX_FREE_ITEM = new Item(3, "imported tax free item", true, 11.25);
    private static final Item ITEM_WITH_NEGATIVE_PIECES = new Item(-5, "tax free item", false, 0.85);
    private static final Item ITEM_WITH_NEGATIVE_PRICE = new Item(1, "taxed item", false, -14.99);
    private static final Pattern differentPattern = Pattern.compile("^(?<pieces>\\d+)" +
                                                        "(?<descseparator> )" +
                                                        "(?<description>.+)" +
                                                        "(?<priceseparator> for )" +
                                                        "(?<price>\\d+\\.\\d{2}) USD$",
                                                        Pattern.MULTILINE);

    private TextInputParser parser;

    @BeforeEach
    void setUp() {
        parser = new TextInputParser();
    }

    @Test
    public void whenInputsNull_validationShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> parser.convertStringBasketToItems(null));
    }

    @Test
    public void whenInputsEmpty_validationShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> parser.convertStringBasketToItems(""));
    }

    @Test
    public void whenItemHasNegativePieces_ShouldNotBeAddedToList() {
        List<Item> basket = parser.convertStringBasketToItems(NEGATIVE_PIECES_BASKET);
        assertEquals(1, basket.size());
        assertFalse(basket.contains(ITEM_WITH_NEGATIVE_PIECES));
        assertTrue(basket.contains(TAXED_ITEM));
    }

    @Test
    public void whenItemHasNegativePrice_ShouldNotBeAddedToList() {
        List<Item> basket = parser.convertStringBasketToItems(NEGATIVE_PRICE_BASKET);
        assertEquals(1, basket.size());
        assertFalse(basket.contains(ITEM_WITH_NEGATIVE_PRICE));
        assertTrue(basket.contains(TAX_FREE_ITEM));
    }

    @Test
    public void whenItemHasNoPieces_ShouldNotBeAddedToList() {
        List<Item> basket = parser.convertStringBasketToItems(BASKET_WITH_INCORRECT_ITEMS);
        assertEquals(1, basket.size());
        assertTrue(basket.contains(TAX_FREE_ITEM));
    }

    @Test
    public void whenItemHasNoPrice_ShouldNotBeAddedToList() {
        List<Item> basket = parser.convertStringBasketToItems(BASKET_WITH_INCORRECT_ITEMS);
        assertEquals(1, basket.size());
        assertTrue(basket.contains(TAX_FREE_ITEM));
    }

    @Test
    public void whenItemHasNoDescription_ShouldNotBeAddedToList() {
        List<Item> basket = parser.convertStringBasketToItems(BASKET_WITH_INCORRECT_ITEMS);
        assertEquals(1, basket.size());
        assertTrue(basket.contains(TAX_FREE_ITEM));
    }

    @Test
    public void whenItemHasPriceWithoutDecimalSeparator_ShouldNotBeAddedToList() {
        List<Item> basket = parser.convertStringBasketToItems(BASKET_WITH_INCORRECT_ITEMS);
        assertEquals(1, basket.size());
        assertTrue(basket.contains(TAX_FREE_ITEM));
    }

    @Test
    public void whenItemHasPriceWithMoreThanTwoDecimals_ShouldNotBeAddedToList() {
        List<Item> basket = parser.convertStringBasketToItems(BASKET_WITH_INCORRECT_ITEMS);
        assertEquals(1, basket.size());
        assertTrue(basket.contains(TAX_FREE_ITEM));
    }

    @Test
    public void whenItemHasPiecesWithNotAWholeNumber_ShouldNotBeAddedToList() {
        List<Item> basket = parser.convertStringBasketToItems(BASKET_WITH_INCORRECT_ITEMS);
        assertEquals(1, basket.size());
        assertTrue(basket.contains(TAX_FREE_ITEM));
    }

    @Test
    public void whenConverted_itemListShouldContainFourItems() {
        List<Item> basket = parser.convertStringBasketToItems(BASKET);
        assertEquals(4, basket.size());
    }

    @Test
    public void whenConverted_itemListShouldContainTaxFreeImportedTaxFreeTaxedAndImportedTaxedItems() {
        List<Item> basket = parser.convertStringBasketToItems(BASKET);
        assertTrue(basket.contains(IMPORTED_TAXED_ITEM));
        assertTrue(basket.contains(TAXED_ITEM));
        assertTrue(basket.contains(TAX_FREE_ITEM));
        assertTrue(basket.contains(IMPORTED_TAX_FREE_ITEM));
    }

    @Test
    public void whenInputFormatIsChanged_usingCustomizedPatternShouldCreateItemsList() {
        TextInputParser differentParser = new TextInputParser(differentPattern);
        List<Item> basket = differentParser.convertStringBasketToItems(BASKET_WITH_DIFFERENT_FORMAT);
        assertTrue(basket.contains(IMPORTED_TAXED_ITEM));
        assertTrue(basket.contains(TAXED_ITEM));
        assertTrue(basket.contains(TAX_FREE_ITEM));
        assertTrue(basket.contains(IMPORTED_TAX_FREE_ITEM));
    }
}