package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.dao.AuthUserDao;
import io.student.rangiffler.data.entity.auth.UserEntity;
import io.student.rangiffler.mapper.UserEntityRowMapper;
import io.student.rangiffler.tpl.DataSources;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthUserDaoSpringJdbc implements AuthUserDao {
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
                            """,
            SELECT_USER_SQL =
                    """
                                SELECT * FROM `rangiffler-auth`.`user` WHERE username = ?;
                            """;

    @Override
    public UserEntity create(UserEntity user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        user.setId(UUID.randomUUID());
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    CREATE_USER_SQL
            );
            preparedStatement.setString(1, user.getId().toString());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBoolean(4, user.getEnabled());
            preparedStatement.setBoolean(5, user.getAccountNonExpired());
            preparedStatement.setBoolean(6, user.getAccountNonLocked());
            preparedStatement.setBoolean(7, user.getCredentialsNonExpired());
            return preparedStatement;
                }
        );
        return user;
    }

    @Override
    public void delete(UserEntity user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    DELETE_USER_SQL
            );
            preparedStatement.setString(1, user.getUsername());
            return preparedStatement;
        });
    }

    @Override
    public List<UserEntity> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        return jdbcTemplate.query(
                SELECT_ALL_SQL,
                UserEntityRowMapper.INSTANCE
        );
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SELECT_USER_SQL,
                            UserEntityRowMapper.INSTANCE,
                            username
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
