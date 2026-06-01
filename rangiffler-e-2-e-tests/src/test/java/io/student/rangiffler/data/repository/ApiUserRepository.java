package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.api.ApiUserEntity;
import io.student.rangiffler.data.entity.api.CountryEntity;

import java.util.List;
import java.util.Optional;

public interface ApiUserRepository {
    ApiUserEntity create(ApiUserEntity user);

    void delete(ApiUserEntity user);

    List<ApiUserEntity> findAll();

    Optional<CountryEntity> findCountryByCode(String code);

    Optional<ApiUserEntity> findByUsername(String username);

    void addInvitation(ApiUserEntity requester, ApiUserEntity addressee);

    void addFriend(ApiUserEntity requester, ApiUserEntity addressee);
}