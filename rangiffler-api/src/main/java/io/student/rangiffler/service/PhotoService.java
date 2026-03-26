package io.student.rangiffler.service;

import io.student.rangiffler.data.entity.CountryEntity;
import io.student.rangiffler.data.entity.PhotoEntity;
import io.student.rangiffler.data.repository.CountryRepository;
import io.student.rangiffler.data.repository.PhotoRepository;
import io.student.rangiffler.data.repository.UserRepository;
import io.student.rangiffler.model.Photo;
import io.student.rangiffler.model.PhotoInput;
import io.student.rangiffler.utils.PhotoDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    public Photo createPhoto(String username, PhotoInput photoInput) {
        UUID userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username: %s not found".formatted(username)))
                .getId();

        CountryEntity country = countryRepository
                .findByCode(photoInput.getCountry().getCode())
                .orElseThrow(() ->
                        new IllegalArgumentException("Country was not found by code: %s".formatted(photoInput.getCountry().getCode()))
                );

        PhotoEntity entity = PhotoEntity.builder()
                .userId(userId)
                .country(country)
                .description(photoInput.getDescription())
                .photo(PhotoDecoder.decodeDataUriBase64(photoInput.getSrc()))
                .createdDate(LocalDateTime.now())
                .build();

        PhotoEntity saved = photoRepository.save(entity);

        return new Photo().toDto(saved);
    }

    public void deletePhoto(String username, UUID photoId) {
        UUID userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username: %s not found".formatted(username)))
                .getId();

        var photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Picture: %s not found".formatted(photoId.toString())));

        if (!userId.equals(photo.getUserId())) {
            throw new AccessDeniedException("You can't delete this picture");
        }

        photoRepository.delete(photo);
    }
}
