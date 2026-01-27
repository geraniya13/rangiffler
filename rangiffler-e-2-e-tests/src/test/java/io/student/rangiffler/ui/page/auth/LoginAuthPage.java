package io.student.rangiffler.ui.page.auth;

import com.codeborne.selenide.Selenide;
import io.student.rangiffler.ui.page.MainPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;

public class LoginAuthPage extends BaseAuthPage {
    private static final String LOGIN_PATH = "login";
    public static final String WRONG_CREDENTIALS = "Неверные учетные данные пользователя";

    public static LoginAuthPage open() {
        return Selenide.open(String.format("%s%s", AUTH_URL, LOGIN_PATH), LoginAuthPage.class);
    }

    public MainPage login(String name, String password) {
        inputUserData(name, password);
        submitButton.click();
        return Selenide.page(MainPage.class);
    }

    public LoginAuthPage failedLogin(String name, String password, String errorText) {
        inputUserData(name, password);
        submitButton.click();
        verifyError(errorText);
        return this;
    }

    public LoginAuthPage verifyError(String errorText) {
        errorMessage.shouldBe(visible);
        errorMessage.shouldHave(exactText(errorText));
        return this;
    }

}
