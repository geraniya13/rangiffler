package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.dao.AuthUserDao;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.mapper.UserEntityRowMapper;
import io.student.rangiffler.tpl.DataSources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class AuthUserDaoSpringJdbc implements AuthUserDao {
    private final Config CFG = Config.getInstance();

    @Override
    public UserEntity create(UserEntity user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO `rangiffler-auth`.user (username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.getEnabled());
            preparedStatement.setBoolean(4, user.getAccountNonExpired());
            preparedStatement.setBoolean(5, user.getAccountNonLocked());
            preparedStatement.setBoolean(6, user.getCredentialsNonExpired());
            return preparedStatement;
                }, keyHolder
        );
        final UUID generatedKey = (UUID) keyHolder.getKeys().get("id");
        user.setId(generatedKey);
        return user;
    }

    @Override
    public void delete(UserEntity user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM `rangiffler-auth`.user WHERE username = ?"
            );
            preparedStatement.setString(1, user.getUsername());
            return preparedStatement;
        });
    }

    @Override
    public List<UserEntity> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        return jdbcTemplate.query(
                "SELECT * FROM `rangiffler-auth`.`user`",
                UserEntityRowMapper.INSTANCE
        );
    }
}
