package io.student.rangiffler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feed {
  private String username;

  private boolean withFriends;

  private Page<Photo> photos;

  private List<Stat> stat;

  @Override
  public String toString() {
    return "Feed{username='" + username + "', withFriends='" + withFriends + "', photos='" + photos + "', stat='" + stat + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Feed that = (Feed) o;
    return Objects.equals(username, that.username) &&
        withFriends == that.withFriends &&
        Objects.equals(photos, that.photos) &&
        Objects.equals(stat, that.stat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, withFriends, photos, stat);
  }
}
