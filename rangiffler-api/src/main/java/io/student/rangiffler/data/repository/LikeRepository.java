package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.LikeEntity;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {

    List<Like> findByUser(UserEntity user);
}
