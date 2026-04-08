package io.student.rangiffler.data.dao;

import io.student.rangiffler.data.entity.UserEntity;

public interface AuthUserDao {
    UserEntity create(UserEntity user);

    void delete(UserEntity user);
}