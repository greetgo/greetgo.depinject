package kz.greetgo.depinject.testing.old;

import kz.greetgo.depinject.ap.engine.ClassName;
import org.fest.assertions.api.Assertions;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClassNameTest {
  @Test
  public void parseQualifiedName() {

    String qualifiedName = "kz.greetgo.wow.HelloSystem";

    //
    //
    ClassName className = ClassName.parseQualifiedName(qualifiedName);
    //
    //

    assertThat(className.packageName).isEqualTo("kz.greetgo.wow");
    assertThat(className.name).isEqualTo("HelloSystem");
  }
}
