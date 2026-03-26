package io.student.rangiffler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoInput {
  private UUID id;

  private String src;

  private CountryInput country;

  private String description;

  private LikeInput like;

  @Override
  public String toString() {
    return "PhotoInput{id='" + id + "', src='" + src + "', country='" + country + "', description='" + description + "', like='" + like + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PhotoInput that = (PhotoInput) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(src, that.src) &&
        Objects.equals(country, that.country) &&
        Objects.equals(description, that.description) &&
        Objects.equals(like, that.like);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, src, country, description, like);
  }
}
