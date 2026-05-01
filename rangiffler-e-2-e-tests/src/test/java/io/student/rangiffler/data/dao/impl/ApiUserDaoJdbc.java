package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.dao.ApiUserDao;
import io.student.rangiffler.data.entity.api.ApiUserEntity;
import io.student.rangiffler.data.entity.api.CountryEntity;
import io.student.rangiffler.mapper.ApiUserEntityRowMapper;
import io.student.rangiffler.mapper.CountryEntityRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.student.rangiffler.tpl.Connections.holder;

public class ApiUserDaoJdbc implements ApiUserDao {
    private final Config CFG = Config.getInstance();

    private final String CREATE_USER_SQL =
            """
                          INSERT INTO `rangiffler-api`.`user`
                           (id, username,firstname,lastname,avatar,country_id)  
                           VALUES (UUID_TO_BIN(?, true),?,?,?,?,UUID_TO_BIN(?, true));
                    """,
            DELETE_USER_SQL =
                    """
                                DELETE FROM `rangiffler-api`.`user`
                                WHERE username = ?;
                            """,
            SELECT_ALL_SQL =
                    """
                                SELECT * FROM `rangiffler-api`.`user`;
                            """,
            SELECT_COUNTRY_SQL =
                    """
                                SELECT BIN_TO_UUID(id, 1) AS id, code, name, flag FROM `rangiffler-api`.`country`
                                WHERE code = ?;
                            """,
            SELECT_USER_SQL =
                    """
                                SELECT * FROM `rangiffler-api`.`user` WHERE username = ?;
                            """;

    @Override
    public ApiUserEntity create(ApiUserEntity userEntity) {
        userEntity.setId(UUID.randomUUID());
        try (PreparedStatement ps = holder(CFG.apiJdbcUrl()).connection().prepareStatement(CREATE_USER_SQL)) {
            ps.setString(1, userEntity.getId().toString());
            ps.setString(2, userEntity.getUsername());
            ps.setString(3, userEntity.getFirstname());
            ps.setString(4, userEntity.getLastname());
            ps.setBytes(5, userEntity.getAvatar());
            ps.setString(6, userEntity.getCountry().getId().toString());

            ps.executeUpdate();

            return userEntity;

        } catch (SQLException e) {
            throw new RuntimeException("User creation in api failed", e);
        }
    }

    @Override
    public void delete(ApiUserEntity userEntity) {
        try (PreparedStatement ps = holder(CFG.apiJdbcUrl()).connection().prepareStatement(DELETE_USER_SQL)) {
            ps.setString(1, userEntity.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("User deletion failed", e);
        }
    }

    @Override
    public List<ApiUserEntity> findAll() {
        try (PreparedStatement ps = holder(CFG.apiJdbcUrl()).connection().prepareStatement(SELECT_ALL_SQL)) {
            ps.execute();

            List<ApiUserEntity> userEntityList = new ArrayList<>();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                   userEntityList.add(ApiUserEntityRowMapper.INSTANCE.mapRow(rs, rs.getRow()));
                }
            }

            return userEntityList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CountryEntity> findCountryByCode(String code) {
        try (PreparedStatement ps = holder(CFG.apiJdbcUrl()).connection().prepareStatement(SELECT_COUNTRY_SQL)) {
            ps.setString(1, code);
            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    return Optional.of(CountryEntityRowMapper.INSTANCE.mapRow(rs, rs.getRow()));
                }
                else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ApiUserEntity> findByUsername(String username) {
        try (PreparedStatement ps = holder(CFG.apiJdbcUrl()).connection().prepareStatement(SELECT_USER_SQL)) {
            ps.setString(1, username);

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {

                if (rs.next()) {
                    return Optional.of(
                            ApiUserEntityRowMapper.INSTANCE.mapRow(rs, rs.getRow())
                    );
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
