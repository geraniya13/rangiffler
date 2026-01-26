package io.student.rangiffler.service;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.enums.Authority;
import io.student.rangiffler.model.Data;
import io.student.rangiffler.model.User;
import io.student.rangiffler.model.UserJson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.DriverManager;
import java.util.UUID;

public class UsersDbClient implements UsersClient {
    private static final Config CFG = Config.getInstance();

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private String uuid = UUID.randomUUID().toString();

    private static final String INSERT_USER_SQL = """
                  INSERT INTO `rangiffler-auth`.`user`
                   (id, username,password,enabled,account_non_expired,account_non_locked,credentials_non_expired)  
                   VALUES (UUID_TO_BIN(?, true),?,?,?,?,?,?);
            """,
            INSERT_AUTHORITIES_SQL = """
                            INSERT INTO `rangiffler-auth`.authority 
                            (id, user_id, authority)
                            VALUES (UUID_TO_BIN(?, true),UUID_TO_BIN(?, true),?);
                    """;

    @Override
    public UserJson createUser(String userName, String password) {
        try {
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(
                    DriverManager.getConnection(
                            CFG.authJdbcUrl(),
                            CFG.dbUsername(),
                            CFG.dbPassword()
                    ),
                    true)
            );

            insertUser(jdbcTemplate, uuid, userName, password);

            insertAuthorities(jdbcTemplate, uuid, Authority.read, Authority.write);

            return createUserJson(uuid, userName);

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void insertUser(
            JdbcTemplate jdbcTemplate,
            String uuid,
            String userName,
            String password
    ) {
        jdbcTemplate.update(
                INSERT_USER_SQL,
                uuid,
                userName,
                passwordEncoder.encode(password),
                true,
                true,
                true,
                true
        );
    }

    private void insertAuthorities(
            JdbcTemplate jdbcTemplate,
            String uuid,
            Authority... authorities
    ) {
        for (Authority authority : authorities) {
            jdbcTemplate.update(
                    INSERT_AUTHORITIES_SQL,
                    UUID.randomUUID().toString(),
                    uuid,
                    authority.toString()
            );
        }
    }

    private UserJson createUserJson(String uuid, String userName) {
        return new UserJson(
                new Data(
                        new User(
                                uuid,
                                userName,
                                null,
                                null,
                                null,
                                null
                        )
                )
        );
    }
}
