package kz.greetgo.depinject.testing.old.t03x.test_beans033.p02_list_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class Bean03302_21 implements BeanGroup03302_2 {
  @Override
  public void hi(StringBuilder out) {
    out.append("Hi1 from ").append(getClass().getSimpleName()).append("\n");
  }
}
