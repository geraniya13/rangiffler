package io.student.rangiffler.enums;

public enum PeopleTab {
    FRIENDS("Friends"),
    ALL_PEOPLE("All people"),
    OUTCOME_INVITATIONS("Outcome invitations"),
    INCOME_INVITATIONS("Income invitations");

    private final String tabName;

    PeopleTab(String tabName) {
        this.tabName = tabName;
    }

    public String getTabName() {
        return tabName;
    }
}
