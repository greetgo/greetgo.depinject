package kz.greetgo.depinject.gen;

import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class DepinjectVersionTest {
  @Test
  public void parse() {
    //
    //
    DepinjectVersion version = DepinjectVersion.parse("12.34.56");
    //
    //

    assertThat(version).isNotNull();
    assertThat(version.version1).isEqualTo(12);
    assertThat(version.version2).isEqualTo(34);
    assertThat(version.version3).isEqualTo(56);
  }
}