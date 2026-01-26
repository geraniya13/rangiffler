package io.student.rangiffler.ui.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.student.rangiffler.ui.page.auth.BaseAuthPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage {
    private static final String MAIN_URL = "/my-travels";

    private final SelenideElement map = $("figure.worldmap__figure-container"),
            logoutButton = $("button[aria-label='Logout']");

    public static MainPage open() {
        return Selenide.open(MAIN_URL, MainPage.class);
    }

    public MainPage assertMapIsVisible() {
        map.shouldBe(visible);
        return this;
    }

    public BaseAuthPage logout() {
        logoutButton.click();
        return Selenide.page(BaseAuthPage.class);
    }
}
