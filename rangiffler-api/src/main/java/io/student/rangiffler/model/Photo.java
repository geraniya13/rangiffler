package io.student.rangiffler.model;

import io.student.rangiffler.data.entity.PhotoEntity;
import io.student.rangiffler.utils.PhotoDecoder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
  private UUID id;

  private String src;

  private Country country;

  private String description;

  private LocalDate creationDate;

  private Likes likes;

  private boolean isOwner;

  @Override
  public String toString() {
    return "Photo{id='" + id + "', src='" + src + "', country='" + country + "', description='" + description + "', creationDate='" + creationDate + "', likes='" + likes + "', isOwner='" + isOwner + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Photo that = (Photo) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(src, that.src) &&
        Objects.equals(country, that.country) &&
        Objects.equals(description, that.description) &&
        Objects.equals(creationDate, that.creationDate) &&
        Objects.equals(likes, that.likes) &&
        isOwner == that.isOwner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, src, country, description, creationDate, likes, isOwner);
  }

    public Photo toDto(PhotoEntity photoEntity) {
        return Photo.builder()
                .id(photoEntity.getId())
                .src(PhotoDecoder.encodeDataUriBase64(photoEntity.getPhoto()))
                .country(Country.toDto(photoEntity.getCountry()))
                .description(photoEntity.getDescription())
                .creationDate(photoEntity.getCreatedDate().toLocalDate())
                .likes(new Likes())
                .isOwner(true)
                .build();
    }
}
