package kz.greetgo.depinject.testing.old.t03x.test_beans033.p01_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class Bean03301_3 {
  public void hello(StringBuilder out) {
    out.append("Hello from ").append(getClass().getSimpleName()).append("\n");
  }
}
