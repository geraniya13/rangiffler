package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatisticRepository extends JpaRepository<StatisticEntity, UUID> {
}
