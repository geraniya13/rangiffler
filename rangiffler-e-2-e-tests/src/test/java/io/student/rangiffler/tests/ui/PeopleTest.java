package io.student.rangiffler.tests.ui;

import io.student.rangiffler.annotation.UserType;
import io.student.rangiffler.extension.UsersQueueExtension;
import io.student.rangiffler.model.StaticUser;
import io.student.rangiffler.ui.pages.auth.BaseAuthPage;
import io.student.rangiffler.ui.pages.auth.LoginAuthPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.student.rangiffler.annotation.UserType.Type.*;
import static io.student.rangiffler.enums.Action.ACCEPT;
import static io.student.rangiffler.enums.Action.WAITING;
import static io.student.rangiffler.enums.PeopleTab.*;


@ExtendWith(UsersQueueExtension.class)
public class PeopleTest extends BaseUiTest {
    @Test
    void friendShouldBePresentInFriendsTable(@UserType(WITH_FRIEND) StaticUser user) {
        BaseAuthPage.open(CFG.frontUrl(), LoginAuthPage.class)
                .login()
                .login(user.username(), user.password())
                .assertMapIsVisible()
                .goToPeoplePage()
                .shouldHavePeople();
    }

    // always fails since data is hardcoded
    @Test
    void friendsTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {
        BaseAuthPage.open(CFG.frontUrl(), LoginAuthPage.class)
                .login()
                .login(user.username(), user.password())
                .assertMapIsVisible()
                .goToPeoplePage()
                .shouldBeEmpty();
    }

    // checked on income invitations tab instead of friends since data is hardcoded
    @Test
    void incomeInvitationBePresentInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {
        BaseAuthPage.open(CFG.frontUrl(), LoginAuthPage.class)
                .login()
                .login(user.username(), user.password())
                .assertMapIsVisible()
                .goToPeoplePage()
                .goToPeopleTab(INCOME_INVITATIONS)
                .shouldHavePeople()
                .shouldHavePersonWithActions(user.income(), ACCEPT);
    }

    // checked on outcome invitations tab instead of all people since data is hardcoded
    @Test
    void outcomeInvitationBePresentInAllPeoplesTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {
        BaseAuthPage.open(CFG.frontUrl(), LoginAuthPage.class)
                .login()
                .login(user.username(), user.password())
                .assertMapIsVisible()
                .goToPeoplePage()
                .goToPeopleTab(OUTCOME_INVITATIONS)
                .shouldHavePeople()
                .shouldHavePersonWithActions(user.outcome(), WAITING);
    }
}
