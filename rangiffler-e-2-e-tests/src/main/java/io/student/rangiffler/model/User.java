package io.student.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.student.rangiffler.annotation.generators.Random;

public record User(
        @JsonProperty("id")
        String id,
        @Random
        @JsonProperty("username")
        String username,
        @JsonProperty("firstname")
        String firstname,
        @JsonProperty("surname")
        String surname,
        @JsonProperty("avatar")
        String avatar,
        @JsonProperty("location")
        Location location
) {
}
