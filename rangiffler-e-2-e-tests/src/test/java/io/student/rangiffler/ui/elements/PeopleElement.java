package io.student.rangiffler.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;


public class PeopleElement {
    protected final SelenideElement element;

    private final ElementsCollection cells;

    private final SelenideElement actions;

    public PeopleElement(SelenideElement element) {
        this.element = element;
        this.cells = element.$$("td");
        this.actions = element.lastChild();
    }

    public boolean isPersonPresented(String name) {
        return cells.stream().anyMatch(cell -> cell.text().equals(name));
    }


    public PeopleElement checkRelationship(String availableAction) {
        actions.shouldBe(visible);
        actions.shouldHave(text(availableAction));
        return this;
    }
}
