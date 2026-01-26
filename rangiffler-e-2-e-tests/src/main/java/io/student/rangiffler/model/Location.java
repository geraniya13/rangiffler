package io.student.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Location(
        @JsonProperty("code")
        String code,
        @JsonProperty("name")
        String name
) {
}
