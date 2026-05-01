package io.student.rangiffler.mapper;

import io.student.rangiffler.data.entity.ApiUserEntity;
import io.student.rangiffler.data.entity.CountryEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ApiUserEntityRowMapper implements RowMapper<ApiUserEntity> {
    public static final ApiUserEntityRowMapper INSTANCE = new ApiUserEntityRowMapper();

    private ApiUserEntityRowMapper() {
    }

    @Override
    public ApiUserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ApiUserEntity userEntity = new ApiUserEntity();
        userEntity.setId(rs.getObject("id", UUID.class));
        userEntity.setUsername(rs.getString("username"));
        userEntity.setFirstname(rs.getObject("firstname", String.class));
        userEntity.setLastname(rs.getObject("lastname", String.class));
        userEntity.setAvatar(rs.getBytes("avatar"));
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(rs.getObject("country_id", UUID.class));
        userEntity.setCountry(countryEntity);

        return userEntity;
    }
}
