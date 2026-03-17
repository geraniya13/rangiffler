package io.student.rangiffler.controller.mutation;


import io.student.rangiffler.model.Photo;
import io.student.rangiffler.model.PhotoInput;
import io.student.rangiffler.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class PhotoMutationController {

    private final PhotoService photoService;

    @MutationMapping
    public Photo photo(@AuthenticationPrincipal Jwt principal,
                       @Argument PhotoInput input) {
        final String principalUsername = principal.getClaimAsString("sub");
        return photoService.createPhoto(principalUsername, input);
    }

    @MutationMapping
    public void deletePhoto(@AuthenticationPrincipal Jwt principal, @Argument UUID id) {
        final String principalUsername = principal.getClaimAsString("sub");
        photoService.deletePhoto(principalUsername, id);
    }
}
