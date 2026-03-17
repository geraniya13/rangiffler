package io.student.rangiffler.model;

import io.student.rangiffler.data.entity.LikeEntity;
import io.student.rangiffler.data.entity.PhotoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoLikeEntityId implements Serializable {
    private PhotoEntity photo;

    private LikeEntity like;

    @Override
    public String toString() {
        return "PhotoLikeEntityId{" +
                "photo=" + photo +
                ", like=" + like +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhotoLikeEntityId that = (PhotoLikeEntityId) o;
        return Objects.equals(photo, that.photo) && Objects.equals(like, that.like);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photo, like);
    }
}
