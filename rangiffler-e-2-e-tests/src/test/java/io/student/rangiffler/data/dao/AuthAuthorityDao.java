package io.student.rangiffler.data.dao;

import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.data.entity.UserEntity;

import java.util.List;


public interface AuthAuthorityDao {
    void create(AuthorityEntity... authority);

    void delete(UserEntity user);

    List<AuthorityEntity> findAll();
}