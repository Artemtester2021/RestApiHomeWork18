package helpers;

import api.requests.authorization.AuthResponseDto;
import api.AccountApiRequests;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context){
        if (context.getTestMethod().isPresent() &&
        context.getTestMethod().get().isAnnotationPresent(WithLogin.class)) {

            authorizeAndSetCookies();
        }
    }

    @Step("Авторизация через Api и установка cookies")
    private void  authorizeAndSetCookies() {
        AuthResponseDto auth = AccountApiRequests.authorize();

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("userID", auth.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", auth.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", auth.getExpires()));
    }
}
