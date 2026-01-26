package io.student.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Data(
        @JsonProperty("user")
        User user
) {
}
