package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends TestBase {

    @Test
    @DisplayName("Авторизация standard_user: переход на /inventory.html, заголовок Products")
    void successfulLogin() {
        InventoryPage inventory = loginAsStandardUser();
        assertTrue(inventory.currentUrl().contains("/inventory.html"),
                "Ожидали URL с /inventory.html, получили: " + inventory.currentUrl());
        assertEquals("Products", inventory.title(), "Заголовок страницы товаров");
    }

    @Test
    @DisplayName("Негатив: locked_out_user видит сообщение об ошибке")
    void lockedOutUser() {
        LoginPage login = new LoginPage(driver, wait).open();
        login.login(Config.LOCKED_USER, Config.PASSWORD);
        String error = login.errorText();
        assertTrue(error.contains("Epic sadface") && error.contains("locked out"),
                "Ожидали сообщение о блокировке, получили: " + error);
        assertTrue(login.currentUrl().endsWith("/") || !login.currentUrl().contains("inventory"),
                "Пользователь не должен попасть в каталог");
    }

    @Test
    @DisplayName("Негатив: неверный пароль")
    void wrongPassword() {
        LoginPage login = new LoginPage(driver, wait).open();
        login.login(Config.STANDARD_USER, "wrong_password");
        String error = login.errorText();
        assertTrue(error.contains("do not match"),
                "Ожидали сообщение о неверных данных, получили: " + error);
    }

    @Test
    @DisplayName("Негатив: пустой логин")
    void emptyUsername() {
        LoginPage login = new LoginPage(driver, wait).open();
        login.loginWithoutUsername(Config.PASSWORD);
        String error = login.errorText();
        assertTrue(error.contains("Username is required"),
                "Ожидали 'Username is required', получили: " + error);
    }
}
