package kz.greetgo.depinject.gen;

import java.util.Objects;

public class DepinjectVersion implements Comparable<DepinjectVersion> {
  public final int version1, version2, version3;

  private DepinjectVersion(int version1, int version2, int version3) {
    this.version1 = version1;
    this.version2 = version2;
    this.version3 = version3;
  }

  public static DepinjectVersion parse(String version) {
    String[] split = version.split("\\.");
    return new DepinjectVersion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
  }

  @Override
  public String toString() {
    return "DepinjectVersion{" +
      "version1=" + version1 + ", version2=" + version2 + ", version3=" + version3 + '}';
  }

  @Override
  @SuppressWarnings("NullableProblems")
  public int compareTo(DepinjectVersion o) {
    {
      int cmp = version1 - o.version1;
      if (cmp != 0) {
        return cmp;
      }
    }
    {
      int cmp = version2 - o.version2;
      if (cmp != 0) {
        return cmp;
      }
    }
    {
      return version3 - o.version3;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    DepinjectVersion that = (DepinjectVersion) o;
    return compareTo(that) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(version1, version2, version3);
  }
}
