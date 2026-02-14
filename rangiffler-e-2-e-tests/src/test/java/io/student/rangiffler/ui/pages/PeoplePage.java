package io.student.rangiffler.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.student.rangiffler.enums.Action;
import io.student.rangiffler.enums.PeopleTab;
import io.student.rangiffler.ui.elements.PeopleElement;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PeoplePage {
    private static final String PEOPLE_URL = "/people";

    private final SelenideElement searchInput = $("input[placeholder='Search people']"),
            searchButton = $(byAttribute("data-testid", "SearchIcon")),
            peopleTable = $("table[aria-labelledby='tableTitle']");

    private final ElementsCollection tabButtons = $$("button[type='button'][role ='tab']"),
            peopleElements = $$("tbody tr");

    public static PeoplePage open() {
        return Selenide.open(PEOPLE_URL, PeoplePage.class);
    }

    public PeoplePage goToPeopleTab(PeopleTab tab) {
        tabButtons
                .findBy(text(tab.getTabName()))
                .click();
        return this;
    }

    public List<PeopleElement> getPeopleElements() {
        peopleTable.shouldBe(visible);
        return peopleElements.stream().map(PeopleElement::new).toList();
    }

    public PeoplePage searchPerson(String person) {
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(person);
        searchButton.click();
        return this;
    }


    public static boolean arePeoplePresented(List<PeopleElement> people) {
        return people.isEmpty();
    }

    public static boolean isPersonPresented(List<PeopleElement> people, String name) {
        return people.stream()
                .anyMatch(person -> person.isPersonPresented(name));
    }

    public static PeopleElement getPerson(List<PeopleElement> people, String name) {
        return people.stream()
                .filter(person -> person.isPersonPresented(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Element not found"));
    }

    public PeoplePage shouldHavePeople() {
        peopleTable.shouldBe(visible);
        peopleElements.shouldHave(sizeGreaterThan(0));
        return this;
    }


    public PeoplePage shouldHavePersonWithActions(String name, Action availableAction) {
        peopleTable.shouldBe(visible);
        List<PeopleElement> people = getPeopleElements();
        PeopleElement person = getPerson(people, name);
        person.checkRelationship(availableAction.getActionName());
        return this;
    }

    public PeoplePage shouldBeEmpty() {
        peopleTable.shouldBe(visible);
        peopleElements.shouldHave(size(0));
        return this;
    }
}
