package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.dao.ApiUserDao;
import io.student.rangiffler.data.entity.ApiUserEntity;
import io.student.rangiffler.data.entity.CountryEntity;
import io.student.rangiffler.mapper.ApiUserEntityRowMapper;
import io.student.rangiffler.mapper.CountryEntityRowMapper;
import io.student.rangiffler.tpl.DataSources;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class ApiUserDaoSpringJdbc implements ApiUserDao {
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
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.apiJdbcUrl()));
        userEntity.setId(UUID.randomUUID());
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    CREATE_USER_SQL
            );
            ps.setString(1, userEntity.getId().toString());
            ps.setString(2, userEntity.getUsername());
            ps.setString(3, userEntity.getFirstname());
            ps.setString(4, userEntity.getLastname());
            ps.setBytes(5, userEntity.getAvatar());
            ps.setString(6, userEntity.getCountry().getId().toString());
            return ps;
                }
        );
        return userEntity;
    }

    @Override
    public void delete(ApiUserEntity user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.apiJdbcUrl()));
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    DELETE_USER_SQL
            );
            preparedStatement.setString(1, user.getUsername());
            return preparedStatement;
        });
    }

    @Override
    public List<ApiUserEntity> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.apiJdbcUrl()));
        return jdbcTemplate.query(
                SELECT_ALL_SQL,
                ApiUserEntityRowMapper.INSTANCE
        );
    }

    @Override
    public Optional<CountryEntity> findCountryByCode(String code) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.apiJdbcUrl()));
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SELECT_COUNTRY_SQL,
                            CountryEntityRowMapper.INSTANCE,
                            code
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ApiUserEntity> findByUsername(String username) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.apiJdbcUrl()));
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SELECT_USER_SQL,
                            ApiUserEntityRowMapper.INSTANCE,
                            username
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
