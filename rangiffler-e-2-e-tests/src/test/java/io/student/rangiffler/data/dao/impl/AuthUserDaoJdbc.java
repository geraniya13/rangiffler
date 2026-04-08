package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.data.dao.AuthUserDao;
import io.student.rangiffler.data.entity.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthUserDaoJdbc implements AuthUserDao {
    private final Connection connection;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public AuthUserDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    private final String CREATE_USER_SQL =
            """
                          INSERT INTO `rangiffler-auth`.`user`
                           (id, username,password,enabled,account_non_expired,account_non_locked,credentials_non_expired)  
                           VALUES (UUID_TO_BIN(?, true),?,?,?,?,?,?);
                    """,
            DELETE_USER_SQL =
                    """
                                DELETE FROM `rangiffler-auth`.`user`
                                WHERE username = ?;
                            """;

    @Override
    public UserEntity create(UserEntity userEntity) {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_USER_SQL)) {
            ps.setString(1, userEntity.getId().toString());
            ps.setString(2, userEntity.getUsername());
            ps.setString(3, passwordEncoder.encode(userEntity.getPassword()));
            ps.setBoolean(4, userEntity.getEnabled());
            ps.setBoolean(5, userEntity.getAccountNonExpired());
            ps.setBoolean(6, userEntity.getAccountNonLocked());
            ps.setBoolean(7, userEntity.getCredentialsNonExpired());
            ps.executeUpdate();

            return userEntity;

        } catch (SQLException e) {
            throw new RuntimeException("User creation failed", e);
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL)) {
            ps.setString(1, userEntity.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("User deletion failed", e);
        }
    }
}
