package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;

import java.util.Map;

public abstract class TestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,900", "--disable-notifications", "--incognito");
        // Отключаем всплывающее окно Chrome про "смену пароля / утечку пароля"
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        driver = new ChromeDriver(options); // Selenium Manager сам подберёт chromedriver
        wait = new WebDriverWait(driver, Config.TIMEOUT);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /** Общий шаг: войти под standard_user и дождаться страницы товаров. */
    protected InventoryPage loginAsStandardUser() {
        new LoginPage(driver, wait).open().login(Config.STANDARD_USER, Config.PASSWORD);
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        return new InventoryPage(driver, wait);
    }
}
