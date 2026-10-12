package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutTest extends TestBase {
    private static final String BACKPACK = "sauce-labs-backpack";

    private CheckoutPage goToCheckoutWithBackpack() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart(BACKPACK);
        CartPage cart = inventory.openCart();
        return cart.checkout();
    }

    @Test
    @DisplayName("Полное оформление заказа -> 'Thank you for your order!'")
    void successfulCheckout() {
        CheckoutPage checkout = goToCheckoutWithBackpack();
        checkout.fillInfo("Ivan", "Petrov", "12345");
        checkout.continueToOverview();

        assertEquals("Sauce Labs Backpack", checkout.overviewItemName(), "Товар на странице Overview");
        assertEquals(29.99, checkout.itemTotal(), 0.001, "Сумма товаров");
        assertTrue(checkout.orderTotal() > checkout.itemTotal(),
                "Итог с налогом должен быть больше суммы товаров");

        checkout.finish();
        assertEquals("Thank you for your order!", checkout.completeMessage(), "Сообщение об успешном заказе");
    }

    @Test
    @DisplayName("Негатив: пустое имя - нельзя продолжить")
    void emptyFirstName() {
        CheckoutPage checkout = goToCheckoutWithBackpack();
        checkout.fillInfo("", "Petrov", "12345");
        checkout.clickContinue();
        String error = checkout.errorText();
        assertTrue(error.contains("First Name is required"),
                "Ожидали 'First Name is required', получили: " + error);
    }

    @Test
    @DisplayName("Негатив: пустой почтовый индекс - нельзя продолжить")
    void emptyPostalCode() {
        CheckoutPage checkout = goToCheckoutWithBackpack();
        checkout.fillInfo("Ivan", "Petrov", "");
        checkout.clickContinue();
        String error = checkout.errorText();
        assertTrue(error.contains("Postal Code is required"),
                "Ожидали 'Postal Code is required', получили: " + error);
    }
}
