package io.student.rangiffler.service;

import io.student.rangiffler.data.entity.*;
import io.student.rangiffler.data.repository.*;
import io.student.rangiffler.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final PhotoRepository photoRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final StatisticRepository statisticRepository;
    private final LikeRepository likeRepository;
    private final PhotoLikeRepository photoLikeRepository;

    public Feed getFeed(String username, boolean withLikes) {
        return new Feed();
    }

    public Likes getLikes(Photo photo) {
        photoRepository.findById(photo.getId())
                .orElseThrow(() -> new IllegalArgumentException("Picture: %s not found".formatted(photo.getId().toString())));

        List<Like> likes = photoLikeRepository.findAllByPhotoId(photo.getId())
                .stream()
                .map(PhotoLikeEntity::getLike)
                .map(like -> likeRepository.findById(like.getId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Like %s not found".formatted(like.getId().toString())
                        ))
                )
                .map(likeEntity -> new Like().toDto(likeEntity))
                .toList();

        return new Likes(likes.size(), likes);
    }


    public List<Stat> getStat(Feed feed) {
        return new ArrayList<Stat>();
    }
}
