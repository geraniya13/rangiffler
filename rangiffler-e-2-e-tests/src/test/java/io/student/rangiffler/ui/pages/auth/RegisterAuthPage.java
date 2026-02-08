package io.student.rangiffler.ui.pages.auth;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegisterAuthPage extends BaseAuthPage {
    private static final String REGISTER_PATH = "register";
    public static final String SHORT_PASSWORD = "Allowed password length should be from 3 to 12 characters",
            DIF_PASSWORDS = "Passwords should be equal";

    private final SelenideElement passwordSubmitInput = $("#passwordSubmit"),
            successMessage = $(byText("Congratulations! You've registered!"));

    public static RegisterAuthPage open() {
        return Selenide.open(String.format("%s%s", AUTH_URL, REGISTER_PATH), RegisterAuthPage.class);
    }

    public RegisterAuthPage register(String name, String password, String passwordConfirm) {
        inputUserData(name, password);
        passwordSubmitInput.val(passwordConfirm);
        submitButton.click();
        return this;
    }

    public RegisterAuthPage verifySuccess() {
        successMessage.shouldBe(visible);
        return this;
    }

    public RegisterAuthPage verifySameState() {
        submitButton.shouldBe(visible);
        successMessage.shouldNotBe(visible);
        return this;
    }

    public RegisterAuthPage verifyError(String errorText) {
        errorMessage.shouldBe(visible);
        errorMessage.shouldHave(exactText(errorText));
        return this;
    }
}
