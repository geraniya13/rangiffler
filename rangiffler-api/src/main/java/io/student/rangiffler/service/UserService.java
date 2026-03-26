package io.student.rangiffler.service;

import io.student.rangiffler.data.entity.CountryEntity;
import io.student.rangiffler.data.entity.FriendshipEntity;
import io.student.rangiffler.data.entity.PhotoLikeEntity;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.data.repository.CountryRepository;
import io.student.rangiffler.data.repository.FriendshipRepository;
import io.student.rangiffler.data.repository.UserRepository;
import io.student.rangiffler.model.*;
import io.student.rangiffler.utils.PhotoDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final FriendshipRepository friendshipRepository;

    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username)
                .map(User::toDto);

        if (user.isEmpty()) {
            CountryEntity country = countryRepository
                    .findByCode("ru")
                    .orElseThrow(() ->
                            new IllegalArgumentException("Country was not found by code: ru")
                    );

            UserEntity userEntity = UserEntity.builder()
                    .username(username)
                    .country(country)
                    .build();

            userRepository.save(userEntity);

            return User.toDto(userEntity);
        }
        else {
            return user.get();
        }
    }


    public User updateUser(String username, UserInput userInput) {
        UUID userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username: %s not found".formatted(username)))
                .getId();

        CountryEntity country = countryRepository
                .findByCode(userInput.getLocation().getCode())
                .orElseThrow(() ->
                        new IllegalArgumentException("Country was not found by code: %s".formatted(userInput.getLocation().getCode()))
                );

            UserEntity userEntity = UserEntity.builder()
                    .id(userId)
                    .username(username)
                    .firstname(userInput.getFirstname())
                    .lastname(userInput.getSurname())
                    .avatar(PhotoDecoder.decodeDataUriBase64(userInput.getAvatar()))
                    .country(country)
                    .build();

            userRepository.save(userEntity);

            return User.toDto(userEntity);
    }

    public User updateFriendship(String username, FriendshipInput friendshipInput) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username: %s not found".formatted(username)));

        UserEntity friendUser = userRepository.findById(friendshipInput.getUser())
                .orElseThrow(() -> new IllegalArgumentException("Friend username: %s not found".formatted(username)));

        FriendshipEntity friendshipEntity = new FriendshipEntity();

        User friend = User.toDto(friendUser);

        switch (friendshipInput.getAction()) {
            case ADD:
                friendshipEntity.setStatus(FriendshipStatus.PENDING);
                friendshipEntity.setRequester(user);
                friendshipEntity.setAddressee(friendUser);
                friendshipRepository.save(friendshipEntity);
                friend.setFriendStatus(FriendStatus.INVITATION_SENT);
                break;
            case DELETE:


        }




        return friend;
    }

    public Slice<User> getAllUsers(String username, Pageable pageable, String searchQuery) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username: %s not found".formatted(username)));


        return searchQuery == null ? userRepository.findByUsernameNot(username, pageable)
                .map(User::toDto)
                : userRepository.findByUsernameNotAndSearchQuery(username, pageable, searchQuery)
                        .map(User::toDto);
    }
}
