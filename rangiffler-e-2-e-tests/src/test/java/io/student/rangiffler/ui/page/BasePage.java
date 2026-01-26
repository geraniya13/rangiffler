package io.student.rangiffler.ui.page;

import com.codeborne.selenide.Selenide;

public abstract class BasePage {
    public static <T extends BasePage> T open(String url, Class<T> pageClass) {
        return Selenide.open(url, pageClass);
    }
}
