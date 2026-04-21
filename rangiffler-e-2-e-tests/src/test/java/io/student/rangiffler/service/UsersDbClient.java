package io.student.rangiffler.service;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.DataBases;
import io.student.rangiffler.data.dao.impl.AuthAuthorityDaoJdbc;
import io.student.rangiffler.data.dao.impl.AuthUserDaoJdbc;
import io.student.rangiffler.data.dao.impl.AuthUserDaoSpringJdbc;
import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.enums.Authority;
import io.student.rangiffler.model.Data;
import io.student.rangiffler.model.User;
import io.student.rangiffler.model.UserJson;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static io.student.rangiffler.data.DataBases.xaTransaction;

public class UsersDbClient implements UsersClient {

    private static final Config CFG = Config.getInstance();

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private UUID uuid = UUID.randomUUID();

//    public UserJson createSpringUser(String userName, String password) {
//
//
//    }

    public List<UserJson> getAllUsers() {
        List<UserEntity> userEntityList = xaTransaction(Connection.TRANSACTION_READ_COMMITTED,
                new DataBases.XaFunction<>(
                        connection -> new AuthUserDaoJdbc(connection).findAll(),
                        CFG.authJdbcUrl()
                )

                );
        return userEntityList.stream()
                .map(userEntity -> createUserJson(userEntity.getId().toString(), userEntity.getUsername())).toList();
    }

    public List<AuthorityEntity> getAllAuthorities() {
        return xaTransaction(Connection.TRANSACTION_READ_COMMITTED,
                new DataBases.XaFunction<>(
                        connection -> new AuthAuthorityDaoJdbc(connection).findAll(),
                        CFG.authJdbcUrl()
                )
        );
    }

    @Override
    public UserJson createUser(String userName, String password) {
        UserEntity newUser = xaTransaction(Connection.TRANSACTION_READ_COMMITTED,
                new DataBases.XaFunction<>(
                        connection -> {
                            UserEntity userEntity = new UserEntity();
                            userEntity.setId(uuid);
                            userEntity.setUsername(userName);
                            userEntity.setPassword(passwordEncoder.encode(password));
                            userEntity.setEnabled(true);
                            userEntity.setAccountNonExpired(true);
                            userEntity.setAccountNonLocked(true);
                            userEntity.setCredentialsNonExpired(true);
                            UserEntity user = new AuthUserDaoJdbc(connection)
                                    .create(userEntity);

                            new AuthAuthorityDaoJdbc(connection).create(
                                    Arrays.stream(Authority.values())
                                            .map(authority -> {
                                                        AuthorityEntity authorityEntity = new AuthorityEntity();
                                                        authorityEntity.setUser(user);
                                                        authorityEntity.setAuthority(authority);
                                                        return authorityEntity;
                                                    }
                                            )
                                            .toArray(AuthorityEntity[]::new));
                            return user;
                        },
                        CFG.authJdbcUrl()
                )
        );
        return createUserJson(newUser.getId().toString(), newUser.getUsername());
    }

    @Override
    public void deleteUser(String userName) {
        xaTransaction(Connection.TRANSACTION_READ_COMMITTED,
                new DataBases.XaConsumer(
                        connection -> {
                            UserEntity userEntity = new UserEntity();
                            userEntity.setUsername(userName);

                            new AuthAuthorityDaoJdbc(connection).delete(userEntity);
                            new AuthUserDaoJdbc(connection).delete(userEntity);
                        },
                        CFG.authJdbcUrl()
                )
        );
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
