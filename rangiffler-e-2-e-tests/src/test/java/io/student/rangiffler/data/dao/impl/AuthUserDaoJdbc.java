package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.dao.AuthUserDao;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.mapper.UserEntityRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static io.student.rangiffler.tpl.Connections.holder;

public class AuthUserDaoJdbc implements AuthUserDao {
    private final Config CFG = Config.getInstance();

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
                            """,
            SELECT_ALL_SQL =
                    """
                                SELECT * FROM `rangiffler-auth`.`user`;
                            """;

    @Override
    public UserEntity create(UserEntity userEntity) {
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(CREATE_USER_SQL)) {
            ps.setString(1, userEntity.getId().toString());
            ps.setString(2, userEntity.getUsername());
            ps.setString(3, userEntity.getPassword());
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
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(DELETE_USER_SQL)) {
            ps.setString(1, userEntity.getUsername());
            ps.executeUpdate();

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
                while (rs.next()) {
                   userEntityList.add(UserEntityRowMapper.INSTANCE.mapRow(rs, rs.getRow()));
                }
            }

            return userEntityList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
