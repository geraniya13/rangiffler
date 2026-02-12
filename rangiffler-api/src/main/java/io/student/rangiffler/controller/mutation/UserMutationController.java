package io.student.rangiffler.controller.mutation;

import io.student.rangiffler.model.FriendshipInput;
import io.student.rangiffler.model.User;
import io.student.rangiffler.model.UserInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

@Controller
@PreAuthorize("isAuthenticated()")
public class UserMutationController {

    @MutationMapping
    public User user(@AuthenticationPrincipal Jwt principal,
                     @Argument UserInput input) {
        return User.builder().username("MockUser").build();
    }
    @MutationMapping
    public User friendship(@AuthenticationPrincipal Jwt principal,
                           @Argument FriendshipInput input) {
        return User.builder().username("MockUser").build();
    }
}
