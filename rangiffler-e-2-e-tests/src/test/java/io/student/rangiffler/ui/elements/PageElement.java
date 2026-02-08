package io.student.rangiffler.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;


public abstract class PageElement {
    protected final SelenideElement element;

    public PageElement(SelenideElement element) {
        this.element = element;
    }

    protected SelenideElement find(By selector) {
        return element.$(selector);
    }

    protected SelenideElement find(String cssSelector) {
        return element.$(cssSelector);
    }

    protected ElementsCollection findAll(By selector) {
        return element.$$(selector);
    }

    protected ElementsCollection findAll(String cssSelector) {
        return element.$$(cssSelector);
    }
}
