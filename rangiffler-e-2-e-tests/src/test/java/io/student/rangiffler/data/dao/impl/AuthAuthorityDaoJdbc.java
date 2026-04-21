package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.dao.AuthAuthorityDao;
import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.mapper.AuthorityEntityRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.student.rangiffler.tpl.Connections.holder;

public class AuthAuthorityDaoJdbc implements AuthAuthorityDao {
    private final Config CFG = Config.getInstance();

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
                            """,
            SELECT_ALL_SQL =
                    """
                                SELECT * FROM `rangiffler-auth`.`authority`;
                            """;

    @Override
    public void create(AuthorityEntity... authority) {
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(CREATE_AUTHORITY_SQL)) {
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
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(DELETE_AUTHORITY_SQL)) {
            ps.setString(1, userEntity.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Authority deletion failed", e);
        }
    }

    @Override
    public List<AuthorityEntity> findAll() {
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(SELECT_ALL_SQL)) {
            ps.execute();

            List<AuthorityEntity> authorityEntityList = new ArrayList<>();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    authorityEntityList.add(AuthorityEntityRowMapper.INSTANCE.mapRow(rs, rs.getRow()));
                }
            }

            return authorityEntityList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}