package io.student.rangiffler.model;

public record User(
        String id,
        String username,
        String firstname,
        String surname,
        String avatar,
        Location location
) {
}
