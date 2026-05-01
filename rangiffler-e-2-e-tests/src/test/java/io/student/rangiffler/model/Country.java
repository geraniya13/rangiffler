package io.student.rangiffler.model;

import io.student.rangiffler.data.entity.CountryEntity;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public record Country(
        String flag,
        String code,
        String name
) {
    @Nonnull
    public static Country fromEntity(@Nonnull CountryEntity entity) {
        return new Country(
                entity.getFlag() != null && entity.getFlag().length > 0 ? new String(entity.getFlag(), StandardCharsets.UTF_8) : null,
                entity.getCode(),
                entity.getName()
        );
    }
}
