package io.student.rangiffler.data.repository;

import io.student.rangiffler.data.entity.CountryEntity;
import io.student.rangiffler.data.entity.UserEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID>  {

    Optional<UserEntity> findByUsername(String username);

    @Query("select u from UserEntity u where u.username <> :username" +
            " and (u.username like %:searchQuery% or u.firstname like %:searchQuery% " +
            "or u.lastname like %:searchQuery%)")
    Slice<UserEntity> findByUsernameNotAndSearchQuery(@Param("username") @Nonnull String username,
                                                      @Nonnull Pageable pageable,
                                                      @Param("searchQuery") String searchQuery);

    Slice<UserEntity> findByUsernameNot(@Nonnull String username,
                                        @Nonnull Pageable pageable);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.addressee" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.ACCEPTED and f.requester = :requester")
    Slice<UserEntity> findFriends(@Param("requester") UserEntity requester,
                                  @Nonnull Pageable pageable);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.addressee" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.ACCEPTED and f.requester = :requester" +
            " and (u.username like %:searchQuery% or u.firstname like %:searchQuery% or u.lastname like %:searchQuery%)")
    Slice<UserEntity> findFriends(@Param("requester") UserEntity requester,
                                  @Nonnull Pageable pageable,
                                  @Param("searchQuery") String searchQuery);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.addressee" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.ACCEPTED and f.requester = :requester")
    Slice<UserEntity> findFriends(@Param("requester") UserEntity requester);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.addressee" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.ACCEPTED and f.requester = :requester" +
            " and (u.username like %:searchQuery% or u.firstname like %:searchQuery% or u.lastname like %:searchQuery%)")
    Slice<UserEntity> findFriends(@Param("requester") UserEntity requester,
                                  @Param("searchQuery") String searchQuery);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.addressee" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.PENDING and f.requester = :requester")
    Slice<UserEntity> findOutcomeInvitations(@Param("requester") UserEntity requester,
                                             @Nonnull Pageable pageable);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.addressee" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.PENDING and f.requester = :requester" +
            " and (u.username like %:searchQuery% or u.firstname like %:searchQuery% or u.lastname like %:searchQuery%)")
    Slice<UserEntity> findOutcomeInvitations(@Param("requester") UserEntity requester,
                                             @Nonnull Pageable pageable,
                                             @Param("searchQuery") String searchQuery);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.addressee" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.PENDING and f.requester = :requester")
    Slice<UserEntity> findOutcomeInvitations(@Param("requester") UserEntity requester);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.addressee" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.PENDING and f.requester = :requester" +
            " and (u.username like %:searchQuery% or u.firstname like %:searchQuery% or u.lastname like %:searchQuery%)")
    Slice<UserEntity> findOutcomeInvitations(@Param("requester") UserEntity requester,
                                            @Param("searchQuery") String searchQuery);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.requester" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.PENDING and f.addressee = :addressee")
    Slice<UserEntity> findIncomeInvitations(@Param("addressee") UserEntity addressee,
                                            @Nonnull Pageable pageable);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.requester" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.PENDING and f.addressee = :addressee" +
            " and (u.username like %:searchQuery% or u.firstname like %:searchQuery% or u.lastname like %:searchQuery%)")
    Slice<UserEntity> findIncomeInvitations(@Param("addressee") UserEntity addressee,
                                            @Nonnull Pageable pageable,
                                            @Param("searchQuery") String searchQuery);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.requester" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.PENDING and f.addressee = :addressee")
    Slice<UserEntity> findIncomeInvitations(@Param("addressee") UserEntity addressee);

    @Query("select u from UserEntity u join FriendshipEntity f on u = f.requester" +
            " where f.status = io.student.rangiffler.model.FriendshipStatus.PENDING and f.addressee = :addressee" +
            " and (u.username like %:searchQuery% or u.firstname like %:searchQuery% or u.lastname like %:searchQuery%)")
    Slice<UserEntity> findIncomeInvitations(@Param("addressee") UserEntity addressee,
                                           @Param("searchQuery") String searchQuery);
}
