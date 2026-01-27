package io.student.rangiffler.tests.ui;

import io.student.rangiffler.annotation.User;
import io.student.rangiffler.model.UserJson;
import io.student.rangiffler.ui.page.auth.BaseAuthPage;
import io.student.rangiffler.ui.page.auth.LoginAuthPage;
import io.student.rangiffler.ui.page.auth.RegisterAuthPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.student.rangiffler.extension.UserExtension.PASSWORD;
import static io.student.rangiffler.ui.page.auth.LoginAuthPage.WRONG_CREDENTIALS;
import static io.student.rangiffler.ui.page.auth.RegisterAuthPage.DIF_PASSWORDS;
import static io.student.rangiffler.ui.page.auth.RegisterAuthPage.SHORT_PASSWORD;

public class AuthTest extends BaseUiTest {
    @Test
    @DisplayName("User should be able to register with correct data")
    public void userShouldBeAbleToRegisterWithCorrectData() {
        String password = faker.lorem().characters(6, true, true);
        BaseAuthPage.open(CFG.frontUrl(), RegisterAuthPage.class)
                .register()
                .register(faker.name().username(), password, password)
                .verifySuccess();
    }

    @User
    @Test
    @DisplayName("User should be able to login with correct data")
    public void userShouldBeAbleToLoginWithCorrectData(UserJson user) {
        BaseAuthPage.open(CFG.frontUrl(), LoginAuthPage.class)
                .login()
                .login(user.data().user().username(), PASSWORD)
                .assertMapIsVisible()
                .logout();
    }

    @Test
    @DisplayName("User should not be able to register with short password")
    public void userShouldNotBeAbleToRegisterWithShortPassword() {
        String password = "0";
        BaseAuthPage.open(CFG.frontUrl(), RegisterAuthPage.class)
                .register()
                .register(faker.name().username(), password, password)
                .verifyError(SHORT_PASSWORD);
    }

    @Test
    @DisplayName("User should not be able to register with unmatched passwords")
    public void userShouldNotBeAbleToRegisterWithUnmatchedPasswords() {
        BaseAuthPage.open(CFG.frontUrl(), RegisterAuthPage.class)
                .register()
                .register(faker.name().username(), PASSWORD, PASSWORD + "1")
                .verifyError(DIF_PASSWORDS);
    }

    @User
    @Test
    @DisplayName("User should not be able to register with existing username")
    public void userShouldNotBeAbleToRegisterWithExistingUsername(UserJson user) {
        BaseAuthPage.open(CFG.frontUrl(), RegisterAuthPage.class)
                .register()
                .register(user.data().user().username(), PASSWORD, PASSWORD)
                .verifySameState();
    }

    @User
    @Test
    @DisplayName("User should not be able to login with wrong password")
    public void userShouldNotBeAbleToLoginWithWrongPassword(UserJson user) {
        BaseAuthPage.open(CFG.frontUrl(), LoginAuthPage.class)
                .login()
                .failedLogin(user.data().user().username(), PASSWORD + "1", WRONG_CREDENTIALS);
    }


    @Test
    @DisplayName("User should not be able to login with non existent username")
    public void userShouldNotBeAbleToLoginWithNonExistentUsername() {
        BaseAuthPage.open(CFG.frontUrl(), LoginAuthPage.class)
                .login()
                .failedLogin(faker.name().username(), PASSWORD, WRONG_CREDENTIALS);
    }
}
