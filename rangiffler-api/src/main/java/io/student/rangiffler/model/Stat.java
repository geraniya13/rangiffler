package io.student.rangiffler.model;

import io.student.rangiffler.data.entity.StatisticEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stat {
    private int count;

    private Country country;

    @Override
    public String toString() {
        return "Stat{count='" + count + "', country='" + country + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stat that = (Stat) o;
        return count == that.count &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, country);
    }

    public static Stat toDto(StatisticEntity statEntity) {
        return Stat.builder()
                .country(Country.toDto(statEntity.getCountry()))
                .count(statEntity.getCount())
                .build();
    }
}
