package io.student.rangiffler.data.entity.api;

import io.student.rangiffler.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@IdClass(FriendshipEntityId.class)
@Table(name = "friendship")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "requester_id", nullable = false, columnDefinition = "BINARY(16)")
    private ApiUserEntity requester;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "addressee_id", nullable = false, columnDefinition = "BINARY(16)")
    private ApiUserEntity addressee;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
}
