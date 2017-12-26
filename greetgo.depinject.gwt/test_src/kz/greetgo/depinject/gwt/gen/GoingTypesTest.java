package kz.greetgo.depinject.gwt.gen;

import kz.greetgo.depinject.gwt.gen.example.remote.test.AsdService;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class GoingTypesTest {
  @Test
  public void extractFromSync() throws Exception {
    GoingTypes res = GoingTypes.extractFromSync(AsdService.class);
    assertThat(res).isNotNull();
    assertThat(res.toServer.toString()).isEqualTo(String.class.toString());
    assertThat(res.fromServer.toString()).isEqualTo("java.util.Map<java.lang.String, java.util.List<java.lang.Long>>");
  }
}
