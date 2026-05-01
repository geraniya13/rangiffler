package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.auth.AuthorityEntity;

import java.util.List;


public interface AuthAuthorityRepository {
    void create(AuthorityEntity... authority);

    void delete(AuthorityEntity... authority);

    List<AuthorityEntity> findAll();
}