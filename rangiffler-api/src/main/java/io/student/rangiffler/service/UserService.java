package io.student.rangiffler.service;

import io.student.rangiffler.data.entity.CountryEntity;
import io.student.rangiffler.data.entity.UserEntity;
import io.student.rangiffler.data.repository.CountryRepository;
import io.student.rangiffler.data.repository.UserRepository;
import io.student.rangiffler.model.User;
import io.student.rangiffler.model.UserInput;
import io.student.rangiffler.utils.PhotoDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

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
}
