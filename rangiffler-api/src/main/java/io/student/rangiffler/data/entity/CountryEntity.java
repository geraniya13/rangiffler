package io.student.rangiffler.data.entity;

import io.student.rangiffler.model.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "country")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] flag;

    public Country toDto() {
        Country dto = new Country();
        dto.setCode(this.code);
        dto.setName(this.name);
        dto.setFlag("data:image/png;base64," + Base64.getEncoder().encodeToString(this.flag));
        return dto;
    }
}
