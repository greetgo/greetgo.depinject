package kz.greetgo.depinject.gen;

import static org.fest.assertions.api.Assertions.assertThat;
import kz.greetgo.depinject.gen.example.remote.test.AsdService;

import org.testng.annotations.Test;

public class GoingTypesTest {
  @Test
  public void extractFromSync() throws Exception {
    GoingTypes res = GoingTypes.extractFromSync(AsdService.class);
    assertThat(res).isNotNull();
    assertThat(res.toServer.toString()).isEqualTo(String.class.toString());
    assertThat(res.fromServer.toString()).isEqualTo(Long.class.toString());
  }
}
