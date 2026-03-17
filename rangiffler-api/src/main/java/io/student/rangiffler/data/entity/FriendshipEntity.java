package io.student.rangiffler.data.entity;

import io.student.rangiffler.model.FriendshipEntityId;
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
    private UserEntity requester;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "addressee_id", nullable = false, columnDefinition = "BINARY(16)")
    private UserEntity addressee;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private String status;
}
