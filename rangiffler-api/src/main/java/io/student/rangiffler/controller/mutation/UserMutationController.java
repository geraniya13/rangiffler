package io.student.rangiffler.controller.mutation;

import io.student.rangiffler.model.FriendshipInput;
import io.student.rangiffler.model.User;
import io.student.rangiffler.model.UserInput;
import io.student.rangiffler.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class UserMutationController {
    private final UserService userService;

    @MutationMapping
    public User user(@AuthenticationPrincipal Jwt principal,
                     @Argument UserInput input) {
        final String principalUsername = principal.getClaimAsString("sub");
        return userService.updateUser(principalUsername, input);
    }

    @MutationMapping
    public User friendship(@AuthenticationPrincipal Jwt principal,
                           @Argument FriendshipInput input) {
        return User.builder().username("MockUser").build();
    }
}
