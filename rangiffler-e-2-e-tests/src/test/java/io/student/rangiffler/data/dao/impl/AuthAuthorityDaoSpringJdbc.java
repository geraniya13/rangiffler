package io.student.rangiffler.data.dao.impl;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.dao.AuthAuthorityDao;
import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.mapper.AuthorityEntityRowMapper;
import io.student.rangiffler.tpl.DataSources;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AuthAuthorityDaoSpringJdbc implements AuthAuthorityDao  {
    private final Config CFG = Config.getInstance();

    @Override
    public void create(AuthorityEntity... authority) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        jdbcTemplate.batchUpdate(
                "INSERT INTO `rangiffler-auth`.authority (user_id, authority) VALUES (UUID_TO_BIN(?, true),?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setObject(1, authority[i].getUser().getId());
                        ps.setString(2, authority[i].getAuthority().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return authority.length;
                    }
                }
        );

    }

    @Override
    public void delete(UserEntity user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM `rangiffler-auth`.`authority` WHERE user_id = (SELECT id FROM `rangiffler-auth`.`user` WHERE username = ?)"
            );
            preparedStatement.setString(1, user.getUsername());
            return preparedStatement;
        });
    }

    @Override
    public List<AuthorityEntity> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSources.dataSource(CFG.authJdbcUrl()));
        return jdbcTemplate.query(
                "SELECT * FROM `rangiffler-auth`.`authority`",
                AuthorityEntityRowMapper.INSTANCE
        );
    }
}
