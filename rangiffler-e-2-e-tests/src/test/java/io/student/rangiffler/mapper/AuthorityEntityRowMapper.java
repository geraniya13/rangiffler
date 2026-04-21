package io.student.rangiffler.mapper;

import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.enums.Authority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthorityEntityRowMapper implements RowMapper<AuthorityEntity> {
    public static final AuthorityEntityRowMapper INSTANCE = new AuthorityEntityRowMapper();

    private AuthorityEntityRowMapper() {
    }

    @Override
    public AuthorityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setId(rs.getObject("id", UUID.class));
        UserEntity userEntity = new UserEntity();
        userEntity.setId(rs.getObject("user_id", UUID.class));
        authorityEntity.setUser(userEntity);
        authorityEntity.setAuthority(Authority.valueOf(rs.getString("authority")));

        return authorityEntity;
    }
}
