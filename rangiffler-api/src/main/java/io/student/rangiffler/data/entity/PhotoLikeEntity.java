package io.student.rangiffler.data.entity;

import io.student.rangiffler.model.PhotoLikeEntityId;
import jakarta.persistence.*;
import lombok.*;


@Entity
@IdClass(PhotoLikeEntityId.class)
@Table(name = "photo_like")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoLikeEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "photo_id", nullable = false, columnDefinition = "BINARY(16)")
    private PhotoEntity photo;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "like_id", nullable = false, columnDefinition = "BINARY(16)")
    private LikeEntity like;
}
