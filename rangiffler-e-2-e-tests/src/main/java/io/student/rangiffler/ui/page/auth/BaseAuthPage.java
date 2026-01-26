package io.student.rangiffler.ui.page.auth;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.student.rangiffler.ui.page.BasePage;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class BaseAuthPage extends BasePage {
    protected static final String AUTH_URL = "http://localhost:9002/";

    private final SelenideElement loginButton = $(byText("Login")),
            registerButton = $(byText("Register")),
            nameInput = $("input[name='username']"),
            passwordInput = $("input[type='password']");
    protected final SelenideElement submitButton = $("button[type='submit']"),
            errorMessage = $(".form__error");

    protected void inputUserData(String name, String password) {
        nameInput.val(name);
        passwordInput.val(password);
    }

    public LoginAuthPage login() {
        loginButton.click();
        return Selenide.page(LoginAuthPage.class);
    }

    public RegisterAuthPage register() {
        registerButton.click();
        return Selenide.page(RegisterAuthPage.class);
    }
}
