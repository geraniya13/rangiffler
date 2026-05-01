package io.student.rangiffler.tests;

import io.student.rangiffler.service.UsersDbClient;
import org.junit.jupiter.api.Test;

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
}
