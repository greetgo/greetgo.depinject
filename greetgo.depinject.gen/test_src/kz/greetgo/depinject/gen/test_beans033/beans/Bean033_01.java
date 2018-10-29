package kz.greetgo.depinject.gen.test_beans033.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean033_01 {
  public void hello(StringBuilder out) {
    out.append("Hello from ").append(getClass().getSimpleName()).append("\n");
  }
}
