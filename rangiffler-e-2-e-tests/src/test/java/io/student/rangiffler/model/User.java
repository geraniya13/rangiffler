package io.student.rangiffler.model;

import io.student.rangiffler.data.entity.ApiUserEntity;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public record User(
        String id,
        String username,
        String firstname,
        String surname,
        String avatar,
        Country location
) {
    @Nonnull
    public static User fromEntity(@Nonnull ApiUserEntity entity) {
        return new User(
                entity.getId().toString(),
                entity.getUsername(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getAvatar() != null && entity.getAvatar().length > 0 ? new String(entity.getAvatar(), StandardCharsets.UTF_8) : null,
                Country.fromEntity(entity.getCountry())
        );
    }
}
