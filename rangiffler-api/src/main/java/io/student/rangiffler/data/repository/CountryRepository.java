package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

    Optional<CountryEntity> findByCode(String code);
}
