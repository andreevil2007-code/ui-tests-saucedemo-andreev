package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.InventoryPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortTest extends TestBase {

    @Test
    @DisplayName("Сортировка по названию A -> Z")
    void nameAscending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("az");
        List<String> actual = inventory.names();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        assertEquals(expected, actual, "Порядок названий A-Z");
    }

    @Test
    @DisplayName("Сортировка по названию Z -> A")
    void nameDescending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("za");
        List<String> actual = inventory.names();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        assertEquals(expected, actual, "Порядок названий Z-A");
    }

    @Test
    @DisplayName("Сортировка по цене low -> high")
    void priceAscending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("lohi");
        List<Double> actual = inventory.prices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        assertEquals(expected, actual, "Порядок цен low-high");
    }

    @Test
    @DisplayName("Сортировка по цене high -> low")
    void priceDescending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("hilo");
        List<Double> actual = inventory.prices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        assertEquals(expected, actual, "Порядок цен high-low");
    }
}
