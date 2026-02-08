package io.student.rangiffler.enums;

public enum Action {
    REMOVE("Remove"),
    WAITING("Waiting..."),
    ACCEPT("Accept"),
    DECLINE("Decline");

    private final String actionName;

    Action(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }
}
