import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.*;


public class ConverterTest {
    
    private Converter converter;
    private HashMap<String, Double> currencies;

    @BeforeEach
    public void setUp() throws Exception {
        currencies = new HashMap<>();
        currencies.put("USD", 3.75);
        currencies.put("GBP", 5.15);
        currencies.put("CHF", 3.85);
        currencies.put("EUR", 4.75);
        converter = new Converter(currencies);
    }

    @Test
    @DisplayName("Test przeliczania wskazanej kwoty i waluty")
    public void testGetConversion() {
        assertEquals(3750d, converter.getConversion(1000d, "USD"), "Przeliczenie \'złotówki na dolary\'");
        assertEquals(5150d, converter.getConversion(1000d, "GBP"), "Przeliczenie \'złotówki na funty\'");
        assertEquals(3850d, converter.getConversion(1000d, "CHF"), "Przeliczenie \'złotówki na franki\'");
        assertEquals(4750d, converter.getConversion(1000d, "EUR"), "Przeliczenie \'złotówki na euro\'");
    }

    @Test
    @DisplayName("Test z przeliczeniem zera i kwoty ujemnej")
    public void testGetConversionWithUnusualInput() {
        assertEquals(0d, converter.getConversion(0d, "USD"), "Przeliczenie 0PLN na dolary");
        assertEquals(-3750d, converter.getConversion(-1000d, "USD"), "Przeliczenie 0PLN na dolary");
    }
}
