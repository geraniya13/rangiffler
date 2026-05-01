package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.auth.UserEntity;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository {
    UserEntity create(UserEntity user);

    void delete(UserEntity user);

    List<UserEntity> findAll();

    Optional<UserEntity> findByUsername(String username);
}