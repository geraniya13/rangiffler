package io.student.rangiffler.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.student.rangiffler.ui.elements.PageElement;

import java.util.List;
import java.util.function.Function;

public abstract class BasePage {
    public static <T extends BasePage> T open(String url, Class<T> pageClass) {
        return Selenide.open(url, pageClass);
    }

    protected <T extends PageElement> List<T> generatePageElements
            (ElementsCollection collection, Function<SelenideElement, T> creator) {
        return collection.stream().map(creator).toList();
    }
}
