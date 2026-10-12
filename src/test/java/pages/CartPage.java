package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {
    private final By items = By.cssSelector(".cart_item");
    private final By itemNames = By.cssSelector(".cart_item .inventory_item_name");
    private final By itemPrices = By.cssSelector(".cart_item .inventory_item_price");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public int itemsCount() {
        return driver.findElements(items).size();
    }

    public List<String> names() {
        List<String> result = new ArrayList<>();
        for (WebElement el : driver.findElements(itemNames)) {
            result.add(el.getText().trim());
        }
        return result;
    }

    public List<String> prices() {
        List<String> result = new ArrayList<>();
        for (WebElement el : driver.findElements(itemPrices)) {
            result.add(el.getText().trim());
        }
        return result;
    }

    public CheckoutPage checkout() {
        click(checkoutButton);
        wait.until(ExpectedConditions.urlContains("/checkout-step-one.html"));
        return new CheckoutPage(driver, wait);
    }
}
