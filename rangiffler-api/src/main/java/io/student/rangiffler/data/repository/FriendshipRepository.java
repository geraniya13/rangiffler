package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.FriendshipEntity;
import io.student.rangiffler.model.FriendshipEntityId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendshipRepository extends JpaRepository<FriendshipEntity, FriendshipEntityId> {

}
