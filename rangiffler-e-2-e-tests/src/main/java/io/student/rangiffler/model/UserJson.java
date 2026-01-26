package io.student.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserJson(
        @JsonProperty("data")
        Data data
) {
}
