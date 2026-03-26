package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.PhotoLikeEntity;
import io.student.rangiffler.model.PhotoLikeEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhotoLikeRepository extends JpaRepository<PhotoLikeEntity, PhotoLikeEntityId> {

    List<PhotoLikeEntity> findAllByPhotoId(UUID photoId);
}
