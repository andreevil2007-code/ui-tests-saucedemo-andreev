package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Config;

public class LoginPage extends BasePage {
    private final By userName = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public LoginPage open() {
        driver.get(Config.BASE_URL + "/");
        waitVisible(loginButton);
        return this;
    }

    public void login(String user, String pass) {
        type(userName, user);
        type(password, pass);
        click(loginButton);
    }

    public void loginWithoutUsername(String pass) {
        type(password, pass);
        click(loginButton);
    }

    public String errorText() {
        return waitVisible(error).getText();
    }
}
