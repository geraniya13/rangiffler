package io.student.rangiffler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInput {
  private String firstname;

  private String surname;

  private String avatar;

  private CountryInput location;

  @Override
  public String toString() {
    return "UserInput{firstname='" + firstname + "', surname='" + surname + "', avatar='" + avatar + "', location='" + location + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserInput that = (UserInput) o;
    return Objects.equals(firstname, that.firstname) &&
        Objects.equals(surname, that.surname) &&
        Objects.equals(avatar, that.avatar) &&
        Objects.equals(location, that.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, surname, avatar, location);
  }
}
