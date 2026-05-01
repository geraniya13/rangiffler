package io.student.rangiffler.mapper;

import io.student.rangiffler.data.entity.auth.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserEntityRowMapper implements RowMapper<UserEntity> {
    public static final UserEntityRowMapper INSTANCE = new UserEntityRowMapper();

    private UserEntityRowMapper() {
    }

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(rs.getObject("id", UUID.class));
        userEntity.setUsername(rs.getString("username"));
        userEntity.setPassword(rs.getString("password"));
        userEntity.setEnabled(rs.getBoolean("enabled"));
        userEntity.setAccountNonExpired(rs.getBoolean("account_non_expired"));
        userEntity.setAccountNonLocked(rs.getBoolean("account_non_locked"));
        userEntity.setCredentialsNonExpired(rs.getBoolean("credentials_non_expired"));

        return userEntity;
    }
}
