package io.student.rangiffler.model;

import java.util.Date;

public record SessionJson(
        String username,
        Date issuedAt,
        Date expiresAt
) {
}
