package io.student.rangiffler.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "statistic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "country_id", nullable = false, columnDefinition = "BINARY(16)")
    private CountryEntity country;

    @Column(nullable = false)
    private int count;
}
