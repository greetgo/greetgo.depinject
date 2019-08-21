package kz.greetgo.depinject.testing.old.t03x.test_beans033.p02_list_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class Bean03302_11 implements BeanGroup03302_1 {
  @Override
  public void hello(StringBuilder out) {
    out.append("Hello1 from ").append(getClass().getSimpleName()).append("\n");
  }
}
