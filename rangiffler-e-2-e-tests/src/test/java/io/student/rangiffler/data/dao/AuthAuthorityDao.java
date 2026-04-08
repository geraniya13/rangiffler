package io.student.rangiffler.data.dao;

import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.data.entity.UserEntity;


public interface AuthAuthorityDao {
    void create(AuthorityEntity... authority);

    void delete(UserEntity user);
}