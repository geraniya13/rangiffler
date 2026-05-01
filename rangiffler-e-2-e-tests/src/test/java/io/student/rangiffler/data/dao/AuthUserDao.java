package io.student.rangiffler.data.dao;

import io.student.rangiffler.data.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface AuthUserDao {
    UserEntity create(UserEntity user);

    void delete(UserEntity user);

    List<UserEntity> findAll();

    Optional<UserEntity> findByUsername(String username);
}