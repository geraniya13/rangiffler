package io.student.rangiffler.service;

import io.student.rangiffler.data.entity.AuthorityEntity;
import io.student.rangiffler.model.UserJson;

import java.util.List;

public interface UsersClient {
    UserJson createUser(String username, String password);

    void deleteUser(String username);

    List<UserJson> getAllUsers();

    List<AuthorityEntity> getAllAuthorities();

    UserJson getUser(String username);
}
