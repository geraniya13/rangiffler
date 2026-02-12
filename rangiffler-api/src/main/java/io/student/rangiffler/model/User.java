package io.student.rangiffler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Objects;
import java.util.UUID;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private UUID id;

  private String username;

  private String firstname;

  private String surname;

  private String avatar;

  private FriendStatus friendStatus;

  private Page<User> friends;

  private Page<User> incomeInvitations;

  private Page<User> outcomeInvitations;

  private Country location;

  @Override
  public String toString() {
    return "User{id='" + id + "', username='" + username + "', firstname='" + firstname + "', surname='" + surname + "', avatar='" + avatar + "', friendStatus='" + friendStatus + "', friends='" + friends + "', incomeInvitations='" + incomeInvitations + "', outcomeInvitations='" + outcomeInvitations + "', location='" + location + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User that = (User) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(username, that.username) &&
        Objects.equals(firstname, that.firstname) &&
        Objects.equals(surname, that.surname) &&
        Objects.equals(avatar, that.avatar) &&
        Objects.equals(friendStatus, that.friendStatus) &&
        Objects.equals(friends, that.friends) &&
        Objects.equals(incomeInvitations, that.incomeInvitations) &&
        Objects.equals(outcomeInvitations, that.outcomeInvitations) &&
        Objects.equals(location, that.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, firstname, surname, avatar, friendStatus, friends, incomeInvitations, outcomeInvitations, location);
  }
}
