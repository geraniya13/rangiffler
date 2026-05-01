package io.student.rangiffler.mapper;

import io.student.rangiffler.data.entity.CountryEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CountryEntityRowMapper implements RowMapper<CountryEntity> {
    public static final CountryEntityRowMapper INSTANCE = new CountryEntityRowMapper();

    private CountryEntityRowMapper() {
    }

    @Override
    public CountryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(rs.getObject("id", UUID.class));
        countryEntity.setCode(rs.getString("code"));
        countryEntity.setName(rs.getString("name"));
        countryEntity.setFlag(rs.getBytes("flag"));

        return countryEntity;
    }
}
