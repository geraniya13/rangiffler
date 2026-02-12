package io.student.rangiffler.controller.query;

import io.student.rangiffler.model.*;
import io.student.rangiffler.utils.GqlQueryPaginationAndSort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class FeedQueryController {
    @SchemaMapping(typeName = "Feed", field = "stat")
    public List<Stat> stat(Feed feed) {
        return List.of(new Stat());
    }

    @SchemaMapping(typeName = "Photo", field = "likes")
    public Likes likes(Photo photo) {
        return new Likes();
    }

    @SchemaMapping(typeName = "User", field = "photos")
    public Slice<Photo> photos(User user,
                               @Argument int page,
                               @Argument int size) {

        Pageable pageable = new GqlQueryPaginationAndSort(page, size).pageable();

        return new SliceImpl<>(
                List.of(new Photo()),
                pageable,
                false
        );
    }

    @SchemaMapping(typeName = "Feed", field = "photos")
    public Slice<Photo> photos(Feed feed,
                               @Argument int page,
                               @Argument int size) {

        Pageable pageable = new GqlQueryPaginationAndSort(page, size).pageable();

        return new SliceImpl<>(
                List.of(new Photo()),
                pageable,
                false
        );
    }

    @QueryMapping
    public Feed feed(@AuthenticationPrincipal Jwt principal,
                     @Argument boolean withFriends) {
        return new Feed();
    }
}
