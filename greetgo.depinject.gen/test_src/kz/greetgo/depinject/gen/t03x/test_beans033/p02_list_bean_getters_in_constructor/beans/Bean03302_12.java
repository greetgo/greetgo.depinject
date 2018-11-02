package kz.greetgo.depinject.gen.t03x.test_beans033.p02_list_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean03302_12 implements BeanGroup03302_1 {
  @Override
  public void hello(StringBuilder out) {
    out.append("Hello2 from ").append(getClass().getSimpleName()).append("\n");
  }
}
