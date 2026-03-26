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
public class FriendshipInput {
  private String user;

  private FriendshipAction action;

  @Override
  public String toString() {
    return "FriendshipInput{user='" + user + "', action='" + action + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FriendshipInput that = (FriendshipInput) o;
    return Objects.equals(user, that.user) &&
        Objects.equals(action, that.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, action);
  }
}
