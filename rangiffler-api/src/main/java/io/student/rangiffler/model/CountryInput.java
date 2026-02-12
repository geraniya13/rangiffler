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
public class CountryInput {
  private String code;

  @Override
  public String toString() {
    return "CountryInput{code='" + code + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CountryInput that = (CountryInput) o;
    return Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }
}
