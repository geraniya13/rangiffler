package io.student.rangiffler.service;

import io.student.rangiffler.config.Config;
import io.student.rangiffler.data.dao.ApiUserDao;
import io.student.rangiffler.data.dao.AuthAuthorityDao;
import io.student.rangiffler.data.dao.AuthUserDao;
import io.student.rangiffler.data.dao.impl.*;
import io.student.rangiffler.data.entity.ApiUserEntity;
import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.enums.Authority;
import io.student.rangiffler.model.Data;
import io.student.rangiffler.model.User;
import io.student.rangiffler.model.UserJson;
import io.student.rangiffler.tpl.JdbcTransactionTemplate;
import io.student.rangiffler.tpl.XaTransactionTemplate;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;

import static io.student.rangiffler.tpl.DataSources.dataSource;

public class UsersDbClient implements UsersClient {

    private static final Config CFG = Config.getInstance();

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final AuthUserDao authUserSpringDao = new AuthUserDaoSpringJdbc(),
    authUserDao = new AuthUserDaoJdbc();
    private final AuthAuthorityDao authAuthoritySpringDao = new AuthAuthorityDaoSpringJdbc(),
    authAuthorityDao = new AuthAuthorityDaoJdbc();
    private final ApiUserDao apiUserSpringDao = new ApiUserDaoSpringJdbc(),
    apiUserDao = new ApiUserDaoJdbc();

    private final TransactionTemplate txTemplate = new TransactionTemplate(
            new ChainedTransactionManager(
                    new JdbcTransactionManager(
                            dataSource(CFG.authJdbcUrl())
                    )
            )
    );

    private final JdbcTransactionTemplate jdbcTxTemplate = new JdbcTransactionTemplate(CFG.authJdbcUrl());

    private final XaTransactionTemplate xaTxTemplate = new XaTransactionTemplate(CFG.authJdbcUrl(), CFG.apiJdbcUrl());

    public UserJson createUserSpingJdbcChainedTx(String userName, String password) {
        UserEntity user = txTemplate.execute(status -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            userEntity.setPassword(passwordEncoder.encode(password));
            userEntity.setEnabled(true);
            userEntity.setAccountNonExpired(true);
            userEntity.setAccountNonLocked(true);
            userEntity.setCredentialsNonExpired(true);

            UserEntity createdUser = authUserSpringDao.create(userEntity);

            authAuthoritySpringDao.create(Arrays.stream(Authority.values()).map(
                    e -> {
                        AuthorityEntity authorityEntity = new AuthorityEntity();
                        authorityEntity.setUser(createdUser);
                        authorityEntity.setAuthority(e);
                        return authorityEntity;
                    }
            ).toArray(AuthorityEntity[]::new));

            return createdUser;
        });
        return createUserJson(user.getId().toString(), user.getUsername());
    }

    public UserJson createUserSpingJdbcTx(String userName, String password) {
        UserEntity user = jdbcTxTemplate.execute(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            userEntity.setPassword(passwordEncoder.encode(password));
            userEntity.setEnabled(true);
            userEntity.setAccountNonExpired(true);
            userEntity.setAccountNonLocked(true);
            userEntity.setCredentialsNonExpired(true);

            UserEntity createdUser = authUserSpringDao.create(userEntity);

            authAuthoritySpringDao.create(Arrays.stream(Authority.values()).map(
                    e -> {
                        AuthorityEntity authorityEntity = new AuthorityEntity();
                        authorityEntity.setUser(createdUser);
                        authorityEntity.setAuthority(e);
                        return authorityEntity;
                    }
            ).toArray(AuthorityEntity[]::new));

            return createdUser;
        });
        return createUserJson(user.getId().toString(), user.getUsername());
    }

    public User createApiUserXaTx(String userName, String password, String countryCode) {
        return xaTxTemplate.execute(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            userEntity.setPassword(passwordEncoder.encode(password));
            userEntity.setEnabled(true);
            userEntity.setAccountNonExpired(true);
            userEntity.setAccountNonLocked(true);
            userEntity.setCredentialsNonExpired(true);

            UserEntity createdUser = authUserDao.create(userEntity);

            authAuthorityDao.create(Arrays.stream(Authority.values()).map(
                    e -> {
                        AuthorityEntity authorityEntity = new AuthorityEntity();
                        authorityEntity.setUser(createdUser);
                        authorityEntity.setAuthority(e);
                        return authorityEntity;
                    }
            ).toArray(AuthorityEntity[]::new));

            ApiUserEntity user = new ApiUserEntity();
            user.setUsername(userName);
            user.setCountry(apiUserDao.findCountryByCode(countryCode).get());

            return User.fromEntity(apiUserDao.create(user));
        });
    }

    public UserJson createUserXaTx(String userName, String password) {
        return xaTxTemplate.execute(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            userEntity.setPassword(passwordEncoder.encode(password));
            userEntity.setEnabled(true);
            userEntity.setAccountNonExpired(true);
            userEntity.setAccountNonLocked(true);
            userEntity.setCredentialsNonExpired(true);

            UserEntity createdUser = authUserDao.create(userEntity);

            authAuthorityDao.create(Arrays.stream(Authority.values()).map(
                    e -> {
                        AuthorityEntity authorityEntity = new AuthorityEntity();
                        authorityEntity.setUser(createdUser);
                        authorityEntity.setAuthority(e);
                        return authorityEntity;
                    }
            ).toArray(AuthorityEntity[]::new));

            return createUserJson(createdUser.getId().toString(), createdUser.getUsername());
        });
    }

    public UserJson createUser(String userName, String password) {
        return xaTxTemplate.execute(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            userEntity.setPassword(passwordEncoder.encode(password));
            userEntity.setEnabled(true);
            userEntity.setAccountNonExpired(true);
            userEntity.setAccountNonLocked(true);
            userEntity.setCredentialsNonExpired(true);

            UserEntity createdUser = authUserDao.create(userEntity);

            authAuthorityDao.create(Arrays.stream(Authority.values()).map(
                    e -> {
                        AuthorityEntity authorityEntity = new AuthorityEntity();
                        authorityEntity.setUser(createdUser);
                        authorityEntity.setAuthority(e);
                        return authorityEntity;
                    }
            ).toArray(AuthorityEntity[]::new));

            return createUserJson(createdUser.getId().toString(), createdUser.getUsername());
        });
    }

    public void deleteUserXaTx(String userName) {
        xaTxTemplate.execute(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);

            authAuthorityDao.delete(userEntity);
            authUserDao.delete(userEntity);

            return null;
        });
    }

    public void deleteUserSpringJdbcTx(String userName) {
        jdbcTxTemplate.execute(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);

            authAuthoritySpringDao.delete(userEntity);
            authUserSpringDao.delete(userEntity);

            return null;
        });
    }

    public void deleteApiUserXaTx(String userName) {
        xaTxTemplate.execute(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            ApiUserEntity apiUserEntity = new ApiUserEntity();
            apiUserEntity.setUsername(userName);

            authAuthorityDao.delete(userEntity);
            authUserDao.delete(userEntity);
            apiUserDao.delete(apiUserEntity);

            return null;
        });
    }

    public void deleteUser(String userName) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);

            authAuthorityDao.delete(userEntity);
            authUserDao.delete(userEntity);
    }

    public void deleteUserSpringJdbc(String userName) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);

            authAuthoritySpringDao.delete(userEntity);
            authUserSpringDao.delete(userEntity);
    }


    public List<UserJson> getAllUsersTx() {
        return xaTxTemplate.execute(() -> authUserDao.findAll()).stream()
                .map(userEntity -> createUserJson(userEntity.getId().toString(), userEntity.getUsername())).toList();
    }

    public List<UserJson> getAllUsersSpringTx() {
        return jdbcTxTemplate.execute(() -> authUserSpringDao.findAll()).stream()
                .map(userEntity -> createUserJson(userEntity.getId().toString(), userEntity.getUsername())).toList();
    }

    public List<UserJson> getAllUsers() {
        return authUserDao.findAll().stream()
                .map(userEntity -> createUserJson(userEntity.getId().toString(), userEntity.getUsername())).toList();
    }

    public List<UserJson> getAllUsersSpring() {
        return authUserSpringDao.findAll().stream()
                .map(userEntity -> createUserJson(userEntity.getId().toString(), userEntity.getUsername())).toList();
    }

    public List<AuthorityEntity> getAllAuthoritiesTx() {
        return xaTxTemplate.execute(authAuthorityDao::findAll);
    }

    public List<AuthorityEntity> getAllAuthoritiesSpringTx() {
        return jdbcTxTemplate.execute(authAuthoritySpringDao::findAll);
    }

    public List<AuthorityEntity> getAllAuthorities() {
        return authAuthorityDao.findAll();
    }

    public List<AuthorityEntity> getAllAuthoritiesSpring() {
        return authAuthoritySpringDao.findAll();
    }

    public UserJson getUser(String userName) {
            return createUserJson(authUserDao.findByUsername(userName).orElseThrow().getId().toString(), userName);
    }

    public UserJson getUserSpringJdbc(String userName) {
            return createUserJson(authUserSpringDao.findByUsername(userName).orElseThrow().getId().toString(), userName);
    }

    public UserJson getUserXaTx(String userName) {
        return xaTxTemplate.execute(() -> {
            return createUserJson(authUserDao.findByUsername(userName).orElseThrow().getId().toString(), userName);
        });
    }

    public UserJson getUserSpringJdbcTx(String userName) {
        return jdbcTxTemplate.execute(() -> {
            return createUserJson(authUserSpringDao.findByUsername(userName).orElseThrow().getId().toString(), userName);
        });
    }

    public User getApiUserXaTx(String userName) {
        return xaTxTemplate.execute(() -> {
            return User.fromEntity(apiUserDao.findByUsername(userName).orElseThrow());
        });
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
