package io.student.rangiffler.data.dao;

import io.student.rangiffler.data.entity.ApiUserEntity;
import io.student.rangiffler.data.entity.CountryEntity;

import java.util.List;
import java.util.Optional;

public interface ApiUserDao {
    ApiUserEntity create(ApiUserEntity user);

    void delete(ApiUserEntity user);

    List<ApiUserEntity> findAll();

    Optional<CountryEntity> findCountryByCode(String code);

    Optional<ApiUserEntity> findByUsername(String username);
}