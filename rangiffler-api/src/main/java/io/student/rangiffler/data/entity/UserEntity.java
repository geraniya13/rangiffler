package io.student.rangiffler.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column
    private String firstname;


    @Column
    private String lastname;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatar;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "country_id", nullable = false, columnDefinition = "BINARY(16)")
    private CountryEntity country;
}
