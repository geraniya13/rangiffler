package io.student.rangiffler.data.entity;

import io.student.rangiffler.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.UUID;

@Getter
@Setter
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
    private String lastName;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatar;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    public User toDto() {
        return User.builder()
                .id(this.getId())
                .username(this.getUsername())
                .firstname(this.getFirstname())
                .surname(this.getLastName())
                .avatar("data:image/png;base64," + Base64.getEncoder().encodeToString(this.getAvatar()))
                .location(this.getCountry().toDto())
                .build();
    }
}
