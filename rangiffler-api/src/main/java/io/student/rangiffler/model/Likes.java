package io.student.rangiffler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes {
    private int total;

    private List<Like> likes;

    @Override
    public String toString() {
        return "Likes{total='" + total + "', likes='" + likes + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Likes that = (Likes) o;
        return total == that.total &&
                Objects.equals(likes, that.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, likes);
    }
}
