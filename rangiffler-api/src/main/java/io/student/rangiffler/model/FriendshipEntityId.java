package io.student.rangiffler.model;

import io.student.rangiffler.data.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipEntityId implements Serializable {
    private UserEntity requester;

    private UserEntity addressee;

    @Override
    public String toString() {
        return "FriendshipEntityId{" +
                "requester=" + requester +
                ", addressee=" + addressee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FriendshipEntityId that = (FriendshipEntityId) o;
        return Objects.equals(requester, that.requester) && Objects.equals(addressee, that.addressee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requester, addressee);
    }
}
