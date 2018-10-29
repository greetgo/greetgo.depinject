package kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.group2;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean03303_21 implements BeanGroup03303_2 {
  @Override
  public void hello2(StringBuilder out) {
    out.append("Hello2 from ").append(getClass().getSimpleName()).append("\n");
  }
}
