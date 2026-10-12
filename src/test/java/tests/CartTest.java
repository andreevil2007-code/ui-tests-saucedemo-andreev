package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.InventoryPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest extends TestBase {
    private static final String BACKPACK = "sauce-labs-backpack";
    private static final String BIKE_LIGHT = "sauce-labs-bike-light";

    @Test
    @DisplayName("Добавить рюкзак: счётчик = 1, в корзине верные название и цена")
    void addBackpack() {
        InventoryPage inventory = loginAsStandardUser();
        assertEquals(0, inventory.cartCount(), "Изначально корзина пуста");

        inventory.addToCart(BACKPACK);
        assertEquals(1, inventory.cartCount(), "Счётчик корзины после добавления");

        CartPage cart = inventory.openCart();
        assertEquals(List.of("Sauce Labs Backpack"), cart.names(), "Названия в корзине");
        assertEquals(List.of("$29.99"), cart.prices(), "Цены в корзине");
    }

    @Test
    @DisplayName("Добавить два товара, затем удалить один")
    void addTwoRemoveOne() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart(BACKPACK);
        inventory.addToCart(BIKE_LIGHT);
        assertEquals(2, inventory.cartCount(), "Счётчик после добавления двух товаров");

        inventory.removeFromCart(BACKPACK);
        assertEquals(1, inventory.cartCount(), "Счётчик после удаления одного товара");

        CartPage cart = inventory.openCart();
        assertEquals(1, cart.itemsCount(), "Количество позиций в корзине");
        assertEquals(List.of("Sauce Labs Bike Light"), cart.names(), "Оставшийся товар");
    }
}
