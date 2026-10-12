package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {
    private final By title = By.cssSelector(".title");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By sortSelect = By.cssSelector(".product_sort_container");
    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By itemPrices = By.cssSelector(".inventory_item_price");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String title() {
        return waitVisible(title).getText();
    }

    /** slug - часть id кнопки, например "sauce-labs-backpack". */
    public void addToCart(String slug) {
        click(By.id("add-to-cart-" + slug));
        waitVisible(By.id("remove-" + slug));
    }

    public void removeFromCart(String slug) {
        click(By.id("remove-" + slug));
        waitVisible(By.id("add-to-cart-" + slug));
    }

    /** Если бейджа нет, корзина пуста -> 0. */
    public int cartCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badges.get(0).getText().trim());
    }

    public CartPage openCart() {
        click(cartLink);
        wait.until(ExpectedConditions.urlContains("/cart.html"));
        return new CartPage(driver, wait);
    }

    public void sortBy(String value) {
        // value: az, za, lohi, hilo
        new Select(waitVisible(sortSelect)).selectByValue(value);
    }

    public List<String> names() {
        List<String> result = new ArrayList<>();
        for (WebElement el : driver.findElements(itemNames)) {
            result.add(el.getText().trim());
        }
        return result;
    }

    public List<Double> prices() {
        List<Double> result = new ArrayList<>();
        for (WebElement el : driver.findElements(itemPrices)) {
            result.add(Double.parseDouble(el.getText().replace("$", "").trim()));
        }
        return result;
    }
}
