package io.student.rangiffler.data.repository.impl;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.entity.auth.UserEntity;
import io.student.rangiffler.data.entity.auth.AuthorityEntity;
import io.student.rangiffler.data.repository.AuthUserRepository;
import io.student.rangiffler.mapper.AuthorityEntityRowMapper;
import io.student.rangiffler.mapper.UserEntityRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.student.rangiffler.tpl.Connections.holder;

public class AuthUserRepositoryJdbc implements AuthUserRepository {
    private final Config CFG = Config.getInstance();

    private final String CREATE_USER_SQL =
            """
                          INSERT INTO `rangiffler-auth`.`user`
                           (id, username,password,enabled,account_non_expired,account_non_locked,credentials_non_expired)  
                           VALUES (UUID_TO_BIN(?, true),?,?,?,?,?,?);
                    """,
            SELECT_ALL_SQL =
                    """
                                SELECT
                                    u.id,
                                    u.username,
                                    u.password,
                                    u.enabled,
                                    u.account_non_expired,
                                    u.account_non_locked,
                                    u.credentials_non_expired,
                                    a.id AS authority_id,
                                    a.user_id,
                                    a.authority
                                FROM `rangiffler-auth`.`user` u
                                JOIN `rangiffler-auth`.`authority` a ON u.id = a.user_id;
                            """,
            SELECT_USER_SQL =
                    """
                                SELECT
                                    u.id,
                                    u.username,
                                    u.password,
                                    u.enabled,
                                    u.account_non_expired,
                                    u.account_non_locked,
                                    u.credentials_non_expired,
                                    a.id AS authority_id,
                                    a.user_id,
                                    a.authority
                                FROM `rangiffler-auth`.`user` u
                                JOIN `rangiffler-auth`.`authority` a ON u.id = a.user_id
                                WHERE u.username = ?;
                            """,
            CREATE_AUTHORITY_SQL =
                    """
                                    INSERT INTO `rangiffler-auth`.`authority` 
                                    (id, user_id, authority)
                                    VALUES (UUID_TO_BIN(?, true),UUID_TO_BIN(?, true),?);
                            """,
            DELETE_USER_BY_ID_SQL =
                    """
                                DELETE FROM `rangiffler-auth`.`user`
                                WHERE id = UUID_TO_BIN(?);
                            """,
            DELETE_AUTHORITY_BY_ID_SQL =
                    """
                            DELETE FROM `rangiffler-auth`.`authority`
                            WHERE id = UUID_TO_BIN(?);
                            """;

    @Override
    public UserEntity create(UserEntity userEntity) {
        userEntity.setId((UUID.randomUUID()));
        try (PreparedStatement userPs = holder(CFG.authJdbcUrl()).connection().prepareStatement(CREATE_USER_SQL);
             PreparedStatement authorityPs = holder(CFG.authJdbcUrl()).connection().prepareStatement(CREATE_AUTHORITY_SQL)) {
            userPs.setObject(1, userEntity.getId().toString());
            userPs.setString(2, userEntity.getUsername());
            userPs.setString(3, userEntity.getPassword());
            userPs.setBoolean(4, userEntity.getEnabled());
            userPs.setBoolean(5, userEntity.getAccountNonExpired());
            userPs.setBoolean(6, userEntity.getAccountNonLocked());
            userPs.setBoolean(7, userEntity.getCredentialsNonExpired());
            userPs.executeUpdate();

            List<AuthorityEntity> authorityEntityList = userEntity.getAuthorities();

            for (AuthorityEntity authorityEntity : authorityEntityList) {
                UUID id = UUID.randomUUID();
                authorityEntity.setId(id);
                userEntity.getAuthorities().get(userEntity.getAuthorities().indexOf(authorityEntity)).setId(id);
                authorityPs.setObject(1, authorityEntity.getId().toString());
                authorityPs.setString(2, userEntity.getId().toString());
                authorityPs.setString(3, authorityEntity.getAuthority().name());
                authorityPs.addBatch();
                authorityPs.clearParameters();
            }
            authorityPs.executeBatch();

            return userEntity;

        } catch (SQLException e) {
            throw new RuntimeException("User creation failed", e);
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        userEntity = findByUsername(userEntity.getUsername()).orElseThrow();
        try (PreparedStatement userPs = holder(CFG.authJdbcUrl()).connection().prepareStatement(DELETE_USER_BY_ID_SQL);
             PreparedStatement authorityPs = holder(CFG.authJdbcUrl()).connection().prepareStatement(DELETE_AUTHORITY_BY_ID_SQL)) {
            for (AuthorityEntity authorityEntity : userEntity.getAuthorities()) {
                authorityPs.setString(1, authorityEntity.getId().toString());
                authorityPs.executeUpdate();
            }

            userPs.setString(1, userEntity.getId().toString());
            userPs.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("User deletion failed", e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(SELECT_ALL_SQL)) {
            ps.execute();

            List<UserEntity> userEntityList = new ArrayList<>();

            try (ResultSet rs = ps.getResultSet()) {
                UserEntity userEntity = null;
                List<AuthorityEntity> authorityEntityList = new ArrayList<>();
                while (rs.next()) {
                    if (userEntity == null) {
                        userEntity = UserEntityRowMapper.INSTANCE.mapRow(rs, 1);
                    } else if (!userEntity.getId().equals(rs.getObject("id", UUID.class))) {
                        userEntity.setAuthorities(authorityEntityList);

                        userEntityList.add(userEntity);
                        userEntity = UserEntityRowMapper.INSTANCE.mapRow(rs, rs.getRow());
                        authorityEntityList = new ArrayList<>();
                    }

                    authorityEntityList.add(AuthorityEntityRowMapper.INSTANCE.mapRow(rs, rs.getRow()));
                }
            }

            return userEntityList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(SELECT_USER_SQL)) {
            ps.setString(1, username);

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                UserEntity userEntity = null;
                List<AuthorityEntity> authorityEntityList = new ArrayList<>();
                while (rs.next()) {
                    if (userEntity == null) {
                        userEntity = UserEntityRowMapper.INSTANCE.mapRow(rs, 1);
                    }
                    authorityEntityList.add(AuthorityEntityRowMapper.INSTANCE.mapRow(rs, rs.getRow()));
                }

                if (userEntity == null) {
                    return Optional.empty();
                } else {
                    userEntity.setAuthorities(authorityEntityList);
                    return Optional.of(userEntity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
