package kz.greetgo.depinject.gen.t03x.test_beans033.p01_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean03301_3 {
  public void hello(StringBuilder out) {
    out.append("Hello from ").append(getClass().getSimpleName()).append("\n");
  }
}
