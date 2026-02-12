package io.student.rangiffler.model;

import lombok.*;

import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
  private String flag;

  private String code;

  private String name;

  @Override
  public String toString() {
    return "Country{flag='" + flag + "', code='" + code + "', name='" + name + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Country that = (Country) o;
    return Objects.equals(flag, that.flag) &&
        Objects.equals(code, that.code) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flag, code, name);
  }
}
