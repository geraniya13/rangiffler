package io.student.rangiffler.model;

import io.student.rangiffler.data.entity.LikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private String user;

    private String username;

    private LocalDate creationDate;

    @Override
    public String toString() {
        return "Like{user='" + user + "', username='" + username + "', creationDate='" + creationDate + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like that = (Like) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(username, that.username) &&
                Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, username, creationDate);
    }

    public Like toDto(LikeEntity likeEntity) {
        return Like.builder()
                .user(likeEntity.getUser().getId().toString())
                .username(likeEntity.getUser().getUsername())
                .creationDate(likeEntity.getCreatedDate().toLocalDate())
                .build();
    }
}
