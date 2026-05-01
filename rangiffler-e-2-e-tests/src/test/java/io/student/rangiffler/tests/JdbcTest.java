package io.student.rangiffler.tests;

import io.student.rangiffler.model.UserJson;
import io.student.rangiffler.service.UsersDbClient;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JdbcTest {
    @Test
    public void testApiUserXaTx() {
        String userName = "testUserName11";
        UsersDbClient usersDbClient = new UsersDbClient();
        usersDbClient.createApiUserXaTx(userName, "123456", "ae");
        usersDbClient.getUserXaTx(userName);
        usersDbClient.getApiUserXaTx(userName);
        usersDbClient.deleteApiUserXaTx(userName);
    }

    @Test
    public void testJdbcTemplateTX() {
        String userName = "testUserName12";
        UsersDbClient usersDbClient = new UsersDbClient();
        usersDbClient.createUserSpingJdbcTx(userName, "123456");
        usersDbClient.getUserSpringJdbc(userName);
        usersDbClient.deleteUserSpringJdbcTx(userName);
    }

    @Test
    public void testUserXaTx() {
        String userName = "testUserName13";
        UsersDbClient usersDbClient = new UsersDbClient();
        usersDbClient.createUserXaTx(userName, "123456");
        usersDbClient.getUserXaTx(userName);
        usersDbClient.deleteUserXaTx(userName);
    }

    @Test
    public void testDeleteUsersXaTx() {
        String userName = "testUserName";
        UsersDbClient usersDbClient = new UsersDbClient();
        for (int i = 1; i < 2; i++) {
            usersDbClient.deleteUserXaTx(userName + i);
        }
    }

    @Test
    public void testDeleteUsersXaTxJpa() {
        String userName = "testUserName";
        UsersDbClient usersDbClient = new UsersDbClient();
        for (int i = 1; i < 2; i++) {
            usersDbClient.deleteUserXaTxJpa(userName + i);
        }
    }

    @Test
    public void testDeleteAuthoritiesXaTxJpa() {
        UsersDbClient usersDbClient = new UsersDbClient();
        usersDbClient.deleteAuthorityXaTxJpa("arnita.gibson", "terrance.pacocha");
    }

    @Test
    public void testUserXaTxJpa() {
        String userName = "testUserName1";
        UsersDbClient usersDbClient = new UsersDbClient();
        usersDbClient.createUserXaTxJpa(userName, "123456");
    }

    @Test
    public void testFindAuthoritiesXaTxJpa() {
        UsersDbClient usersDbClient = new UsersDbClient();
        usersDbClient.getAllAuthoritiesTxJpa();
    }

    @Test
    public void testFindAllUsersXaTxJpa() {
        UsersDbClient usersDbClient = new UsersDbClient();
        List<UserJson> userJsonList = usersDbClient.getAllUsersTxJpa();
        for (UserJson userJson : userJsonList) {
            System.out.println(userJson.toString());
        }
    }

    @Test
    public void testFindUsersXaTxJpa() {
        UsersDbClient usersDbClient = new UsersDbClient();
        UserJson userJson = usersDbClient.getUserXaTxJpa("testUserName");
        System.out.println(userJson.toString());
    }
}
