package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By error = By.cssSelector("[data-test='error']");
    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By subtotal = By.cssSelector(".summary_subtotal_label");
    private final By total = By.cssSelector(".summary_total_label");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.cssSelector(".complete-header");

    public CheckoutPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void fillInfo(String first, String last, String zip) {
        type(firstName, first);
        type(lastName, last);
        type(postalCode, zip);
    }

    /** Нажимает Continue и ждёт страницу Overview. */
    public void continueToOverview() {
        click(continueButton);
        wait.until(ExpectedConditions.urlContains("/checkout-step-two.html"));
    }

    /** Нажимает Continue, не ожидая перехода (для негативных проверок). */
    public void clickContinue() {
        click(continueButton);
    }

    public String errorText() {
        return waitVisible(error).getText();
    }

    public String overviewItemName() {
        return waitVisible(itemName).getText().trim();
    }

    public double itemTotal() {
        return parseMoney(waitVisible(subtotal).getText());
    }

    public double orderTotal() {
        return parseMoney(waitVisible(total).getText());
    }

    public void finish() {
        click(finishButton);
        wait.until(ExpectedConditions.urlContains("/checkout-complete.html"));
    }

    public String completeMessage() {
        return waitVisible(completeHeader).getText().trim();
    }

    private double parseMoney(String text) {
        // "Item total: $29.99" -> 29.99
        return Double.parseDouble(text.substring(text.indexOf('$') + 1).trim());
    }
}
