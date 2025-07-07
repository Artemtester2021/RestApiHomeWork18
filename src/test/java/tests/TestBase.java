package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = System.getProperty("screenResolution", "1920x1080");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.pageLoadStrategy = "eager";

        boolean isRemoteRun = System.getProperty("selenoid_host") != null;

        if (isRemoteRun) {
            // Настройки для удалённого запуска
            Configuration.remote = String.format("https://%s:%s@%s/wd/hub",
                    System.getProperty("selenoid_login"),
                    System.getProperty("selenoid_password"),
                    System.getProperty("selenoid_host"));

            Configuration.browserVersion = System.getProperty("browserVersion", "127.0");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }

        RestAssured.baseURI = "https://demoqa.com";
    }

    @BeforeEach
    public void setupAllureListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void shutDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();

        if (System.getProperty("selenoid_host") != null) {
            Attach.addVideo();
        }

        closeWebDriver();
    }
}