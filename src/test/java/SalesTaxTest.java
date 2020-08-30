import com.claranet.salestax.TextInputParser;
import com.claranet.salestax.entity.Item;
import com.claranet.salestax.Receipt;
import com.claranet.salestax.strategy.TaxStrategyConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SalesTaxTest {
    private static final String NON_IMPORTED_BASKET = "2 book at 12.49\n" +
                                          "1 music CD at 14.99\n" +
                                          "1 chocolate bar at 0.85";
    private static final String IMPORTED_BASKET = "1 imported box of chocolates at 10.00\n" +
                                          "1 imported bottle of perfume at 47.50";
    private static final String MIXED_BASKET = "1 imported bottle of perfume at 27.99\n" +
                                          "1 bottle of perfume at 18.99\n" +
                                          "1 packet of headache pills at 9.75\n" +
                                          "3 box of imported chocolates at 11.25";
    private static final String NON_IMPORTED_RECEIPT = "2 book: 24,98\n" +
                                                       "1 music CD: 16,49\n" +
                                                       "1 chocolate bar: 0,85\n" +
                                                       "Sales Taxes: 1,50\n" +
                                                       "Total: 42,32\n";
    private static final String IMPORTED_RECEIPT = "1 imported box of chocolates: 10,50\n" +
                                                   "1 imported bottle of perfume: 54,65\n" +
                                                   "Sales Taxes: 7,65\n" +
                                                   "Total: 65,15\n";
    private static final String MIXED_RECEIPT = "1 imported bottle of perfume: 32,19\n" +
                                                "1 bottle of perfume: 20,89\n" +
                                                "1 packet of headache pills: 9,75\n" +
                                                "3 box of imported chocolates: 35,55\n" +
                                                "Sales Taxes: 7,90\n" +
                                                "Total: 98,38\n";
    private static final double DELTA = 0.001;
    private static final double BOOK_TAX = 0.00;
    private static final double BOOK_TOTAL = 24.98;
    private static final double CD_TAX = 1.50;
    private static final double CD_TOTAL = 16.49;
    private static final double CHOCOLATE_TAX = 0.00;
    private static final double CHOCOLATE_TOTAL = 0.85;
    private static final double IMPORTED_CHOCOLATE1_TAX = 0.50;
    private static final double IMPORTED_CHOCOLATE1_TOTAL = 10.50;
    private static final double IMPORTED_PERFUME1_TAX = 7.15;
    private static final double IMPORTED_PERFUME1_TOTAL = 54.65;
    private static final double IMPORTED_PERFUME2_TAX = 4.20;
    private static final double IMPORTED_PERFUME2_TOTAL = 32.19;
    private static final double PERFUME_TAX = 1.90;
    private static final double PERFUME_TOTAL = 20.89;
    private static final double PILLS_TAX = 0.00;
    private static final double PILLS_TOTAL = 9.75;
    private static final double IMPORTED_CHOCOLATE2_TAX = 1.80;
    private static final double IMPORTED_CHOCOLATE2_TOTAL = 35.55;

    private TextInputParser parser;
    private TaxStrategyConfigurator taxConfigurator;
    private Receipt receipt;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        parser = new TextInputParser();
        taxConfigurator = new TaxStrategyConfigurator();
        receipt = new Receipt();
    }

    @Test
    public void whenNonImportedBasketIsUsed_taxShouldBeCorrectlyCalculatedForEachItem() {
        List<Item> basket = parser.convertStringBasketToItems(NON_IMPORTED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(BOOK_TAX, basket.get(0).calculateTax(), DELTA);
        assertEquals(CD_TAX, basket.get(1).calculateTax(), DELTA);
        assertEquals(CHOCOLATE_TAX, basket.get(2).calculateTax(), DELTA);
    }

    @Test
    public void whenImportedBasketIsUsed_taxShouldBeCorrectlyCalculatedForEachItem() {
        List<Item> basket = parser.convertStringBasketToItems(IMPORTED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(IMPORTED_CHOCOLATE1_TAX, basket.get(0).calculateTax(), DELTA);
        assertEquals(IMPORTED_PERFUME1_TAX, basket.get(1).calculateTax(), DELTA);
    }

    @Test
    public void whenMixedBasketIsUsed_taxShouldBeCorrectlyCalculatedForEachItem() {
        List<Item> basket = parser.convertStringBasketToItems(MIXED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(IMPORTED_PERFUME2_TAX, basket.get(0).calculateTax(), DELTA);
        assertEquals(PERFUME_TAX, basket.get(1).calculateTax(), DELTA);
        assertEquals(PILLS_TAX, basket.get(2).calculateTax(), DELTA);
        assertEquals(IMPORTED_CHOCOLATE2_TAX, basket.get(3).calculateTax(), DELTA);
    }

    @Test
    public void whenNonImportedBasketIsUsed_totalShouldBeCorrectlyCalculatedForEachItem() {
        List<Item> basket = parser.convertStringBasketToItems(NON_IMPORTED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(BOOK_TOTAL, basket.get(0).calculateTotal(), DELTA);
        assertEquals(CD_TOTAL, basket.get(1).calculateTotal(), DELTA);
        assertEquals(CHOCOLATE_TOTAL, basket.get(2).calculateTotal(), DELTA);
    }

    @Test
    public void whenImportedBasketIsUsed_totalShouldBeCorrectlyCalculatedForEachItem() {
        List<Item> basket = parser.convertStringBasketToItems(IMPORTED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(IMPORTED_CHOCOLATE1_TOTAL, basket.get(0).calculateTotal(), DELTA);
        assertEquals(IMPORTED_PERFUME1_TOTAL, basket.get(1).calculateTotal(), DELTA);
    }

    @Test
    public void whenMixedBasketIsUsed_totalShouldBeCorrectlyCalculatedForEachItem() {
        List<Item> basket = parser.convertStringBasketToItems(MIXED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(IMPORTED_PERFUME2_TOTAL, basket.get(0).calculateTotal(), DELTA);
        assertEquals(PERFUME_TOTAL, basket.get(1).calculateTotal(), DELTA);
        assertEquals(PILLS_TOTAL, basket.get(2).calculateTotal(), DELTA);
        assertEquals(IMPORTED_CHOCOLATE2_TOTAL, basket.get(3).calculateTotal(), DELTA);
    }

    @Test
    public void whenNonImportedBasketIsUsed_correctReceiptShouldBeGenerated() {
        List<Item> basket = parser.convertStringBasketToItems(NON_IMPORTED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(NON_IMPORTED_RECEIPT, receipt.generate(basket));
    }

    @Test
    public void whenImportedBasketIsUsed_correctReceiptShouldBeGenerated() {
        List<Item> basket = parser.convertStringBasketToItems(IMPORTED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(IMPORTED_RECEIPT, receipt.generate(basket));
    }

    @Test
    public void whenMixedBasketIsUsed_correctReceiptShouldBeGenerated() {
        List<Item> basket = parser.convertStringBasketToItems(MIXED_BASKET);
        taxConfigurator.configure(basket);
        assertEquals(MIXED_RECEIPT, receipt.generate(basket));
    }
}
