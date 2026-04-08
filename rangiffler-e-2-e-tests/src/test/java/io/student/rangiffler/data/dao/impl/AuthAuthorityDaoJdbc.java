package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.data.dao.AuthAuthorityDao;
import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.data.entity.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class AuthAuthorityDaoJdbc implements AuthAuthorityDao {
    private final Connection connection;

    public AuthAuthorityDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    private final String CREATE_AUTHORITY_SQL =
            """
                            INSERT INTO `rangiffler-auth`.authority 
                            (id, user_id, authority)
                            VALUES (UUID_TO_BIN(?, true),UUID_TO_BIN(?, true),?);
                    """,
            DELETE_AUTHORITY_SQL =
                    """
                            DELETE FROM `rangiffler-auth`.`authority`
                            WHERE user_id = (SELECT id FROM `rangiffler-auth`.`user` WHERE username = ?);
                            """;

    @Override
    public void create(AuthorityEntity... authority) {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_AUTHORITY_SQL)) {
            for (AuthorityEntity authorityEntity : authority) {
                ps.setObject(1, UUID.randomUUID().toString());
                ps.setString(2, authorityEntity.getUser().getId().toString());
                ps.setString(3, authorityEntity.getAuthority().name());
                ps.addBatch();
                ps.clearParameters();
            }
            ps.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Authority creation failed", e);
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_AUTHORITY_SQL)) {
            ps.setString(1, userEntity.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Authority deletion failed", e);
        }
    }
}