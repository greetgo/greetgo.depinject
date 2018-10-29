package kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean03302_22 implements BeanGroup03302_2 {
  @Override
  public void hi(StringBuilder out) {
    out.append("Hi2 from ").append(getClass().getSimpleName()).append("\n");
  }
}
