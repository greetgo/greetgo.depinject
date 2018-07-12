package modules;

import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ModulesTest {
  @Test
  public void findRoot() {
    System.err.println("Modules.findRoot() = " + Modules.findRoot());

    assertThat(Modules.findRoot().toFile()).isDirectory();
  }

  @Test
  public void depinject() {
    System.err.println("Modules.depinject() = " + Modules.depinject().toFile());
    assertThat(Modules.depinject().toFile()).isDirectory();
  }
}