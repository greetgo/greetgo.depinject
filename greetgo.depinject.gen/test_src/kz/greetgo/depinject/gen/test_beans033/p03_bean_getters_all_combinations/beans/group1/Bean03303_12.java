package kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.group1;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean03303_12 implements BeanGroup03303_1 {
  @Override
  public void hello1(StringBuilder out) {
    out.append("Hello1 from ").append(getClass().getSimpleName()).append("\n");
  }
}
