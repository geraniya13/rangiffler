package io.student.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Location(
        String code,
        String name
) {
}
