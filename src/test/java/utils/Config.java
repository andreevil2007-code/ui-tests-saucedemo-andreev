package utils;

import java.time.Duration;

public final class Config {
    private Config() {}

    public static final String BASE_URL = "https://www.saucedemo.com";
    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    // Демо-учётные записи Swag Labs
    public static final String PASSWORD = "secret_sauce";
    public static final String STANDARD_USER = "standard_user";
    public static final String LOCKED_USER = "locked_out_user";
}
