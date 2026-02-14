package io.student.rangiffler.model;

public record StaticUser(
        String username,
        String password,
        String friend,
        String income,
        String outcome
) {
}
